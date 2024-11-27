package com.kong.cc.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kong.cc.dto.CartDto;
import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.ItemExpenseDto;
import com.kong.cc.dto.PaymentRequestDto;
import com.kong.cc.dto.PaymentResponseDto;
import com.kong.cc.dto.ShopOrderDto;
import com.kong.cc.entity.Cart;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.ShopOrder;
import com.kong.cc.entity.Store;
import com.kong.cc.entity.WishItem;
import com.kong.cc.repository.CartRepository;
import com.kong.cc.repository.ItemRepository;
import com.kong.cc.repository.ShopDSLRepository;
import com.kong.cc.repository.ShopOrderRepository;
import com.kong.cc.repository.StoreRepository;
import com.kong.cc.repository.WishItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

	private final ShopDSLRepository shopDslRepo;
	private final ItemRepository itemRepo;
	private final StoreRepository storeRepo;
	private final WishItemRepository wishItemRepo;
	private final CartRepository cartRepo;
	private final ShopOrderRepository shopOrderRepo;
	
	

	// 카테고리 별 아이템 리스트 (검색X)
	@Override
	public List<ItemDto> selectItemsByCategroy(Integer majorNum,Integer middleNum,Integer subNum) throws Exception {
			
		return shopDslRepo.selectItemsByCategories(majorNum, middleNum, subNum).stream().map(item->item.toDto()).collect(Collectors.toList());
	}

	//검색된 아이템 리스트 
	@Override
	public List<ItemDto> selectItemsByKeyword(String keyword) throws Exception {

		List<ItemDto> itemList = null;
		// null이면 전체 조회
		if (keyword == null || keyword.trim().equals("")) {
			itemList = shopDslRepo.selectAllItems().stream().map(i -> i.toDto()).collect(Collectors.toList());
		} else {
			// keyword가 있는 경우 상품명
			itemList = shopDslRepo.selectItemsByKeyWord(keyword).stream().map(i->i.toDto()).collect(Collectors.toList());
		}
		return itemList;
	}

	@Override
	public ItemDto selectItem(String itemCode) throws Exception {
		return itemRepo.findById(itemCode).orElseThrow(()->new Exception("상품 번호 오류")).toDto();
	}

	@Override
	public Integer checkIsWished(String itemCode, Integer storeCode) throws Exception {
		return shopDslRepo.checkIsWishedItem(itemCode, storeCode);
	}

	//상세 페이지 관심상품 토글 - 관심 상품 등록, 삭제 
	@Override
	public Boolean toggleWishItem(String itemCode, Integer storeCode) throws Exception {
		//이미 등록된 상품인지 확인
		Integer wishNum = shopDslRepo.checkIsWishedItem(itemCode, storeCode);
		// 등록 안됨
		if(wishNum == null) {
			   WishItem wishItem= WishItem.builder()
					   			  .itemW(Item.builder().itemCode(itemCode).build())
					   			  .storeW(Store.builder().storeCode(storeCode).build())
					   			  .build();
			   wishItemRepo.save(wishItem);
			   return true;
		//등록됨	   
		}else {
		   wishItemRepo.deleteById(wishNum);
		   return false;
			}
		}

	//카테고리 선택 없는 전체 관심상품 리스트 
	@Override
	public List<ItemDto> selectAllWishItems(Integer storeCode) throws Exception {
			return shopDslRepo.selectAllWishItems(storeCode);
	}

	@Override
	public List<ItemDto> selectAllWishItemsByCategory(Integer storeCode, Integer majorNum, Integer middleNum,Integer subNum) throws Exception {
		
		return shopDslRepo.selectAllWishItemsByCategroy(storeCode, majorNum, middleNum, subNum);
	}

	//wishItem 에서 선택된 상품만 관심 상품 해제 (선택한 만큼 취소)
	@Override
	@Transactional
	public Boolean deleteCheckedWishItem(Integer storeCode,List<Integer>wishItemNumList) throws Exception {
		if(shopDslRepo.deleteCheckedWishItem(storeCode,wishItemNumList) == wishItemNumList.size()) {
			return true;
		}else {
			return false;	
		}
	}

	// cart에 아이템 추가 
	@Override
	@Transactional
	public CartDto addItemToCart(CartDto cartDto) throws Exception {
		
		//기본 수량 체크
		if(cartDto.getCartItemCount() <=0) throw new Exception("수량은 1개 이상 입력 하셔야합니다.");
		
		//내 가맹점 및 존재하는 아이템인지 예외 처리 
		Store myStore = storeRepo.findById(cartDto.getStoreCode()).orElseThrow(()->new Exception("해당 가맹점을 찾을 수 없습니다.")); 
		Item item = itemRepo.findById(cartDto.getItemCode()).orElseThrow(()->new Exception("해당 상품을 찾을 수 없습니다."));
		
		//이미 장바구니에 추가한 상품인지 check
		Integer isExistedNum = shopDslRepo.checkIsExistedCartItem(cartDto.getStoreCode(),cartDto.getItemCode()); 
		
		Cart cart;
		//카트 추가 -존재하면 수량에서 +
		if(isExistedNum!=null) {
			 cart = cartRepo.findById(isExistedNum).orElseThrow(()->new Exception("해당 장바구니를 찾을 수 없습니다."));
			 cart.setCartItemCount(cart.getCartItemCount()+cartDto.getCartItemCount());
		
		//카트 추가 - 존재하지 않음 새로 추가 
		}else {
			
		//예외처리로 확인된 가맹점과 아이템을 사용
			cart = Cart.builder()
			.cartItemCount(cartDto.getCartItemCount())
			.storeCa(myStore)
			.itemCa(item)
			.build();		 		
		}

		return cartRepo.save(cart).toDto();
	}

	@Override
	public List<CartDto> selectAllCartItems(Integer sotreCode) throws Exception {
		
		//예외처리 
		storeRepo.findById(sotreCode).orElseThrow(()->new Exception("해당 가맹점을 찾을 수 없습니다.")); 
				
		return shopDslRepo.selectAllCartItems(sotreCode);
	}

	//장바구니 목록에서 수량 변경 
	@Override
	@Transactional
	public CartDto updateCartItemCount(Integer cartNum, Integer count) throws Exception {

		if (count <= 0)
			throw new Exception("수량은 1개 이상 입력하셔야 합니다.");

		Cart cart = cartRepo.findById(cartNum).orElseThrow(() -> new Exception("해당 장바구니를 찾을 수 없습니다."));

		//기존 수량 덮어 쓰기 - 수량을 덜어낼 수도 있으니까
		cart.setCartItemCount(count);

		return cartRepo.save(cart).toDto();

	}

	//장바구니 삭제
	@Override
	public Boolean deleteCartItem(Integer storeCode, Integer cartNum) throws Exception {
		
		//예외처리 
		storeRepo.findById(storeCode).orElseThrow(()->new Exception("해당 가맹점을 찾을 수 없습니다.")); 
		
		return shopDslRepo.deleteCartItem(storeCode,cartNum)>0; //제대로 수행 시 결과는 1 따라서 true반환 
	}

	//Map<String,Object>로 리팩토링 예정, 주문정보와 주문아이템, 총 주문 분리시켜 조회 할 예정(주문자 반복 조회 이슈)
	@Override
	public List<CartDto> selectAllOrderItemAndInfo(Integer storeCode, List<Integer> cartItemNumList) throws Exception {
		
		//예외처리 
		storeRepo.findById(storeCode).orElseThrow(()->new Exception("해당 가맹점을 찾을 수 없습니다.")); 
		
		return shopDslRepo.selectAllCartItemForOrder(storeCode, cartItemNumList);
	}
	
	//결제 요청 정보 검증
	@Override
	public void validatePaymentRequest(PaymentRequestDto paymentRequest) throws Exception {
		 if (paymentRequest.getAmount() <= 0) {
	            throw new IllegalArgumentException("결제 금액이 올바르지 않습니다.");
	        }
	}

	@Override
	public PaymentRequestDto requestPayment(Integer storeCode, List<Integer> cartNums) throws Exception {
		// 선택된 장바구니 상품 정보 조회
        List<CartDto> selectedItems = shopDslRepo.selectAllCartItemForOrder(storeCode, cartNums);
		
        //결제 정보 생성
        
		return null;
	}
	
	//결제 검증
	@Override
	public PaymentResponseDto verifyPayment(String imUid, String merchanUid, Integer amount) throws Exception {
		
		return null;
	}

	
	// 주문 생성 및 장바구니 삭제
	@Transactional
	@Override
	public List<ShopOrderDto> createOrder(String merchantUid, Integer storeCode, List<Integer> cartNums)
			throws Exception {
		try {
			// 1. 장바구니 상품 조회
			List<Cart> carts = shopDslRepo.findCartsByCartNums(cartNums);
			if (carts.isEmpty()) {
				throw new Exception("주문할 상품이 없습니다.");
			}

			// 주문 생성
			List<ShopOrder> orders = carts.stream()
					.map(cart -> ShopOrder.builder()
							.orderCode(merchantUid) //생성한 주문번호  
							.orderCount(cart.getCartItemCount())
							.orderDate(new Date(System.currentTimeMillis()))
							.orderState("주문접수")
							.orderPayment("카드결제")
							.orderDelivery(cart.getItemCa().getItemStorage()) // 상품 보관 타입에 따른 배송
							.storeO(cart.getStoreCa()).itemO(cart.getItemCa()).build())
					.collect(Collectors.toList());

			// 주문 저장
			List<ShopOrder> savedOrders = shopOrderRepo.saveAll(orders);

			// 장바구니에서 주문 상품 삭제
			shopDslRepo.deleteCartItems(storeCode, cartNums);

			return savedOrders.stream().map(ShopOrder::toDto).collect(Collectors.toList());

		} catch (Exception e) {
			throw new Exception("주문 생성 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	
	//전체 주문 내역 조회 (최신일 기준,기본)
	@Override
	public List<ShopOrderDto> selectAllOrderList(Integer storeCode) throws Exception {

		return shopDslRepo.selectAllShopOrderList(storeCode);
	}

	//기간 설정 주문 내역 조회
	@Override
	public List<ShopOrderDto> selectAllOrderListByPeriod(Integer storeCode, Date startDate, Date endDate)
			throws Exception {
		
		return shopDslRepo.selectAllShopOrderListByPeriod(storeCode,startDate,endDate);
	}
	//주문 상태 주문 내역 조회
	@Override
	public List<ShopOrderDto> selectAllOrderListByOrderState(Integer storeCode, String orderState) throws Exception {
		
		return shopDslRepo.selectAllShopOrderListByOrderState(storeCode,orderState);
	}

	// 주문 상세 내역 - 같은 주문번호 인 주문 출력
	@Override
	public List<ShopOrderDto> selectOrderByOrderCode(Integer storeCode, String orderCode) throws Exception {
		
		return shopDslRepo.selectOneShopOrderByOrderCode(storeCode,orderCode);
	}

	// 기간 설정 주문 상품 별 통계 -상품 별, 대분류,중분류,소분류 통합 별
	@Transactional
	@Override
	public Map<String,Object> selectExpenseItemList(Integer storeCode, Date startDate, Date endDate) throws Exception {
		 
		// 가맹점 확인
		storeRepo.findById(storeCode).orElseThrow(()->new Exception("가맹점 조회 실패!"));
		
		Map<String,Object> result = new HashMap<>();
		
		// 기간 내 총 상품 주문 상품 단가,총 주문 개수 및 금액
		List<ItemExpenseDto> items = shopDslRepo.selectExpnseItemList( storeCode,  startDate,  endDate);
		List<ItemExpenseDto> majors = shopDslRepo.getExpenseItemSummeryByMajorCategroy(storeCode,startDate,endDate);
		List<ItemExpenseDto> middle = shopDslRepo.getExpenseItemSummeryByMiddleCategroy(storeCode,startDate,endDate);
		List<ItemExpenseDto> sub = shopDslRepo.getExpenseItemSummeryBySubCategroy(storeCode,startDate,endDate);
		
		Integer cnt = 0;
		Integer price = 0;
		for(ItemExpenseDto i :items) {
			cnt +=i.getTotalOrderCount();
			price+=i.getTotalOrderPrice();
		}
		Integer orderCnt = shopDslRepo.countOrders(storeCode, startDate, endDate).intValue();
		
		result.put("itemOrderTotalCnt", cnt); //기간내 주문한 총 제품개수
		result.put("itemOrderTotalPirce", price); // 기간내 주문 총 금액
		result.put("orderTotalCount",orderCnt); // 기간 내 총 주문건수 
		result.put("itemOrderExpsenSummary", items); //기간내 상품별 지출 summary
		result.put("itemOrderExpsenSummaryByMajor", majors); 
		result.put("itemOrderExpsenSummaryByMiddle", middle);
		result.put("itemOrderExpsenSummaryBySub", sub);
	
		return result;

	
	}

}

