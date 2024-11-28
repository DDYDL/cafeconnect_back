package com.kong.cc.service;


import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommunityService {


    List<NoticeDto> noticeList() throws Exception;

    NoticeDto noticeDetail(Integer noticeNum) throws Exception;

    List<AskDto> askList() throws Exception;

    void saveAnswer(Integer askNum, AskDto askDto) throws Exception;

    void deleteAnswer(Integer askNum) throws Exception;

    void askWrite(AskDto askDto) throws Exception;

    List<ComplainDto> complainList() throws Exception;
}
