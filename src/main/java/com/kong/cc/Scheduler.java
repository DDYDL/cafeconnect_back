package com.kong.cc;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kong.cc.dto.AlarmDto;
import com.kong.cc.dto.StockDto;
import com.kong.cc.entity.Item;
import com.kong.cc.repository.AlarmDslRepository;
import com.kong.cc.repository.AlarmRepository;
import com.kong.cc.repository.StockDslRepository;

import lombok.extern.slf4j.Slf4j;

@Component // 역제어
@Slf4j // 정형화 시켜서 로그 찍음
public class Scheduler {
	
	@Autowired
	private StockDslRepository stockDslRepository;
	@Autowired
	private AlarmRepository alarmRepository;
	@Autowired
	private AlarmDslRepository alarmDslRepository;
	
	@Scheduled(cron = "0 1 18 * * *") // 매일 오전 01시에 실행
	public void run() {
		// 데이터베이스 처리
		// 1. 유통기한 3일 이하인 stock 알림 생성
		try {
			// 유통기한이 3일 이하 남은 stock 모두 가져오기
			List<StockDto> stockList = stockDslRepository.selectStockByExpirationDate("true").stream().map(s->s.toDto()).collect(Collectors.toList());
			
			// 각각의 알람 저장
			for(StockDto stock: stockList) {
				System.out.println(stock.toString());
				Item item = alarmDslRepository.selectItemByItemCode(stock.getItemCode());
				
				AlarmDto alarmDto = AlarmDto.builder()
						.storeCode(stock.getStoreCode())
						.alarmType("유통기한")
						.alarmDate(new Date()) // 현재 시간
						.alarmContent(stock.getStockReceiptDate() + "에 들어온 [" + item.getItemName()+ "] 이(가) 유통기한이 3일 이하로 남았습니다.")
						.alarmStatus(false)
						.build();
				alarmRepository.save(alarmDto.toEntity());
			}
			
			log.info("add ExpirationDate Alarm");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 2. 재고가 3개 이하인 stock 알림 생성
		try {
			// 재고가 3개 이하 남은 stock 모두 가져오기
			List<StockDto> stockList = stockDslRepository.selectStockByStockCount(3).stream().map(s->s.toDto()).collect(Collectors.toList());
			
			// 각각의 알람 저장
			for(StockDto stock: stockList) {
				System.out.println(stock.toString());
				Item item = alarmDslRepository.selectItemByItemCode(stock.getItemCode());
				
				AlarmDto alarmDto = AlarmDto.builder()
						.storeCode(stock.getStoreCode())
						.alarmType("재고")
						.alarmDate(new Date()) // 현재 시간
						.alarmContent(stock.getStockReceiptDate() + "에 들어온 [" + item.getItemName()+ "] 이(가) 재고가 3개 이하로 남았습니다.")
						.alarmStatus(false)
						.build();
				alarmRepository.save(alarmDto.toEntity());
			}
			log.info("add StockCount Alarm");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		// 3. 주요 공지사항이 올라오면 알림 생성
		log.info("add Notice Alarm");
	}
}
