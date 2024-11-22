package com.kong.cc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.AlarmDto;
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
	
//	@GetMapping("/selectStore") // MyStoreInfo.js
//	@PostMapping("/updateStore") // MyStoreInfo.js
	
//	@GetMapping("/selectStoreList") // MyStoreManage.js
//	@PostMapping("/addStore") // MyStoreManage.js
//	@GetMapping("/deleteStore") // MyStoreManage.js
}
