package com.kong.cc.service;


import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;

import java.util.List;

public interface CommunityMainService {


    List<NoticeDto> noticeListMain() throws Exception;

    NoticeDto noticeDetailMain(Integer noticeNum) throws Exception;

    void noticeWriteMain(NoticeDto noticeDto) throws Exception;
}
