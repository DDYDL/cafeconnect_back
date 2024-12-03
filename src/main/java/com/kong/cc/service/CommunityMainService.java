package com.kong.cc.service;


import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;
import com.kong.cc.util.PageInfo;

import java.util.List;

public interface CommunityMainService {
    List<NoticeDto> noticeListMain() throws Exception;

    NoticeDto noticeDetailMain(Integer noticeNum) throws Exception;

    void noticeWriteMain(NoticeDto noticeDto) throws Exception;
    
    List<AskDto> askListMain(PageInfo page) throws Exception;
    AskDto askDetailMain(Integer askNum) throws Exception;
    Integer addAskAnswerMain(AskDto askDto) throws Exception;
    
    List<ComplainDto> complainListMain(PageInfo page) throws Exception;
    ComplainDto complainDetailMain(Integer complainNum) throws Exception;
    Integer addComplainCommentMain(ComplainDto complainDto) throws Exception;
}
