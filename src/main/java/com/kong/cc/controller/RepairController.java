package com.kong.cc.controller;

import com.kong.cc.dto.ItemResponseDto;
import com.kong.cc.dto.RepairResponseDto;
import com.kong.cc.dto.RepairSearchCondition;
import com.kong.cc.dto.RepairUpdateForm;
import com.kong.cc.service.RepairService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RepairController {

//  @PostMapping("/repairRequestList") 	//RepairRequestList.js 
//  @GetMapping("/repairRequestDetail) // RepairRequestDetail.js
//	@PostMapping("/writeRepairRequest") //RepairRequestForm.js
//	@GetMapping("/machineList) //RepairRequestForm.js
	
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

    @GetMapping("/machineList")  //RepairList.js
    public ResponseEntity<Object> machineList(){
        return null;
    }

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
}
