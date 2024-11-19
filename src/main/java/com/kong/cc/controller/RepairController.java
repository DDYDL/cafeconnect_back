package com.kong.cc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepairController {

//  @PostMapping("/repairRequestList") 	//RepairRequestList.js 
//  @GetMapping("/repairRequestDetail) // RepairRequestDetail.js
//	@PostMapping("/writeRepairRequest") //RepairRequestForm.js
//	@GetMapping("/machineList) //RepairRequestForm.js
	
	
	@GetMapping("/repairListByKeyword")  //RepairList.js
    public ResponseEntity<Object> repairListByKeyword(String keyword){
        return null;
    }

    @GetMapping("/repairListByCategory") //RepairList.js
    public ResponseEntity<Object> repairListByCategory(Integer categoryNum){
        return null;
    }

    @GetMapping("/machineList")  //RepairList.js
    public ResponseEntity<Object> machineList(){
        return null;
    }

    @GetMapping("selectRepairByRepairNum/{repairNum}")  //RepairDetail.js
    public ResponseEntity<Object> selectRepairByRepairNum(@PathVariable Integer repairNum){
        return null;
    }

    @PostMapping("updateRepair/{repairNum}")  //RepairDetail.js
    public ResponseEntity<Object> updateRepair(@PathVariable Integer repairNum){
        return null;
    }
}
