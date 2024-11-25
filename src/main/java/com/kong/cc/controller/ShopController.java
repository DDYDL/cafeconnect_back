package com.kong.cc.controller;

import java.util.Arrays;
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
import com.kong.cc.dto.ShopOrderDto;
import com.kong.cc.service.CategoryService;
import com.kong.cc.service.ShopService;

@RestController
public class ShopController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ShopService shopService;

	// 가맹점
	// @GetMapping("/shopMain") // ShopMain.js

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
	@GetMapping("/categoryItemList") // CategoryItemList.js
	public ResponseEntity<List<ItemDto>> selectItemByCategory(
			@RequestParam(name = "majorNum", required = false) Integer majorNum,
			@RequestParam(name = "middleNum", required = false) Integer middleNum,
			@RequestParam(name = "subNum", required = false) Integer subNum) {

		try {
			List<ItemDto> items = shopService.selectItemsByCategroy(majorNum, middleNum, subNum);

			return new ResponseEntity<List<ItemDto>>(items, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ItemDto>>(HttpStatus.BAD_REQUEST);
		}
	}

	// 검색 결과 아이템 리스트
	@PostMapping("/categoryItemSearch") // CategoryItemList.js
	public ResponseEntity<List<ItemDto>> selectItemByKeyWord(
			@RequestParam(name = "keyword", required = false) String keyword) {
		try {
			List<ItemDto> items = shopService.selectItemsByKeyword(keyword);

			return new ResponseEntity<List<ItemDto>>(items, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ItemDto>>(HttpStatus.BAD_REQUEST);
		}
	}

	// 상품 상세 with 관심 상품 등록 여부
	@GetMapping("/shopItemDetail/{itemCode}") // ShopItemDetail.js
	public ResponseEntity<Map<String, Object>> selectItemDetail(@PathVariable String itemCode,
			@RequestParam(name = "storeCode", required = false) Integer storeCode) {

		try {
			ItemDto item = null; // item정보 담기 -프론트에 이미 가지고 있는 정보를 가지고 사용할 지 재 조회할 지 고민중
			// Boolean isWished = false;
			Integer wishNum = null; // 관심 상품 등록 여부 확인

			item = shopService.selectItem(itemCode);
			// isWished = shopService.checkIsWished(itemCode, storeCode);
			wishNum = shopService.checkIsWished(itemCode, storeCode);

			Map<String, Object> result = new HashMap<>();
			result.put("item", item);
			result.put("wishNum", wishNum);

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
	public ResponseEntity<CartDto>addItemtoCart (@RequestBody CartDto cartDto){
		try {
			CartDto result = shopService.addItemToCart(cartDto);
			return new ResponseEntity<CartDto>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<CartDto>(HttpStatus.BAD_REQUEST);
		}
	}
	
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
	
//  @PostMapping("/selectPreviouOrder") //CartList.js
//	@GetMapping("/selectPreviouOrderDate") // CartList.js
	
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
	
	
	//@PostMapping("/paymentRequest") 프론트에서 아임포트로 결제 요청하기 전
	//@PostMapping("/paymentVerify")  아임포트 결제 완료 후 
	
	//아임포트 검증 및 결제 완료 후 주문 생성 
	@PostMapping("/paymentComplete") // OrderForm.js
	public ResponseEntity<List<ShopOrderDto>>orderComplete(@RequestParam String merchantUid,@RequestParam Integer storeCode, @RequestParam("check") Integer[] cartNum) {
		try {
			List<ShopOrderDto> result = shopService.createOrder(merchantUid,storeCode,Arrays.asList(cartNum));
			return new ResponseEntity<List<ShopOrderDto>>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ShopOrderDto>>(HttpStatus.BAD_REQUEST);
		}
	}
//  @GetMapping("/orderList")  // OrderListForStore.js
//  @PostMapping("/orderListByDate) // OrderListForStore.js	
//  @GetMapping("/orderListByOrderState") // OrderListForStore.js	
//  @GetMapping("/deleteOrder") // OrderListForStore.js
//	@GetMapping("/orderDetail") // OrderDetailForStore.js

//가맹점 상품구매에 따른 지출내역
//  @PostMapping("/expenseList") // ExpenseListByItems.js	

//본사
//  @PostMapping("/mainStoreOrderList) // OrderListForMainStore.js	
//  @PostMapping("/mainStoreOrderListByDate") // OrderListForMainStore.js	
//  @PostMapping("/mainStoreOrderListByKeyWord") // OrderListForMainStore.js	
//  @PostMapping("/changeOrderStatus") // OrderListForMainStore.js
//	@GetMapping("/mainStoreOrderDetail") // OrderDetailForMainStore.js

}
