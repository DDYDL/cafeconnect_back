package com.kong.cc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kong.cc.dto.AlarmDto;
import com.kong.cc.service.AlarmService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AlarmController {
	
	private final AlarmService alarmService;
	
	@PostMapping("/fcmToken") // LoginStore.js
	public ResponseEntity<Integer> fcmToken(@RequestBody Map<String,String> param) {
		try {
			System.out.println(param);
			//프론트에서 받은 fcmToken 데이터베이스에 저장
			Integer storeCode = alarmService.registFcmToken(param.get("username"),param.get("fcmToken"));
			return new ResponseEntity<Integer>(storeCode, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/alarms")
	public ResponseEntity<List<AlarmDto>> alarms(@RequestBody Map<String,String> param) {
		try {
			// 알람 리스트 조회(확인 안한 알람만)
			System.out.println("alarms");
			System.out.println(Integer.parseInt(param.get("storeCode")));
			List<AlarmDto> alarmDto = alarmService.getAlarmList(Integer.parseInt(param.get("storeCode")));
			return new ResponseEntity<List<AlarmDto>>(alarmDto, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<List<AlarmDto>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/alarmConfirm/{alarmNum}")
	public ResponseEntity<Boolean> confirmAlarm(@PathVariable Integer alarmNum) {
		try {
			// 하나의 알람 상태 변경하기
			Boolean confirm = alarmService.confirmAlarm(alarmNum);
			return new ResponseEntity<Boolean>(confirm, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
		}
	}
}
