package com.kong.cc.service;



import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kong.cc.dto.CartDto;
import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.ItemExpenseDto;
import com.kong.cc.dto.OrderItemGroupByCodeDto;
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
import com.kong.cc.util.PageInfo;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

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
	
	 private IamportClient iamportClient; //IamPortClient SDK(토큰관리를대신해줌)
	
	@Value("${imp.api.key}")
	private String apiKey;
	
	@Value("${imp.api.secretkey}")
	private String apiSecret;
	
	//아임포트API사용위한 클라이언트 객체 
	@PostConstruct
	public void init() {
		this.iamportClient = new IamportClient(apiKey, apiSecret);
	}
	
	
	//쇼핑몰 메인 - 주문량이 많은 순 (주문량 없어도 리스트에 개수 맞추어 출력)
	@Override
	public Map<String, List<ItemDto>> getshopMainItems() throws Exception {
		 Map<String, List<ItemDto>> result = new HashMap<>();
		 
		    // 대분류별로 상품 조회  6개씩 자르기 (대분류 고정)
		 	// 커피자재
		    List<ItemDto> coffeeItemList = shopDslRepo.selectItemsByCategoriesWithSort(1, null, null)
		        .stream()
		        .limit(6)
		        .map(item->item.toDto())
		        .collect(Collectors.toList());
		    //분말가공    
		    List<ItemDto> powderItemList = shopDslRepo.selectItemsByCategoriesWithSort(2, null, null)
		        .stream()
		        .limit(6)
		        .map(item->item.toDto())
		        .collect(Collectors.toList());
		    
		    //유가공품
		    List<ItemDto> dairyItemList = shopDslRepo.selectItemsByCategoriesWithSort(7, null, null)
		        .stream()
		        .limit(6)
		        .map(item->item.toDto())
		        .collect(Collectors.toList());

		    result.put("커피자재", coffeeItemList);
		    result.put("분말가공", powderItemList);
		    result.put("유가공품", dairyItemList);
		    
		    return result;
	}

	// 카테고리 별 아이템 리스트 (검색X) +페이징
	@Override
	public List<ItemDto> selectItemsByCategroy(Integer majorNum,Integer middleNum,Integer subNum,PageInfo pageInfo) throws Exception {
		
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 8); //8개씩
		List<ItemDto> categroyItemList =shopDslRepo.selectItemsByCategories(majorNum, middleNum, subNum,pageRequest).stream().map(item->item.toDto()).collect(Collectors.toList());;
		Long allCnt =shopDslRepo.selectCountItemsByCategories(majorNum,middleNum,subNum);
	
		Integer allPage = (int) (Math.ceil(allCnt.doubleValue() / pageRequest.getPageSize()));
		Integer startPage = (pageInfo.getCurPage() - 1) / 8 * 8 + 1;
		Integer endPage = Math.min(startPage + 8 - 1, allPage);
		pageInfo.setAllPage(allPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		return categroyItemList;
	}

	//검색된 아이템 리스트 +페이징
	@Override
	public List<ItemDto> selectItemsByKeyword(String keyword,PageInfo pageInfo) throws Exception {
		
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 8); //8개씩
		List<ItemDto> itemList = null;
		Long allCnt = 0L;
		
		// null이면 전체 조회
		if (keyword == null || keyword.trim().equals("")) {
			itemList = shopDslRepo.selectAllItems(pageRequest).stream().map(i -> i.toDto()).collect(Collectors.toList());
			allCnt = shopDslRepo.selectAllCountItem();
		} else {
			// keyword가 있는 경우 상품명
			itemList = shopDslRepo.selectItemsByKeyWord(keyword,pageRequest).stream().map(i->i.toDto()).collect(Collectors.toList());
			allCnt = shopDslRepo.selectAllCountItemsByKeyWord(keyword);
		}
		Integer allPage = (int) (Math.ceil(allCnt.doubleValue() / pageRequest.getPageSize()));
		Integer startPage = (pageInfo.getCurPage() - 1) / 8 * 8 + 1;
		Integer endPage = Math.min(startPage + 8 - 1, allPage);
		pageInfo.setAllPage(allPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
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

	// 장바구니에 아이템 추가
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
	//장바구니 상품 목록 조회
	@Override
	public List<CartDto> selectAllCartItems(Integer storeCode) throws Exception {

		//예외처리
		storeRepo.findById(storeCode).orElseThrow(()->new Exception("해당 가맹점을 찾을 수 없습니다."));

		return shopDslRepo.selectAllCartItems(storeCode);
	}
	//장바구니 리스트 카운트 
	@Override
	public Integer selectAllCountCartItem(Integer storeCode) throws Exception {
		
		return shopDslRepo.selectCountCartList(storeCode).intValue();
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
	@Transactional
	@Override
	public Boolean deleteCartItem(Integer storeCode, Integer cartNum) throws Exception {
		
		//예외처리 
		storeRepo.findById(storeCode).orElseThrow(()->new Exception("해당 가맹점을 찾을 수 없습니다.")); 
		
		return shopDslRepo.deleteCartItem(storeCode,cartNum)>0; //제대로 수행 시 결과는 1 따라서 true반환 
	}
	//이전 구매 날짜 리스트
	@Override
	public Map<String,Object> selectPreOrderedDate(Integer storeCode) throws Exception {
		 Map<String,Object> result = new HashMap<>();
		 List<String> dates = shopDslRepo.selectPreOrderedDate(storeCode);
		 result.put("orderDates", dates);
		return result;
	}
	//이전 구매 상품리스트
	@Override
	public Map<String, Object> selectOrderedItemLisByDate(Integer storeCode, Date selectedDate, PageInfo pageInfo)throws Exception {

		 Map<String,Object> result = new HashMap<>();
		 //기존에 작성한 쿼리 재활용 함 (기간내 주문 상품 통계)
		 List<ItemExpenseDto>orderedItemSum = shopDslRepo.selectExpnseItemList(storeCode, selectedDate, selectedDate);
		 //페이지 처리
		 List<String> allDates = shopDslRepo.selectPreOrderedDate(storeCode);
		 if(allDates.isEmpty()) {
			 result.put("result", "주문 내역이 없습니다.");
		 }
		    pageInfo.setAllPage(allDates.size());
		    pageInfo.setStartPage(1);
		    pageInfo.setEndPage(allDates.size());

		    result.put("dates", allDates);
		    result.put("items", orderedItemSum);
		    result.put("pageInfo", pageInfo);
		    //버튼 활성화 true,fasle
		    result.put("hasPrevious", pageInfo.getCurPage() > 1); //true,false 반환
		    result.put("hasNext", pageInfo.getCurPage() < allDates.size()); //true,false 반환
		    result.put("selectedDate", new SimpleDateFormat("yyyy-MM-dd").format(selectedDate)); // 다시 스트링으로 내보내기

		return result;
	}
	//이전구매상품 목록에서 장바구니로 상품 추가(addcart,updateQuantity관련 메서드 활용)
	@Override
	public List<CartDto> addPreviousItemsToCart(Integer storeCode, List<String> itemCodesList) throws Exception {
		// 여러 상품 한번에 처리해주기 위함
		List<CartDto> updateCartList = new ArrayList<>();

		Cart cart;
		//카트에 있는 상품인지 조회- 카트번호 반환
		for (String itemCode : itemCodesList) {
			Integer isExistedNum = shopDslRepo.checkIsExistedCartItem(storeCode, itemCode);

	        //이미 넣은 상품이면  수량+1만 해줌 수량 변경 메서드,저장메서드 호출
	        if(isExistedNum !=null) {
	        	cart = cartRepo.findById(isExistedNum).orElseThrow(()->new Exception("해당 장바구니를 찾을 수 없습니다."));
	        	CartDto updateCartItem = updateCartItemCount(isExistedNum,cart.getCartItemCount()+1);

	        	
	        	//addcart 메서드 반환데이터 (다시 재 조회되는 cartList)
	        	updateCartList.add(updateCartItem);
	        	cartRepo.save(updateCartItem.toEntity());
	        	

	        //새로 추가 되는 상품 dto로 만들어 addCart메서드로 보내기 수량=1
	        }else {
	        	CartDto newItemToCart = CartDto.builder()
	        	.storeCode(storeCode)
	        	.itemCode(itemCode)
	        	.cartItemCount(1)
	        	.build();
	        	updateCartList.add(newItemToCart);
	        	cartRepo.save(newItemToCart.toEntity());
	        }
		}
		return updateCartList;
	}


	//Map<String,Object>로 리팩토링 예정, 주문정보와 주문아이템, 총 주문 분리시켜 조회 할 예정(주문자 반복 조회 이슈)
	@Override
	public List<CartDto> selectAllOrderItemAndInfo(Integer storeCode, List<Integer> cartItemNumList) throws Exception {
		
		//예외처리 
		storeRepo.findById(storeCode).orElseThrow(()->new Exception("해당 가맹점을 찾을 수 없습니다.")); 
		
		return shopDslRepo.selectAllCartItemForOrder(storeCode, cartItemNumList);
	}
	
	//결제 요청 정보 검증 (상품 재고는 따로 없음,결제 총 금액만 확인)
	@Override
	public Boolean validatePaymentRequest(PaymentRequestDto paymentRequest) throws Exception {
		 
		// 쿼리로 결제 금액 계산해서 비교하는거로 바꾸기  
		if (paymentRequest.getAmount() <= 0) {
	            throw new IllegalArgumentException("결제 금액이 올바르지 않습니다.");
	        }
		
		return true;
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
	public List<ShopOrderDto> createOrder(String merchantUid, String impUid,String paymentMethod, Integer storeCode, List<Integer> cartNums)
			throws Exception {
		try {
			
			System.out.println("결제 정보: merchantUid=" + merchantUid + ", impUid=" + impUid + ", paymentMethod=" + paymentMethod);
			System.out.println("카트 정보: storeCode=" + storeCode + ", cartNums=" + cartNums);
		    if (merchantUid == null || impUid == null || paymentMethod == null) {
		        throw new IllegalArgumentException("결제 정보가 누락되었습니다");
		    }

		    if (cartNums == null || cartNums.isEmpty()) {
		        throw new IllegalArgumentException("장바구니 정보가 없습니다");
		    }

			// 1. 장바구니 상품 조회
			if (cartNums.isEmpty()) {
				throw new Exception("주문할 상품이 없습니다.");
			}
			List<Cart> carts = shopDslRepo.findCartsByCartNums(cartNums);
			 if (carts.isEmpty()) {
		            throw new Exception("주문할 상품이 없습니다.");
		     }
			// 주문 생성 카트정보가지고 만듦
			List<ShopOrder> orders = carts.stream()
					.map(cart -> ShopOrder.builder()
							.orderCode(merchantUid) //생성한 주문번호  
							.impUid(impUid)
							.orderCount(cart.getCartItemCount())
							.orderDate(new Date(System.currentTimeMillis()))
							.orderState("주문접수")
							.orderPayment(paymentMethod)
							.orderDelivery(cart.getItemCa().getItemStorage()=="냉동"?"업체배송":"일반배송") // 상품 보관 타입에 따른 배송
							.storeO(cart.getStoreCa())
							.itemO(cart.getItemCa())
							.build())
					.collect(Collectors.toList());

			// 주문 저장
			List<ShopOrder> savedOrders = shopOrderRepo.saveAll(orders);

			// 장바구니에서 주문 상품 삭제
			shopDslRepo.deleteCartItems(storeCode, cartNums);

			return savedOrders.stream().map(ShopOrder::toDto).collect(Collectors.toList());

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("주문 생성 중 오류가 발생했습니다: " + e.getMessage());
		}
	}

	
	//주문내역조회(전체 및  조건별 포함)  
	@Override
	public Map<String,Object> selectAllOrderListForStore(Integer storeCode, Date startDate, Date endDate,String orderState,PageInfo pageInfo)
			throws Exception {
		Map<String,Object> result = new LinkedHashMap<>(); 
		
		 // 페이지 처리
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1,10); 
		
		 // Repository 호출하여 페이징된 주문 목록 조회 
		   Map<String, Object> orderData = shopDslRepo.selectAllShopOrderListForStore(
		       storeCode, startDate, endDate, orderState, pageRequest
		   );
		
		// 페이지 정보 계산
		   Long totalCount = (Long) orderData.get("totalCount");
		   Integer allPage = (int) Math.ceil(totalCount.doubleValue() / pageRequest.getPageSize());
		   Integer startPage = (pageInfo.getCurPage() - 1) / 10 * 10 + 1; 
		   Integer endPage = Math.min(startPage + 10 - 1, allPage);

		   pageInfo.setAllPage(allPage);
		   pageInfo.setStartPage(startPage);
		   pageInfo.setEndPage(endPage);

		   // 최종 결과 생성
		
		   result.put("orderList", orderData.get("orders")); 
		   result.put("totalCount", totalCount);
		   result.put("pageInfo", pageInfo);

		   return result;

	}

	// 주문 상세 내역 - 같은 주문번호인 주문 출력 + 결제API로 결제정보 가져오기 
	@Override
	public Map<String,Object> selectOrderByOrderCode(Integer storeCode, String orderCode) throws Exception {
		Map<String,Object> orderDetail = new HashMap<>();
		
		 // 본사인 경우 storeCode가 null이어도 조회 가능하도록 수정
		//db 저장된 주문 정보 가져오기 
	    List<ShopOrderDto> oneOrderDetail = storeCode == null ? 
	        shopDslRepo.selectOrderByOrderCode(orderCode) :  // 본사용 (모든 매장의 주문 조회 가능)
	        shopDslRepo.selectOneShopOrderByOrderCode(storeCode,orderCode); //가맹점용 
		orderDetail.put("orderDetail", oneOrderDetail);
		
		try {
			//impUid로 필요한 결제 정보 가져오기 
			String impUid = oneOrderDetail.get(0).getImpUid();
			IamportResponse<Payment> paymentInfo = iamportClient.paymentByImpUid(impUid);
			PaymentResponseDto payment = new PaymentResponseDto();
			payment.setPaymentMethod(paymentInfo.getResponse().getPayMethod());
			payment.setCancelReason(paymentInfo.getResponse().getCancelReason());
			payment.setCancelAmount(paymentInfo.getResponse().getCancelAmount().intValue());
			payment.setCancelDate(paymentInfo.getResponse().getCancelledAt());
			payment.setCardName(paymentInfo.getResponse().getCardName());
			payment.setCardNumber(paymentInfo.getResponse().getCardNumber());
			payment.setBankName(paymentInfo.getResponse().getBankName());
			payment.setPaymentStatus(paymentInfo.getResponse().getStatus());
			payment.setReceiptUrl(paymentInfo.getResponse().getReceiptUrl());
			
			orderDetail.put("paymentInfo", paymentInfo);
			
		} catch (Exception e) {
			e.printStackTrace();
			//db정보는 반환 될 수 있도록 함
			orderDetail.put("paymentInfoErr", "결제 정보 조회 실패");
		}
		
		return orderDetail;
	}

	//주문 취소 - 주문 접수만  취소 가능(아임포트와 연결하여 취소)
	@Transactional
	@Override
	public Boolean cancelItemOrder(Integer storeCode, String orderCode) throws Exception {
		
		// 주문 접수 상태인지 확인 하고 해당 리스트 저장 
		List<ShopOrder> checkedList = shopDslRepo.checkedListForCancelOrder(storeCode, orderCode);
		if(checkedList.size()==0) throw new Exception("주문 상태를 확인해 주세요,취소 가능한 주문이 없습니다. ");
		
		try {
			//결제 취소API호출 impUid로 결제내역 취소 
			ShopOrder order= checkedList.get(0);
			String impUid = order.getImpUid();
			
			//impUid 확인
			 if(impUid == null || impUid.trim().isEmpty()) {
		            throw new Exception("유효하지 않은 결제 정보입니다.");
		    }
			
			IamportResponse<Payment> paymentInfo = iamportClient.paymentByImpUid(impUid);
			 System.out.println("Payment info: " + paymentInfo.getResponse());
			 Payment payment = paymentInfo.getResponse();
		        // 결제 정보 상세 출력
		        System.out.println("Payment status: " + payment.getStatus());
		        System.out.println("Payment amount: " + payment.getAmount());
		        
		  

	    	CancelData cancelData = new CancelData(impUid,true); //true : 전체 취소
			cancelData.setReason("가맹점 주문 취소");// 취소 사유 추가 
			IamportResponse<Payment> response = iamportClient.cancelPaymentByImpUid(cancelData);
			
			// 이미 API에서 자동 취소된 경우(기 취소 상태 Code:-1) DB만 업데이트
			// paid 상태이면서 취소 요청 시 -1 코드가 온 경우
	        if(payment.getStatus().equals("paid") && response.getCode() == -1) {
	            System.out.println("이미 취소 처리된 거래. DB 상태를 업데이트합니다.");
	            checkedList.forEach(o -> o.setOrderState("주문취소"));
	            return !shopOrderRepo.saveAll(checkedList).isEmpty();
	        }   
				
			if(response.getCode()== 0) {// 서버 취소 완료
				//상태 변경 (접수->주문취소 )
				checkedList.forEach(o->o.setOrderState("주문취소"));
					
				return !(shopOrderRepo.saveAll(checkedList).isEmpty()); // true return;
			}
			throw new Exception("결제 취소 실패");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("취소 처리 오류 :" +e.getMessage());
		}
	}
	
	//주문내역 - 본사 
	@Override
	public Map<String, Object> selectAllOrderListForMainStore(Date startDate, Date endDate, String searchType,
			String keyword,PageInfo pageInfo) throws Exception {
		
		Map<String,Object> result = new LinkedHashMap<>();
	    
		
		 // 페이지 처리
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1,10); 
		
		 // Repository 호출하여 페이징된 주문 목록 조회 (검색 조건에 따른 조회)
		   Map<String, Object> orderData = shopDslRepo.selectMainStoreOrderList(
				   startDate, endDate, searchType, keyword,pageRequest
		   );
		
		// 페이지 정보 계산
		   Long totalCount = (Long) orderData.get("totalCount");
		   Integer allPage = (int) Math.ceil(totalCount.doubleValue() / pageRequest.getPageSize());
		   Integer startPage = (pageInfo.getCurPage() - 1) / 10 * 10 + 1; 
		   Integer endPage = Math.min(startPage + 10 - 1, allPage);

		   pageInfo.setAllPage(allPage);
		   pageInfo.setStartPage(startPage);
		   pageInfo.setEndPage(endPage);

		   // 최종 결과 생성
		
		   result.put("orderList", orderData.get("orders")); 
		   result.put("totalCount", totalCount);
		   result.put("pageInfo", pageInfo);

		   return result;

	}
	
	
	//가맹점 주문 상태 변경 -본사  
	@Override
	@Transactional
	public Boolean updateOrderStatus(String orderCode, String orderState) throws Exception {

		try {
			// 주문 상태 업데이트
			int updatedCount = shopDslRepo.updateOrderStatus(orderCode, orderState);
			return updatedCount > 0;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("주문 상태 변경 실패: " + e.getMessage());
		}
	}

	

	//지출내역통계- 기간 설정 주문 상품 별 통계 -상품 별, 대분류,중분류,소분류 통합 별
	@Transactional
	@Override
	public Map<String,Object> selectExpenseItemList(Integer storeCode, Date startDate, Date endDate) throws Exception {
		 
		// 가맹점 확인
		storeRepo.findById(storeCode).orElseThrow(()->new Exception("가맹점 조회 실패!"));
		
		Map<String,Object> result = new LinkedHashMap<>();
		
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
		//총 주문건 수 
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

