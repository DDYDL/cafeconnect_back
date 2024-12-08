package com.kong.cc;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kong.cc.dto.AlarmDto;
import com.kong.cc.dto.NoticeDto;
import com.kong.cc.dto.StockDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.entity.Item;
import com.kong.cc.repository.AlarmDslRepository;
import com.kong.cc.repository.AlarmRepository;
import com.kong.cc.repository.ShopDSLRepository;
import com.kong.cc.repository.StockDslRepository;
import com.kong.cc.repository.StoreRepository;

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
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired 
	private ShopDSLRepository shopDslRepository;
	
	@Scheduled(cron = "0 30 14 * * *") // 매일 오전 01시에 실행
	public void run() {
		// 데이터베이스 처리
		// 1. 유통기한 3일 이하인 stock 알림 생성
		try {
			// 유통기한이 3일 이하 남은 stock 모두 가져오기
			List<StockDto> stockList = stockDslRepository.selectStockByExpirationDate("true").stream().map(s->s.toDto()).collect(Collectors.toList());
			
			if(stockList!=null) {
				// 각각의 알람 저장
				for(StockDto stock: stockList) {
					System.out.println(stock.toString());
					Item item = alarmDslRepository.selectItemByItemCode(stock.getItemCode());
					
					AlarmDto alarmDto = AlarmDto.builder()
							.storeCode(stock.getStoreCode())
							.alarmType("유통기한")
							.alarmDate(new Date(System.currentTimeMillis())) // 현재 시간
							.alarmContent(stock.getStockReceiptDate() + "에 들어온 [ " + item.getItemName()+ " ] 이(가) 유통기한이 3일 이하로 남았습니다.")
							.alarmStatus(false)
							.build();
					alarmRepository.save(alarmDto.toEntity());
				}				
			}
			
			log.info("add ExpirationDate Alarm");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 2. 재고가 3개 이하인 stock 알림 생성
		try {
			// 재고가 3개 이하 남은 stock 모두 가져오기
			List<StockDto> stockList = stockDslRepository.selectStockByStockCount(3).stream().map(s->s.toDto()).collect(Collectors.toList());
			
			if(stockList!=null) {
				// 각각의 알람 저장
				for(StockDto stock: stockList) {
					System.out.println(stock.toString());
					Item item = alarmDslRepository.selectItemByItemCode(stock.getItemCode());
					
					AlarmDto alarmDto = AlarmDto.builder()
							.storeCode(stock.getStoreCode())
							.alarmType("재고")
							.alarmDate(new Date(System.currentTimeMillis())) // 현재 시간
							.alarmContent(stock.getStockReceiptDate() + "에 들어온 [ " + item.getItemName()+ " ] 이(가) 재고가 3개 이하로 남았습니다.")
							.alarmStatus(false)
							.build();
					alarmRepository.save(alarmDto.toEntity());
				}
			}
			
			log.info("add StockCount Alarm");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 3. 주요 공지사항이 올라오면 알림 생성
		try {
			// 알람 생성 기준일 전날 올라온 주요 공지사항만 가져오기
			List<NoticeDto> noticeList = alarmDslRepository.selectNoticeByNoticeTypeAndNoticeDate("주요").stream().map(n->n.toDto()).collect(Collectors.toList());
			// 활성화 되어 있는 모든 가맹점 가져오기(삭제 신청, 삭제된 가맹점 제외)
			List<StoreDto> storeList = storeRepository.findByStoreStatus("active").stream().map(s->s.toDto()).collect(Collectors.toList());
			
			System.out.println(noticeList.toString());
			System.out.println(storeList.toString());
			
			if(noticeList!=null && storeList!=null ) {
				// 가져온 모든 가맹점에 공지사항 알람 생성하기 
				for(StoreDto store: storeList) {
					for(NoticeDto notice: noticeList) {
						System.out.println(notice.toString());
						
						AlarmDto alarmDto = AlarmDto.builder()
								.storeCode(store.getStoreCode())
								.alarmType("주요공지사항")
								.alarmDate(new Date(System.currentTimeMillis())) // 현재 시간
								.alarmContent("[ " + notice.getNoticeTitle() + " ] 주요 공지사항이 등록되었습니다.")
								.alarmStatus(false)
								.build();
						alarmRepository.save(alarmDto.toEntity());
					}
				}				
			}
			
			log.info("add Notice Alarm");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 @Scheduled(cron = "0 0 18 * * *") //매일 오후 6시 주문 접수 건->주문 확인으로 상태 변경(주문 접수에서만 취소 가능하도록 ) 
	 public void updateOrderState() {
		   try {
	            // 주문 상태 업데이트 로직
			   shopDslRepository.confirmOrderState();
	            System.out.println("금일 주문 접수건이 업데이트 됐습니다.");
	            log.info("금일 주문 접수건이 주문확인으로 변경되었습니다.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 }
}
