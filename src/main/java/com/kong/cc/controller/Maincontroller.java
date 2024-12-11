package com.kong.cc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.MemberDto;
import com.kong.cc.dto.MenuCategoryDto;
import com.kong.cc.dto.MenuDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.service.MainService;
import com.kong.cc.util.PageInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Maincontroller {
	
	private final MainService mainService;
	
	@Value("${upload.path}")
	private String uploadPath;
	
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
	@GetMapping("/selectStoreByStoreAddress/{address}") // Store.js
	public ResponseEntity<List<StoreDto>> selectStoreByStoreAddress(@PathVariable String address) {
		try {
			List<StoreDto> storeDtoList = mainService.selectStoreByStoreAddress(address);
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
	
	@GetMapping("/complainList/{page}") // Complain.js
	public ResponseEntity<Map<String,Object>> complainList(@PathVariable Integer page) {
		try {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurPage(page);
			List<ComplainDto> complainDtoList = mainService.complainList(pageInfo);
			Map<String,Object> listInfo = new HashMap<>();
			listInfo.put("complainList", complainDtoList);
			listInfo.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(listInfo, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/complainWrite") // ComplainWrite.js
	public ResponseEntity<String> complainWrite(@ModelAttribute ComplainDto complainDto) {
		try {
			mainService.complainWrite(complainDto);
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/allStoreList") // ComplainWrite.js
	public ResponseEntity<List<StoreDto>> allStoreList() {
		try {
			List<StoreDto> storeDtoList = mainService.allStoreList();
			return new ResponseEntity<List<StoreDto>>(storeDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StoreDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("image/{fileName}")
	public void image(@PathVariable String fileName, HttpServletResponse response) {
		System.out.println(fileName);
		try {
			InputStream ins = new FileInputStream(new File(uploadPath, fileName));
			FileCopyUtils.copy(ins, response.getOutputStream());
			ins.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/checkUsername/{username}") // FindPassword.js
	public ResponseEntity<MemberDto> checkUsername(@PathVariable String username) {
		try {
			System.out.println(username);
			MemberDto memberDto = mainService.checkUsername(username);
			return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<MemberDto>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/changePassword") // FindPassword.js
	public ResponseEntity<String> changePassword(@ModelAttribute MemberDto memberDto) {
		try {
			System.out.println(memberDto);
			String str = mainService.changePassword(memberDto);
			return new ResponseEntity<String>(str, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
