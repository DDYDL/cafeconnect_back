package com.kong.cc.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.CartDto;
import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.ItemMajorCategoryForm;
import com.kong.cc.dto.PaymentRequestDto;
import com.kong.cc.dto.PaymentResponseDto;
import com.kong.cc.dto.ShopOrderDto;
import com.kong.cc.service.CategoryService;
import com.kong.cc.service.ShopService;
import com.kong.cc.util.PageInfo;

@RestController
public class ShopController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ShopService shopService;

	// 가맹점 & 본사
	//대분류(커피자재,분말가공,유가공품)상품 6개 -> 판매순 6개로 바꾸기 
	@GetMapping("/shopMain") // ShopMain.js
	public ResponseEntity<Map<String, Object>> allShopMainItemList() {
		Map<String, Object> map = new HashMap<>();
		
		try {
			Map<String, List<ItemDto>> shopMainItems = shopService.getshopMainItems();
			map.put("allCategory", shopMainItems);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	
	}
	// 전체 카테고리 목록 출력
	@GetMapping("/shopCategory") // HoverCategorySidebar.js FixedCategorySideBar.js
	public ResponseEntity<Map<String, Object>> allCategoryList() {
		Map<String, Object> map = new HashMap<>();
		List<ItemMajorCategoryForm> allCategory = categoryService.getAllCategoriesForItem();
		try {
			map.put("allCategory", allCategory);
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	// 카테고리 선택 별 아이템 리스트
	@PostMapping("/categoryItemList") // CategoryItemList.js
	public ResponseEntity<Map<String,Object>> selectItemByCategory(
			@RequestParam(name="page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "majorNum", required = false) Integer majorNum,
			@RequestParam(name = "middleNum", required = false) Integer middleNum,
			@RequestParam(name = "subNum", required = false) Integer subNum) {

		Map<String,Object> result = new HashMap<>();
		try {
			
    		PageInfo pageInfo = new PageInfo();
    		pageInfo.setCurPage(page);
			
			List<ItemDto> items = shopService.selectItemsByCategroy(majorNum, middleNum, subNum,pageInfo);
			result.put("items", items);
            result.put("pageInfo", pageInfo);
			
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	// 검색 결과 아이템 리스트
	@PostMapping("/categoryItemSearch") // CategoryItemList.js
	public ResponseEntity<Map<String,Object>> selectItemByKeyWord(
			@RequestParam(name="page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "keyword", required = false) String keyword) {
			Map<String, Object> result = new HashMap<>();
		try {
			PageInfo pageInfo = new PageInfo();
    		pageInfo.setCurPage(page);
			
			List<ItemDto> items = shopService.selectItemsByKeyword(keyword,pageInfo);
			result.put("items", items);
            result.put("pageInfo", pageInfo);
            
			return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	// 상품 상세 with 관심 상품 등록 여부
	@GetMapping("/shopItemDetail/{itemCode}") // ShopItemDetail.js
	public ResponseEntity<Map<String, Object>> selectItemDetail(@PathVariable String itemCode,
			@RequestParam(name = "storeCode", required = false) Integer storeCode) {

		try {
			Map<String, Object> result = new HashMap<>();
			ItemDto item = null; // item정보 담기 -프론트에 이미 가지고 있는 정보를 가지고 사용할 지 재 조회할 지 고민중
			Integer wishNum = null; 
			
			item = shopService.selectItem(itemCode);
			result.put("item", item); 
			
			//store일때만 관심 상품 등록 여부 확인 (본사 접속 시 null)
			if (storeCode != null && !storeCode.equals("null")) { 
			wishNum = shopService.checkIsWished(itemCode, storeCode);
			result.put("wishNum", wishNum);
			}
			
			
			
			return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	// 토글로 관심상품 추가 및 삭제
	@PostMapping("/addWishItem") // ShopItemDetail.js
	public ResponseEntity<String> toggleWishItem(@RequestParam String itemCode, @RequestParam Integer storeCode) {
		try {
			Boolean result = shopService.toggleWishItem(itemCode, storeCode);

			return new ResponseEntity<String>(String.valueOf(result), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/wishItem") // WishItem.js
	public ResponseEntity<List<ItemDto>> selectAllWishItem(@RequestParam Integer storeCode,
			@RequestParam(name = "majorNum", required = false) Integer majorNum,
			@RequestParam(name = "middleNum", required = false) Integer middleNum,
			@RequestParam(name = "subNum", required = false) Integer subNum) {
		try {
			List<ItemDto> result = null;
			// 카테 고리 조건이 없을 때 -전체 조회
			if (majorNum == null && middleNum == null && subNum == null) {
				result = shopService.selectAllWishItems(storeCode);
				// 대,중,소분류 선택 할 때마다 조회
			} else {
				result = shopService.selectAllWishItemsByCategory(storeCode, majorNum, middleNum, subNum);
			}

			return new ResponseEntity<List<ItemDto>>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ItemDto>>(HttpStatus.BAD_REQUEST);
		}
	}

	// 토글 취소가 아닌 리스트 조회 페이지에서 취소
	@PostMapping("/deleteWishItem") // WishItem.js
	public ResponseEntity<String> wishItemDelete(@RequestParam Integer storeCode,@RequestParam("check") Integer wishItemNum[]) {
		try {
			Boolean result = shopService.deleteCheckedWishItem(storeCode, Arrays.asList(wishItemNum));
			return new ResponseEntity<String>(String.valueOf(result), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//장바구니 추가 및 수량 변경 
	@GetMapping("/addCart") // ShopMain.js CategoryItemList.js WishItem.js ShopItemDetail.js CartList.js	
	public ResponseEntity<CartDto>addItemtoCart (CartDto cartDto){
		try {
			CartDto result = shopService.addItemToCart(cartDto);
			return new ResponseEntity<CartDto>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<CartDto>(HttpStatus.BAD_REQUEST);
		}
	}
	//장바구니 리스트 출력
	@GetMapping("/cartList") //CartList.js
	public ResponseEntity<List<CartDto>>selectAllCartItemList (@RequestParam Integer storeCode) {
		try {
			List<CartDto> result = shopService.selectAllCartItems(storeCode);
			return new ResponseEntity<List<CartDto>>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CartDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	//장바구니 상품 수량 변경
	@PostMapping("/updateCartItemQuantity") //CartList.js
	public ResponseEntity<CartDto> updateCartItemQuantity(@RequestParam Integer cartNum,@RequestParam Integer count) {
		try {
			CartDto result = shopService.updateCartItemCount(cartNum,count);
			return new ResponseEntity<CartDto>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<CartDto>(HttpStatus.BAD_REQUEST);
		}
	}
	//장바구니 상품 삭제 
	@PostMapping("deleteCartItem") //CartList.js
	public ResponseEntity<String> deleteCartListItem (@RequestParam Integer storeCode,@RequestParam Integer cartNum) {
		try {
			Boolean result = shopService.deleteCartItem(storeCode,cartNum);
			return new ResponseEntity<String>(String.valueOf(result), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	//장바구니 이전 주문 날짜 리스트  조회(10개) 
	@GetMapping("/selectPreviouOrderDate") // CartList.js
	public ResponseEntity<Map<String,Object>> selectPreviousOrderDate(@RequestParam Integer storeCode){
		
		try {
			Map<String,Object> result = shopService.selectPreOrderedDate(storeCode); 
            return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//장바구니에서 이전 주문 목록 선택 (페이지 1 고정이라 pageInfo안쓰고, 인덱스로 데이터 변경함 리팩토링 필요 )
	@PostMapping("/selectPreviouOrder") //CartList.js
	public ResponseEntity<Map<String,Object>>selectPreviousOrderList(@RequestParam Integer storeCode, 
																	   @RequestParam(name="orderDate",required = false) String orderDate,
																	   @RequestParam(name="page",required = false)Integer page) {
		try {
			 Map<String, Object> result = new HashMap<>();
			 Date selectedDate = Date.valueOf(orderDate);// 선택 날짜
			 page = 1;
			 PageInfo pageInfo = new PageInfo();
			 if(page!=null) {
				 pageInfo.setCurPage(page);
			 }
			 pageInfo.setCurPage(page);
			
			 result = shopService.selectOrderedItemLisByDate(storeCode, selectedDate, pageInfo);
			 
			 return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST); 
		}
	
	}
	//장바구니 이전 주문 목록에서 장바구니로 상품 추가
	@PostMapping("/addPreOrderItemToCart")	 
	public ResponseEntity<List<CartDto>> addPreviousOrderItemsToCart (@RequestParam Integer storeCode, @RequestParam("check") String[] checkedItemCodes) {
		try {
			 List<CartDto> result = shopService.addPreviousItemsToCart(storeCode,Arrays.asList(checkedItemCodes));
			return new ResponseEntity<List<CartDto>>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CartDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//장바구니에서 주문 선택주문, 전체주문 모두 가능 
	@PostMapping("/order") // CartList.js
	public ResponseEntity<List<CartDto>> selectOrderItemAndInfo(@RequestParam Integer storeCode, @RequestParam("check") Integer[] cartNum){
		try {
			List<CartDto> result = shopService.selectAllOrderItemAndInfo(storeCode, Arrays.asList(cartNum));
			return new ResponseEntity<List<CartDto>>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<CartDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//결제 요청 전 검증 (주문번호 생성해서 내려보내기)
	@PostMapping("/paymentRequest") //프론트에서 아임포트로 결제 요청 완성 전 검증
	public ResponseEntity<Map<String,Object>>requestPayment(@RequestBody PaymentRequestDto paymentRequest) {
		Map<String,Object> validateResult = new HashMap<>();
		try {
			Boolean result = shopService.validatePaymentRequest(paymentRequest);
		
			// 여기서 주문 번호 생성해서 보내기 
			ShopOrderDto dto = new ShopOrderDto();
			String merchantUid = dto.makeOrderCode();
			System.out.println(merchantUid);
			
			validateResult.put("isValidated",result); //true
			validateResult.put("orderCode", merchantUid);//주문번호 생성해서 내려보내기 
					
			return new ResponseEntity<Map<String,Object>>(validateResult,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			validateResult.put("isValidated", false);// true
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	// 결제 완료 후 데이터베이스 저장 전 api-key,secret으로 아임포트 서버인증토큰발급 및 서버와 결제정보 조회 및 검증
	@PostMapping("/paymentVerify")
	public ResponseEntity<PaymentResponseDto>responsePayment(@RequestBody PaymentResponseDto paymentResponse) {
		try {
			shopService.verifyPayment(paymentResponse.getImpUid(),paymentResponse.getMerchantUid(), paymentResponse.getTotalAmount());
			return new ResponseEntity<PaymentResponseDto>(paymentResponse,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<PaymentResponseDto>(HttpStatus.BAD_REQUEST);
			
		}
	}
 	
	//아임포트 검증 및 결제 완료 후 주문 생성 
	@PostMapping("/paymentComplete") // OrderForm.js
	public ResponseEntity<List<ShopOrderDto>>orderComplete(@RequestBody PaymentResponseDto paymentResponse) {
		try {
			 System.out.println("Received payment response: " + paymentResponse); // 로그 추가
			   List<ShopOrderDto> result = shopService.createOrder(
			           paymentResponse.getMerchantUid(),
			           paymentResponse.getImpUid(),
			           paymentResponse.getPaymentMethod(),
			           paymentResponse.getStoreCode(),
			           paymentResponse.getCartNums()
			           );
			return new ResponseEntity<List<ShopOrderDto>>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ShopOrderDto>>(HttpStatus.BAD_REQUEST);
		}
	}

	//가맹점 주문내역 - 전체(한달전~오늘기준),기간설정,주문상태 포함
	@PostMapping("/orderListForStore") // OrderListForStore.js	
    public ResponseEntity<Map<String,Object>>selectAllItemOrderListForStore(@RequestParam Integer storeCode,
    																	@RequestParam(name="startDate",required = false)String startDate,
    																	@RequestParam(name="endDate",required = false)String endDate,
    																	@RequestParam(name="orderState",required = false) String orderState){
    
    	try {
   
    		Date sqlStartDate =null;	
    		Date sqlEndDate =null;
    		
    		//파라미터 없음 default 기간설정 (오늘날짜~30일 기준 startDate:30일전,endDate:오늘)
    		if(startDate==null &&endDate==null ) {
    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    		 Calendar now = Calendar.getInstance();
    		 Calendar amonthAgo = Calendar.getInstance();
    		 amonthAgo.add(Calendar.MONTH, -1);    // 한달 전
    		 
    		 endDate = sdf.format(now.getTime()); // 오늘 날짜로   
    		 startDate = sdf.format(amonthAgo.getTime()); // 한달 전
   
    		}
    		// 파라미터 있음
    		//String Date 형식 => 2024-11-27
    		 sqlStartDate = Date.valueOf(startDate);	
    		 sqlEndDate = Date.valueOf(endDate);
    		
    		Map<String,Object>result = shopService.selectAllOrderListForStore(storeCode,sqlStartDate,sqlEndDate,orderState);
    		
    		return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK); 
    		
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
    }
	
	 
	@GetMapping("/cancelItemOrder") // OrderListForStore.js
	public ResponseEntity<String>cancelOrder(@RequestParam Integer storeCode,@RequestParam String orderCode) {
		try {
			Boolean result = shopService.cancelItemOrder(storeCode,orderCode);
			return new ResponseEntity<String>(String.valueOf(result),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//주문 상세 내역 
    @PostMapping("/orderDetail") // OrderDetailForStore.js
    public ResponseEntity<Map<String,Object>>selectOrderByOrderCode(@RequestParam Integer storeCode,@RequestParam String orderCode) {
    	try {
    		Map<String,Object> result = shopService.selectOrderByOrderCode(storeCode,orderCode);
			return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
    }
    
    //가맹점 상품구매에 따른 지출내역
    @PostMapping("/expenseList") // ExpenseListByItems.js	
    public ResponseEntity <Map<String,Object>> selectExpenseItemList(@RequestParam Integer storeCode,
			@RequestParam(name="startDate",required = false)String startDate,
			@RequestParam(name="endDate",required = false)String endDate){

    	try {
    		
    		Date sqlStartDate =null;	
    		Date sqlEndDate =null;
    		
    		//파라미터 없음 default 기간설정 (오늘날짜~30일 기준 startDate:30일전,endDate:오늘)
    		if(startDate==null &&endDate==null ) {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    		 Calendar now = Calendar.getInstance();
    		 Calendar amonthAgo = Calendar.getInstance();
    		 amonthAgo.add(Calendar.MONTH, -1);    // 한달 전
    		 
    		 endDate = sdf.format(now.getTime()); // 오늘 날짜로   
    		 startDate = sdf.format(amonthAgo.getTime()); // 한달 전
   
    		}
    		// 파라미터 있음
    		//String Date 형식 => 2024-11-27
    		 sqlStartDate = Date.valueOf(startDate);	
    		 sqlEndDate = Date.valueOf(endDate);
    		
    		Map<String,Object> result = shopService.selectExpenseItemList(storeCode,sqlStartDate,sqlEndDate);
    		
			return new ResponseEntity <Map<String,Object>>(result,HttpStatus.OK);
    		
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
    }

//본사 주문내역 
  @PostMapping("/mainStoreOrderList") // OrderListForMainStore.js
  public ResponseEntity<Map<String,Object>>selectMainStoreOrderList( @RequestParam(name="startDate",required = false) String startDate,
        															 @RequestParam(name="endDate",required = false) String endDate,
        															 @RequestParam(name="searchType",required = false) String searchType,
															 		 @RequestParam(name="keyword",required = false) String keyword) {


	  try {

		  Date sqlStartDate =null;	
		  Date sqlEndDate =null;

		  //파라미터 없음 default 기간설정 (오늘날짜~30일 기준 startDate:30일전,endDate:오늘)
		  if(startDate==null &&endDate==null ) {
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			  Calendar now = Calendar.getInstance();
			  Calendar amonthAgo = Calendar.getInstance();
			  amonthAgo.add(Calendar.MONTH, -1);    // 한달 전

			  endDate = sdf.format(now.getTime()); // 오늘 날짜로   
			  startDate = sdf.format(amonthAgo.getTime()); // 한달 전

		  }
		  // 파라미터 있음
		  sqlStartDate = Date.valueOf(startDate);	
		  sqlEndDate = Date.valueOf(endDate);

		  Map<String,Object> result = shopService.selectAllOrderListForMainStore(
		            sqlStartDate, sqlEndDate, searchType, keyword);

		  return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK); 

	  } catch (Exception e) {
		  e.printStackTrace();
		  return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
	  }
  }

  //주문 상태 변경
  @PostMapping("/updateOrderStatus")
  public ResponseEntity<String> updateOrderStatus(
		  @RequestParam String orderCode,
		  @RequestParam String orderState) {


	  try {
		  Boolean result = shopService.updateOrderStatus(orderCode, orderState);
		  
		  return new ResponseEntity<String>(String.valueOf(result), HttpStatus.OK);
	  } catch (Exception e) {
		  e.printStackTrace();
		  return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	  }
  }

  //본사 주문 상세 조회
  @PostMapping("/mainStoreOrderDetail")
  public ResponseEntity<Map<String,Object>> selectMainStoreOrderDetail(@RequestParam(required = false) Integer storeCode,@RequestParam String orderCode) {
	  try {
		  Map<String,Object> result = shopService.selectOrderByOrderCode(storeCode,orderCode);
		  return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	  } catch (Exception e) {
		  e.printStackTrace();
		  return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
	  }
  }
}
