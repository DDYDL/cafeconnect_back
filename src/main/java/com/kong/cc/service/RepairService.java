package com.kong.cc.service;

import com.kong.cc.dto.RepairDto;
import com.kong.cc.entity.Repair;
import com.kong.cc.repository.RepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RepairService {

    private final RepairRepository repairRepository;

    Repair selectRepairByRepairNum(Integer repairNum){
        return repairRepository.findByrepairNum(repairNum);
    }


}
