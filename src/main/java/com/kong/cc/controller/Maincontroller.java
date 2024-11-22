package com.kong.cc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.StoreDto;

@RestController
public class Maincontroller {
	
//	@GetMapping("/selectMenu") // IntroMain.js
//	public ResponseEntity<List<StoreDto>> selectStoreList(@PathVariable Integer memberNum) {
//		try {
//			List<StoreDto> storeDtoList = mypageService.selectStoreList(memberNum);
//			return new ResponseEntity<List<StoreDto>>(storeDtoList, HttpStatus.OK);
//		} catch(Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<List<StoreDto>>(HttpStatus.BAD_REQUEST);
//		}
//	}
	
//	@GetMapping("/selectStoreByStoreAddress") // Store.js
//	@GetMapping("/selectStoreByName") // Store.js / CompainWrite.js
//	@GetMapping("/selectMenuCategory") // MenuList.js
//	@GetMapping("/selectMenuByCategory") // MenuList.js
//	@GetMapping("/complainList") // Complain.js
//	@PostMapping("/complainWrite") // ComplainWrite.js
}
