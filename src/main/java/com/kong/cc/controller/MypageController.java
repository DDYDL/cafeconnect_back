package com.kong.cc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.AlarmDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.service.MypageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MypageController {
	
	private final MypageService mypageService;
	
	@GetMapping("/selectAlarmList/{storeCode}") // MyAlarmList.js
	public ResponseEntity<List<AlarmDto>> selectAlarmList(@PathVariable Integer storeCode) {
		try {
			List<AlarmDto> alarmDtoList = mypageService.selectAlarmList(storeCode);
			return new ResponseEntity<List<AlarmDto>>(alarmDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<AlarmDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/checkAlarmConfirm/{alarmNum}") // MyAlarmList.js
	public ResponseEntity<String> checkAlarmConfirm(@PathVariable Integer alarmNum) {
		try {
			mypageService.checkAlarmConfirm(alarmNum);
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/selectAlarmType/{alarmType}") // MyAlarmList.js
	public ResponseEntity<List<AlarmDto>> selectAlarmType(@PathVariable String alarmType) {
		try {
			List<AlarmDto> alarmDtoList = mypageService.selectAlarmType(alarmType);
			return new ResponseEntity<List<AlarmDto>>(alarmDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<AlarmDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/selectStore/{storeCode}") // MyStoreInfo.js
	public ResponseEntity<StoreDto> selectStore(@PathVariable Integer storeCode) {
		try {
			StoreDto storeDto = mypageService.selectStore(storeCode);
			return new ResponseEntity<StoreDto>(storeDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<StoreDto>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/updateStore") // MyStoreInfo.js
	public ResponseEntity<String> updateStore(@RequestBody StoreDto storeDto) {
		try {
			mypageService.updateStore(storeDto);
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/selectStoreList/{username}") // MyStoreManage.js
	public ResponseEntity<List<StoreDto>> selectStoreList(@PathVariable String username) {
		try {
			List<StoreDto> storeDtoList = mypageService.selectStoreList(username);
			return new ResponseEntity<List<StoreDto>>(storeDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StoreDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/addStore") // MyStoreManage.js
	public ResponseEntity<String> addStore(@RequestBody StoreDto storeDto) {
		try {
			mypageService.addStore(storeDto);
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/deleteStore/{storeCode}") // MyStoreManage.js
	public ResponseEntity<String> deleteStore(@PathVariable Integer storeCode) {
		try {
			mypageService.deleteStore(storeCode);
			return new ResponseEntity<String>("true", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
