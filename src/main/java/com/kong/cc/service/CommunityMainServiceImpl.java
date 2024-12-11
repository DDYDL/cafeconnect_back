package com.kong.cc.service;

import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.entity.Ask;
import com.kong.cc.entity.Complain;
import com.kong.cc.entity.Member;
import com.kong.cc.entity.Notice;
import com.kong.cc.entity.Store;
import com.kong.cc.repository.AskDslRepository;
import com.kong.cc.repository.AskRepository;
import com.kong.cc.repository.ComplainDslRepository;
import com.kong.cc.repository.ComplainRepository;
import com.kong.cc.repository.NoticeRepository;
import com.kong.cc.repository.StoreRepository;
import com.kong.cc.util.PageInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityMainServiceImpl implements CommunityMainService {

    private final NoticeRepository noticeRepository;
    private final AskDslRepository askDslRepository;
    private final AskRepository askRepository;
    private final ComplainDslRepository complainDslRepository;
    private final ComplainRepository complainRepository;

    @Override
    public List<NoticeDto> noticeListMain() {
        List<Notice> noticeListMain = this.noticeRepository.findAll();

        return noticeListMain.stream().map(Notice::toDto).collect(Collectors.toList());

    }

    @Override
    public NoticeDto noticeDetailMain(Integer noticeNum) throws Exception {
        Notice noticeInfoMain = this.noticeRepository.findByNoticeNum(noticeNum)
                .orElseThrow( () -> new Exception("noticeNum에 해당하는 정보가 없습니다."));
        return noticeInfoMain.toDto();

    }

    // 24.12.02 pporrorro 수정
    @Override
    public void noticeWriteMain(NoticeDto noticeDto) {
    	Notice notice = noticeDto.toEntity();
    	System.out.println(notice);
        noticeRepository.save(notice);
    }

	@Override
	public List<AskDto> askListMain(PageInfo page) throws Exception {
		PageRequest pageRequest = PageRequest.of(page.getCurPage()-1, 10);
		List<AskDto> askDtoList = askDslRepository.findAskListByPaging(pageRequest)
				.stream().map(s -> s.toDto()).collect(Collectors.toList());
		Long allCnt = 0L;
        allCnt = askDslRepository.findAskCount();

        Integer allPage = (int)(Math.ceil(allCnt.doubleValue()/pageRequest.getPageSize()));
		Integer startPage = (page.getCurPage()-1)/10*10+1;
		Integer endPage = Math.min(startPage+10-1, allPage);

		page.setAllPage(allPage);
		page.setStartPage(startPage);
		page.setEndPage(endPage);
		page.setAllCnt(allCnt);

		return askDtoList;
	}

	@Override
	public AskDto askDetailMain(Integer askNum) throws Exception {
		Ask ask = askRepository.findById(askNum).orElseThrow(()->new Exception("문의번호 오류"));
		return ask.toDto();
	}

	@Override
	public Integer addAskAnswerMain(AskDto askDto) throws Exception {
//		Ask ask = askRepository.findById(askNum).orElseThrow(()->new Exception("문의 번호 오류"));
		Ask ask = askDto.toEntity();
		ask.setAskAnswerDate(new Date(System.currentTimeMillis()));
		ask.setAskStatus("1");
		askRepository.save(ask);
		return ask.getAskNum();
	}

	@Override
	public List<ComplainDto> complainListMain(PageInfo page) throws Exception {
		PageRequest pageRequest = PageRequest.of(page.getCurPage()-1, 10);
		List<ComplainDto> complainDtoList = complainDslRepository.findComplainListByPaging(pageRequest)
				.stream().map(s -> s.toDto()).collect(Collectors.toList());
		Long allCnt = 0L;
        allCnt = complainDslRepository.findComplainCount();

        Integer allPage = (int)(Math.ceil(allCnt.doubleValue()/pageRequest.getPageSize()));
		Integer startPage = (page.getCurPage()-1)/10*10+1;
		Integer endPage = Math.min(startPage+10-1, allPage);

		page.setAllPage(allPage);
		page.setStartPage(startPage);
		page.setEndPage(endPage);
		page.setAllCnt(allCnt);

		return complainDtoList;
	}

	@Override
	public ComplainDto complainDetailMain(Integer complainNum) throws Exception {
		Complain complain = complainRepository.findById(complainNum).orElseThrow(()->new Exception("컴플레인넘버 오류"));
		return complain.toDto();
	}

	@Override
	public Integer addComplainCommentMain(ComplainDto complainDto) throws Exception {
//		Complain complain = complainRepository.findById(complainDto.getComplainNum()).orElseThrow(()->new Exception("컴플레인넘버 오류"));
		Complain complain = complainDto.toEntity();
		complain.setComplainAnswerDate(new Date(System.currentTimeMillis()));
		complain.setComplainStatus(true);
		complainRepository.save(complain);
		return complain.getComplainNum();
	}




    };