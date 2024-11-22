package com.kong.cc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.MenuCategoryDto;
import com.kong.cc.dto.MenuDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.service.MainService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Maincontroller {
	
	private final MainService mainService;
	
	@GetMapping("/selectMenu") // IntroMain.js
	public ResponseEntity<List<MenuDto>> selectMenu() {
		try {
			List<MenuDto> menuDtoList = mainService.selectMenu();
			return new ResponseEntity<List<MenuDto>>(menuDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<MenuDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	// 테스트 필요
	@GetMapping("/selectStoreByStoreAddress/{storeAddress}") // Store.js
	public ResponseEntity<List<StoreDto>> selectStoreByStoreAddress(@PathVariable String storeAddress) {
		try {
			List<StoreDto> storeDtoList = mainService.selectStoreByStoreAddress(storeAddress);
			return new ResponseEntity<List<StoreDto>>(storeDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StoreDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/selectStoreByName/{storeName}") // Store.js / CompainWrite.js
	public ResponseEntity<List<StoreDto>> selectStoreByName(@PathVariable String storeName) {
		try {
			List<StoreDto> storeDtoList = mainService.selectStoreByName(storeName);
			return new ResponseEntity<List<StoreDto>>(storeDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StoreDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/selectMenuCategory") // MenuList.js
	public ResponseEntity<List<MenuCategoryDto>> selectMenuCategory() {
		try {
			List<MenuCategoryDto> menuCategoryDtoList = mainService.selectMenuCategory();
			return new ResponseEntity<List<MenuCategoryDto>>(menuCategoryDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<MenuCategoryDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	// 테스트 필요
	@GetMapping("/selectMenuByCategory/{categoryNum}") // MenuList.js
	public ResponseEntity<List<MenuDto>> selectMenuByCategory(@PathVariable Integer categoryNum) {
		try {
			List<MenuDto> menuDtoList = mainService.selectMenuByCategory(categoryNum);
			return new ResponseEntity<List<MenuDto>>(menuDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<MenuDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/complainList") // Complain.js
	public ResponseEntity<List<ComplainDto>> complainList() {
		try {
			List<ComplainDto> complainDtoList = mainService.complainList();
			return new ResponseEntity<List<ComplainDto>>(complainDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ComplainDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/complainWrite") // ComplainWrite.js
	public ResponseEntity<String> complainWrite(@RequestBody ComplainDto complainDto) {
		try {
			mainService.complainWrite(complainDto);
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
