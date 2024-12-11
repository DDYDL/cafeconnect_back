package com.kong.cc.service;


import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;
import java.util.List;

public interface CommunityService {


    List<NoticeDto> noticeList() throws Exception;

    NoticeDto noticeDetail(Integer noticeNum) throws Exception;

    List<AskDto> askList(Integer storeCode) throws Exception;

    void askWrite(AskDto askDto) throws Exception;

    List<ComplainDto> complainList(Integer storeCode) throws Exception;

    ComplainDto complainDetail(Integer complainNum) throws Exception;

    AskDto askAnswer(Integer storeCode, Integer askNum) throws Exception;

    List<NoticeDto> noticeModal() throws Exception;
}
