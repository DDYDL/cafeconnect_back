package com.kong.cc.controller;

import java.util.List;
import java.util.Map;

import com.kong.cc.dto.UpdateRepairForm;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kong.cc.dto.RepairResponseDto;
import com.kong.cc.dto.RepairSearchCondition;
import com.kong.cc.dto.RepairUpdateForm;
import com.kong.cc.service.RepairService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RepairController {


	private final RepairService repairService;

	@GetMapping("/repairListByKeyword")  //RepairList.js
    public ResponseEntity<Object> repairListByKeyword(Integer pageNum, Integer pageSize, String keyword){

        try{
            Page<RepairResponseDto> page = repairService.repairListByKeyword(pageNum,pageSize,keyword);
            return new ResponseEntity<>(page,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/repairListByCategory") //RepairList.js
    public ResponseEntity<Object> repairListByCategory(Integer pageNum, Integer pageSize,@ModelAttribute RepairSearchCondition condition){
        try{
            Page<RepairResponseDto> page = repairService.repairListByCategory(pageNum,pageSize,condition);
            return new ResponseEntity<>(page,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/machineList")  //RepairList.js
//    public ResponseEntity<Object> machineList(){
//        return null;
//    }

    @GetMapping("selectRepairByRepairNum/{repairNum}")  //RepairDetail.js
    public ResponseEntity<RepairResponseDto> selectRepairByRepairNum(@PathVariable Integer repairNum){
        try{
            RepairResponseDto repairResponseDto = repairService.selectRepairByRepairNum(repairNum);
            return new ResponseEntity<>(repairResponseDto, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("updateRepair/{repairNum}")  //RepairDetail.js
    public ResponseEntity<Object> updateRepair(@PathVariable Integer repairNum,
                                               RepairUpdateForm repairUpdateForm){
        try{
            repairService.updateRepair(repairNum,repairUpdateForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // 가맹점 시작
    // 가맹점 수리 요청 리스트 
    @GetMapping("/repairRequestList") 	//RepairRequestList.js 
    public ResponseEntity<List<RepairResponseDto>> selectRepairRequestList(@RequestParam Integer storeCode) {
    	try {
    		List<RepairResponseDto> result = repairService.selectAllRepairRequestList(storeCode); 
			return new ResponseEntity<List<RepairResponseDto>>(result,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<RepairResponseDto>>(HttpStatus.BAD_REQUEST);
			
		}
    }
    // 가맹점 수리 요청 상세 내역
    @GetMapping("/repairRequestDetail/{repairNum}") // RepairRequestDetail.js
    public ResponseEntity<RepairResponseDto> selectOneRepairRequest(@PathVariable Integer repairNum) {

    	try {
    		
    		RepairResponseDto result = repairService.selectRepairByRepairNum(repairNum); 
			return new ResponseEntity<RepairResponseDto>(result,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<RepairResponseDto>(HttpStatus.BAD_REQUEST);
			
		}
    }
    
    //수리 신청 폼에서 기기 조회(자동완성) - 대분류가 "머신"인 상품 가져오기(코드,이름을 가지고 내려보냄) 
    @GetMapping("/machineFormList") //RepairRequestForm.js
    public ResponseEntity<List<Map<String, Object>>>selectAllMachineList(){
        try {
			List<Map<String, Object>> result = repairService.selectAllMachineList();
        	System.out.println(result.toString());
			return new ResponseEntity<List<Map<String, Object>>>(result,HttpStatus.OK);	
			
        } catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.BAD_REQUEST);
		}
    }
    
    //가맹점 수리 신청
	@PostMapping("/writeRepairRequest") //RepairRequestForm.js
	public ResponseEntity<String> writeRepairRequestFormByStore(RepairResponseDto repairForm) {
			Boolean result= false;
    	try {
    		if(repairService.insertWriteNewRepairForm(repairForm)!=null) {
    			result = true;
    		}
			return new ResponseEntity<String>(String.valueOf(result),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

	}

    @PostMapping("/updateStateRepair/{repairNum}")
    public ResponseEntity<Object> updateStateRepair(@PathVariable Integer repairNum, @RequestPart RepairUpdateForm repairUpdateForm){
        try {
            repairService.updateStateRepair(repairNum,repairUpdateForm);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
	
    
    
    
    
    
    
}
