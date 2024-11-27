package com.kong.cc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<String> addStore(@RequestBody StoreDto storeDto) {
		try {
			System.out.println(storeDto.getStoreName());
			Integer storeCode = storeManageService.createStoreCode();
			storeDto.setStoreCode(storeCode);
			storeDto.setStoreStatus("");
			storeManageService.addStore(storeDto);
			return new ResponseEntity<String>(String.valueOf(storeCode), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("가맹점 등록 오류", HttpStatus.BAD_REQUEST);
		}
	}
  	
  	@PostMapping("/modifyStoreMain") // ModifyStoreMain.js
	public ResponseEntity<String> modifyStore(@RequestBody StoreDto storeDto) {
		System.out.println(storeDto);
		try {
			Integer storeCode = storeManageService.modifyStore(storeDto);
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
			List<StoreDto> storeList = storeManageService.storeList(pageInfo, type, keyword, "Req");
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

  	@PostMapping("/deleteStoreMain/{storeCode}") 	 // DeleteReqStoreMain.js StoreDetailMain.js
	public ResponseEntity<String> deleteStore(@PathVariable Integer storeCode) {
		System.out.println(storeCode);
		try {
			storeManageService.deleteStore(storeCode);
			return new ResponseEntity<String>(String.valueOf(storeCode), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("가맹점 삭제 오류", HttpStatus.BAD_REQUEST);
		}
	}
  
  	@PostMapping("/restoreStoreMain/{storeCode}")    // RestoreStoreMain.js
	public ResponseEntity<String> restoreStore(@PathVariable Integer storeCode) {
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
			List<StoreDto> storeList = storeManageService.storeList(pageInfo, type, keyword, "Delete");
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
  	
  	@GetMapping("/storeDetailMain/{storeCode}")  // StoreDetailMain.js
  	public ResponseEntity<Map<String,Object>> storeDetail(@PathVariable(value="storeCode") Integer storeCode) {
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
