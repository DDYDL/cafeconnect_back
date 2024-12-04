package com.kong.cc.controller;

import com.kong.cc.dto.AskDto;
import com.kong.cc.dto.ComplainDto;
import com.kong.cc.dto.NoticeDto;
import com.kong.cc.dto.StoreDto;
import com.kong.cc.service.CommunityMainService;
import com.kong.cc.util.PageInfo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommunityMainController {

    private final CommunityMainService communityMainService;
	
    @GetMapping("/askListMain")  	 	// AskListMain.js
	public ResponseEntity<Map<String,Object>> askListMain(@RequestParam(value="page", required=false, defaultValue = "1") Integer page) {
		try {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurPage(page);
			List<AskDto> askDtoList = communityMainService.askListMain(pageInfo);
			System.out.println(askDtoList);
			Map<String,Object> listInfo = new HashMap<>();
			listInfo.put("askList", askDtoList);
			listInfo.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(listInfo, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}

    @GetMapping("/askDetailMain/{askNum}")  // ComplainDetailMain.js
	public ResponseEntity<Map<String,Object>> askDetailMain(@PathVariable(value="askNum") Integer askNum) {
		try {
			System.out.println("askDetail");
			System.out.println(askNum);
			Map<String, Object> res = new HashMap<>();
			AskDto askDto = communityMainService.askDetailMain(askNum);
			res.put("ask", askDto);
			return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
    
    @PostMapping("/askAnswerMain") 			// AskDetailMain.js
    public ResponseEntity<String> addAskCommentMain(@RequestBody AskDto askDto) {
    	try {
    		System.out.println("askAnswer");
    		System.out.println(askDto);
    		Integer askNum = communityMainService.addAskAnswerMain(askDto);
    		return new ResponseEntity<String>(String.valueOf(askNum), HttpStatus.OK);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    	}
    }
    

    @GetMapping("/complainListMain")   // ComplainListMain.js
    public ResponseEntity<Map<String,Object>> complainListMain(@RequestParam(value="page", required=false, defaultValue = "1") Integer page) {
		try {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurPage(page);
			List<ComplainDto> complainDtoList = communityMainService.complainListMain(pageInfo);
			System.out.println(complainDtoList);
			Map<String,Object> listInfo = new HashMap<>();
			listInfo.put("complainList", complainDtoList);
			listInfo.put("pageInfo", pageInfo);
			return new ResponseEntity<Map<String,Object>>(listInfo, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
    
    
  @GetMapping("/complainDetailMain/{complainNum}")  // ComplainDetailMain.js
	public ResponseEntity<Map<String,Object>> complainDetailMain(@PathVariable(value="complainNum") Integer complainNum) {
		try {
			System.out.println("complainDetail");
			System.out.println(complainNum);
			Map<String, Object> res = new HashMap<>();
			ComplainDto complainDto = communityMainService.complainDetailMain(complainNum);
			res.put("complain", complainDto);
			return new ResponseEntity<Map<String,Object>>(res, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
	}
  @PostMapping("/addComplainAnswer")  // ComplainDetailMain.js 
  public ResponseEntity<String> addComplainAnswer(@RequestBody ComplainDto complainDto) {
	  try {
		  communityMainService.addComplainCommentMain(complainDto);
		  return new ResponseEntity<String>(String.valueOf(complainDto.getComplainNum()), HttpStatus.OK);
	  } catch(Exception e) {
		  e.printStackTrace();
		  return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	  }
  }
    
    
//  @PostMapping("/writeNotice") 		// WriteNotice.js
  
  	@GetMapping("/modifyNotice") 		// ModifyNotice.js
    public ResponseEntity<NoticeDto> modifyNoticeMain(@PathVariable Integer noticeNum) {
        try {
            NoticeDto noticeMainDto = communityMainService.noticeDetailMain(noticeNum);
            return new ResponseEntity<>(noticeMainDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
  	
  	@PostMapping("/modifyNotice") 		// ModifyNotice.js
  	 public ResponseEntity<String> modifyNoticeMain(@RequestBody NoticeDto noticeDto) {
        try {
            this.communityMainService.noticeWriteMain(noticeDto);
            return new ResponseEntity<>("공지가 수정되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("notice작성 실패");
            e.printStackTrace();
            return new ResponseEntity<>("공지 수정에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }
  	
    //공지사항 리스트 (본사)
    @GetMapping("/noticeListMain") // NoticeListMain.js
    public ResponseEntity<List<NoticeDto>> noticeListMain() {
        try {
            List<NoticeDto> noticeMainDtoList = communityMainService.noticeListMain();
            return new ResponseEntity<>(noticeMainDtoList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 공지사항 상세 (본사)
    @GetMapping("/noticeDetailMain/{noticeNum}") // NoticeMainDetail.js
    public ResponseEntity<NoticeDto> noticeDetailMain(@PathVariable Integer noticeNum) {
        try {
            NoticeDto noticeMainDto = communityMainService.noticeDetailMain(noticeNum);
            return new ResponseEntity<>(noticeMainDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 공지사항 작성 (본사)
    @PostMapping("/noticeWriteMain")
    public ResponseEntity<String> noticeWriteMain(@RequestBody NoticeDto noticeDto) {
        try {
            System.out.println("notice작성 성공");
            communityMainService.noticeWriteMain(noticeDto);
            return new ResponseEntity<>("공지가 등록되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("notice작성 실패");
            e.printStackTrace();
            return new ResponseEntity<>("공지 등록에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
