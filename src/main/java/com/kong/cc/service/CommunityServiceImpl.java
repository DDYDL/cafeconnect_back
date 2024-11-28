package com.kong.cc.service;

import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;
import com.kong.cc.entity.*;
import com.kong.cc.repository.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow( () ->  new Exception("noticeNum 정보가 없습니다."));
        return noticeInfo.toDto();
    }

    @Override
    public List<AskDto> askList() throws Exception {

        List<Ask> askList = this.askRepository.findAll();
        List<AskDto> askDtoList = askList.stream().map(Ask::toDto).collect(Collectors.toList());
        ;
        return askDtoList;

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

        // 저장하는 로직
        // askRepository.save(ask);


        askRepository.save(AskDto.builder()
                .askType(askDto.getAskType())
                .askTitle(askDto.getAskTitle())
                .askContent(askDto.getAskContent())
                .askStatus("1")
                .storeCode(askDto.getStoreCode())
                .build()
                .toEntity());
    }

    @Override
    public List<ComplainDto> complainList() throws Exception {


        List<Complain> complainList = this.complainRepository.findAll();
        List<ComplainDto> complainDtoList = complainList.stream().map(Complain::toDto).collect(Collectors.toList());

        return complainDtoList;


    }
};
