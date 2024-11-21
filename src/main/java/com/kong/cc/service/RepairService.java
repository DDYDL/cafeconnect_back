package com.kong.cc.service;

import com.kong.cc.dto.RepairDto;
import com.kong.cc.dto.RepairResponseDto;
import com.kong.cc.dto.RepairUpdateForm;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.Repair;
import com.kong.cc.repository.RepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RequiredArgsConstructor
@Transactional
@Service
public class RepairService {

    private final RepairRepository repairRepository;


    public RepairResponseDto selectRepairByRepairNum(Integer repairNum) {
        Repair repair = repairRepository.findByRepairNum(repairNum);
        if(repair == null){
            throw new IllegalArgumentException("해당하는 수리내역이 없습니다");

        }
        String storeName = repair.getStoreR().getStoreName();
        Item item = repair.getItemR();
        String itemCode = item.getItemCode();
        String itemCategoryMajorName = item.getItemMajorCategory().getItemCategoryName();
        String itemCategoryMiddleName = item.getItemMiddleCategory().getItemCategoryName();

        return RepairResponseDto.builder()
                .repairNum(repair.getRepairNum())
                .repairType(repair.getRepairType())
                .repairTitle(repair.getRepairTitle())
                .repairContent(repair.getRepairContent())
                .repairDate(repair.getRepairDate())
                .repairStatus(repair.getRepairStatus())
                .repairAnswer(repair.getRepairAnswer())
                .repairAnswerDate(repair.getRepairAnswerDate())
                .storeName(storeName)
                .itemCode(itemCode)
                .itemCategoryMajorName(itemCategoryMajorName)
                .itemCategoryMiddleName(itemCategoryMiddleName)
                .build();
    }

    public void updateRepair(Integer repairNum, RepairUpdateForm repairUpdateForm) {
        Repair repair = repairRepository.findByRepairNum(repairNum);
        if(repair == null) {
            throw new IllegalArgumentException("해당하는 수리내역이 없습니다");
        }
        repair.setRepairAnswer(repairUpdateForm.getRepairAnswer());
        repair.setRepairAnswerDate(new Date());
        repair.setRepairStatus(repairUpdateForm.getRepairStatus());

    }
}
