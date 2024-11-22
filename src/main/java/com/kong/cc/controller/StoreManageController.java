package com.kong.cc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.StoreDto;
import com.kong.cc.service.StoreManageService;
import com.kong.cc.util.PageInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StoreManageController {
	
	private final StoreManageService storeManageService;
	
	  @GetMapping("/storeListMain") 	 // StoreListMain.js
	  public ResponseEntity<Map<String,Object>> storeList(@RequestParam(value="page", required=false, defaultValue = "1") Integer page,
				@RequestParam(value="type", required = false) String type,
				@RequestParam(value="keyword", required = false) String keyword) {
			System.out.println(keyword);
			try {
				PageInfo pageInfo = new PageInfo();
				pageInfo.setCurPage(page);
				List<StoreDto> storeList = storeManageService.storeList(pageInfo, type, keyword, "");
				System.out.println(storeList);
				Map<String,Object> listInfo = new HashMap<>();
				listInfo.put("storeList", storeList);
				listInfo.put("pageInfo", pageInfo);
				return new ResponseEntity<Map<String,Object>>(listInfo, HttpStatus.OK);
			} catch(Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
			}
		}	
	  
  	@PostMapping("/addStoreMain")  	 // AddStoreMain.js
	public ResponseEntity<String> addStore(StoreDto storeDto) {
		System.out.println(storeDto);
		try {
			Integer storeCode = storeManageService.createStoreCode();
			storeDto.setStoreCode(storeCode);
			storeManageService.addStore(storeDto);
			return new ResponseEntity<String>(String.valueOf(storeCode), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("가맹점 등록 오류", HttpStatus.BAD_REQUEST);
		}
	}
  	
  	@PostMapping("/modifyStoreMain") // StoreDetailMain.js
	public ResponseEntity<String> modifyStore(StoreDto storeDto) {
		System.out.println(storeDto);
		try {
			Integer storeCode = storeManageService.addStore(storeDto);
			return new ResponseEntity<String>(String.valueOf(storeCode), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("가맹점 수정 오류", HttpStatus.BAD_REQUEST);
		}
	}
  	
  	@GetMapping("/deleteReqList") 	 // DeleteReqStoreMain.js
    public ResponseEntity<Map<String,Object>> deleteReqList(@RequestParam(value="page", required=false, defaultValue = "1") Integer page,
			@RequestParam(value="type", required = false) String type,
			@RequestParam(value="keyword", required = false) String keyword) {
		System.out.println(keyword);
		try {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurPage(page);
			List<StoreDto> deleteReqList = storeManageService.storeList(pageInfo, type, keyword, "Req");
			System.out.println(deleteReqList);
			Map<String,Object> listInfo = new HashMap<>();
			listInfo.put("deleteReqList", deleteReqList);
			listInfo.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(listInfo, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}

  	@PostMapping("/deleteStore") 	 // DeleteReqStoreMain.js StoreDetailMain.js
	public ResponseEntity<String> deleteStore(Integer storeCode) {
		System.out.println(storeCode);
		try {
			storeManageService.deleteStore(storeCode);
			return new ResponseEntity<String>(String.valueOf(storeCode), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("가맹점 삭제 오류", HttpStatus.BAD_REQUEST);
		}
	}
  
  	@PostMapping("/restoreStore")    // RestoreStoreMain.js
	public ResponseEntity<String> restoreStore(Integer storeCode) {
		System.out.println(storeCode);
		try {
			storeManageService.restoreStore(storeCode);
			return new ResponseEntity<String>(String.valueOf(storeCode), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("가맹점 복원 오류", HttpStatus.BAD_REQUEST);
		}
	}
  	
  	@GetMapping("/selectDeleteList") // RestoreStoreMain.js
    public ResponseEntity<Map<String,Object>> selectDeleteList(@RequestParam(value="page", required=false, defaultValue = "1") Integer page,
			@RequestParam(value="type", required = false) String type,
			@RequestParam(value="keyword", required = false) String keyword) {
		System.out.println(keyword);
		try {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurPage(page);
			List<StoreDto> selectDeleteList = storeManageService.storeList(pageInfo, type, keyword, "Delete");
			System.out.println(selectDeleteList);
			Map<String,Object> listInfo = new HashMap<>();
			listInfo.put("selectDeleteList", selectDeleteList);
			listInfo.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(listInfo, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
  	
  	@GetMapping("/storeDetailMain")  // StoreDetailMain.js
  	public ResponseEntity<Map<String,Object>> storeDetail(@RequestParam(value="storeCode") Integer storeCode) {
		try {
			System.out.println("storeDetail");
			System.out.println(storeCode);
			Map<String, Object> res = new HashMap<>();
			StoreDto storeDto = storeManageService.storeDetail(storeCode);
			res.put("store", storeDto);
			return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
}
