package com.kong.cc.service;

import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;
import com.kong.cc.entity.Ask;
import com.kong.cc.entity.Complain;
import com.kong.cc.entity.Notice;
import com.kong.cc.entity.Store;
import com.kong.cc.repository.AskRepository;
import com.kong.cc.repository.ComplainRepository;
import com.kong.cc.repository.NoticeRepository;
import com.kong.cc.repository.StoreRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final JPAQueryFactory jpaQueryFactory;
    private final NoticeRepository noticeRepository;
    private final AskRepository askRepository;
    private final StoreRepository storeRepository;
    private final ComplainRepository complainRepository;

    @Override
    public List<NoticeDto> noticeList() throws Exception {
        List<Notice> noticeList = this.noticeRepository.findAll();
        List<NoticeDto> noticeDtoList = noticeList.stream().map(Notice::toDto).collect(Collectors.toList());
        ;
        return noticeDtoList;
    }

    @Override
    public NoticeDto noticeDetail(Integer noticeNum) throws Exception {
        Notice noticeInfo = this.noticeRepository.findByNoticeNum(noticeNum)
                .orElseThrow(() -> new Exception("noticeNum 정보가 없습니다."));
        return noticeInfo.toDto();
    }

    @Override
    public List<AskDto> askList(Integer storeCode) throws Exception {

        List<Ask> askList = askRepository.findByStoreCode(storeCode);
        List<Ask> askSaved = new ArrayList<>();

        if (askList.isEmpty()) {
            throw new IllegalArgumentException("No complains found with the provided ids.");
        }
        for (Ask ask : askList) {
            ask.setStoreAs(Store.builder().storeCode(storeCode).build());
            askSaved.add(ask);
            System.out.println("ask" + ask);
        }

        return askSaved.stream().map(Ask::toDto).collect(Collectors.toList());
    }

    @Override
    public void saveAnswer(Integer askNum, AskDto askDto) throws Exception {

        LocalDate now = LocalDate.now();
        // DB에 변경 사항 저장
        askRepository.save(Ask.builder().askAnswer(askDto.getAskAnswer()).askStatus("1").askAnswerDate(Date.valueOf(now)).askNum(askNum).build());
    }


    @Override
    public void deleteAnswer(Integer askNum) throws Exception {

        LocalDate now = LocalDate.now();
        Ask ask = askRepository.findByAskNum(askNum);

        if (ask != null) {
            // 답변 상태를 '0'으로 설정 (삭제된 상태)
            ask.setAskStatus("0"); // 0을 삭제 상태로 가정
//            ask.setAskAnswer(""); // 답변 내용도 초기화
            ask.setAskAnswerDate(Date.valueOf(now)); // 답변 날짜 초기화

            // DB에 변경 사항 저장
            askRepository.save(ask);
        } else {
            throw new Exception("해당 문의가 존재하지 않습니다.");
        }
    }

    @Override
    public void askWrite(AskDto askDto) throws Exception {

        // storeCode를 기반으로 Store 엔티티 조회
        Store store = storeRepository.findById(askDto.getStoreCode())
                .orElseThrow(() -> new IllegalArgumentException("해당 storeCode에 해당하는 Store가 존재하지 않습니다."));
        System.out.println("StoreCode dto " + askDto.getStoreCode());
        System.out.println("StoreCode repo" + store.getStoreCode());
        Integer storeCode = store.getStoreCode();

        askRepository.save(AskDto.builder()
                .askType(askDto.getAskType())
                .askTitle(askDto.getAskTitle())
                .askContent(askDto.getAskContent())
                .askStatus("1")
                .storeCode(storeCode)
                .build()
                .toEntity());
    }

    @Override
    public AskDto askAnswer(Integer storeCode, Integer askNum) throws Exception {
        Ask askInfo = this.askRepository.findByStoreCodeAndAskNum(storeCode, askNum)
                .orElseThrow(() -> new Exception("storeCode 또는 askNum이 일치하는 정보가 없습니다."));
        return askInfo.toDto();
    }

    @Override
    public List<NoticeDto> noticeModal() throws Exception {
        List<Notice> noticeList = this.noticeRepository.findAll();
        List<NoticeDto> noticeDtoList = noticeList.stream().map(Notice::toDto).collect(Collectors.toList());
        ;
        return noticeDtoList;

    }

    ;

    @Override
    public List<ComplainDto> complainList(Integer storeCode) throws Exception {

        List<Complain> complainList = complainRepository.findByStoreCode(storeCode);
        List<Complain> complainSaved = new ArrayList<>();

        if (complainList.isEmpty()) {
            throw new IllegalArgumentException("No complains found with the provided ids.");
        }
        for (Complain complain : complainList) {
            complain.setStoreCo(Store.builder().storeCode(storeCode).build());
            complainSaved.add(complain);
            System.out.println("complain" + complain);
        }

        return complainSaved.stream().map(Complain::toDto).collect(Collectors.toList());
    }

    @Override
    public ComplainDto complainDetail(Integer complainNum) throws Exception {
        Complain complainInfo = this.complainRepository.findByComplainNum(complainNum)
                .orElseThrow(() -> new Exception("complainNum에 해당하는 정보가 없습니다."));
        return complainInfo.toDto();
    }

}
