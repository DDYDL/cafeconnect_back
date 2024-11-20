package com.kong.cc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<Map<String,Object>> boardList(@RequestParam(value="page", required=false, defaultValue = "1") Integer page,
			@RequestParam(value="type", required = false) String type,
			@RequestParam(value="keyword", required = false) String keyword) {
		System.out.println(keyword);
		try {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurPage(page);
			List<StoreDto> boardList = storeManageService.storeList(pageInfo, type, keyword);
			System.out.println(boardList);
			Map<String,Object> listInfo = new HashMap<>();
			listInfo.put("boardList", boardList);
			listInfo.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(listInfo, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}	
	
  	@GetMapping("/addStoreMain")  	 // AddStoreMain.js
	public ResponseEntity<String> addStore(StoreDto storeDto) {
		System.out.println(storeDto);
		try {
			Integer storeCode = storeManageService.addStore(storeDto);
			return new ResponseEntity<String>(String.valueOf(storeCode), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("가맹점 등록 오류", HttpStatus.BAD_REQUEST);
		}
	}		
  	
//  @GetMapping("/selectDeleteList") // RestoreStoreMain.js
//  @PostMapping("/restoreStore")    // RestoreStoreMain.js
//  @GetMapping("/storeDetailMain")  // StoreDetailMain.js
//  @PostMapping("/modifyStoreMain") // StoreDetailMain.js
	
//  @GetMapping("/deleteReqList") 	 // DeleteReqStoreMain.js
//  @PostMapping("/deleteStore") 	 // DeleteReqStoreMain.js StoreDetailMain.js
}
