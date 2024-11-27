package com.kong.cc.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kong.cc.dto.RepairResponseDto;
import com.kong.cc.dto.RepairSearchCondition;
import com.kong.cc.dto.RepairUpdateForm;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.Repair;
import com.kong.cc.entity.Store;
import com.kong.cc.repository.RepairQuerydslRepositoryImpl;
import com.kong.cc.repository.RepairRepository;
import com.kong.cc.repository.StoreRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RepairService {

    private final RepairRepository repairRepository;
    private final StoreRepository storeRepository;
    private final RepairQuerydslRepositoryImpl repairQuerydslRepository;
    
    

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
        repair.setRepairAnswerDate(new Date(System.currentTimeMillis()));
        repair.setRepairStatus(repairUpdateForm.getRepairStatus());

    }

    public Page<RepairResponseDto> repairListByKeyword(Integer pageNum, Integer pageSize, String keyword) {
        PageRequest pageRequest = PageRequest.of(pageNum , pageSize, Sort.by(Sort.Direction.ASC, "repairNum"));
        return repairQuerydslRepository.findRepairResponseDtoListByKeyword(keyword,pageRequest);

    }

    public Page<RepairResponseDto> repairListByCategory(Integer pageNum, Integer pageSize, RepairSearchCondition condition) {
        PageRequest pageRequest = PageRequest.of(pageNum , pageSize, Sort.by(Sort.Direction.ASC, "repairNum"));
        return repairQuerydslRepository.findRepairResponseDtoListByCategory(condition,pageRequest);
    }
    
    //가맹점 시작
    //수리 요청 리스트 출력
    public List<RepairResponseDto> selectAllRepairRequestList(Integer storeCode) {	
    	return repairQuerydslRepository.selectRepairRequestOfStore(storeCode); 
    }
    //수리 요청 상세 보기 - selectRepairByRepairNum 사용   
    public List<Map<String,Object>>selectAllMachineList() {
    	List<Tuple> tuple = repairQuerydslRepository.selectAllMachineInfoList();
    
    	return tuple.stream().map(t->
    	{
    		Map<String,Object>result = new HashMap<>();
    		result.put("itemCode", t.get(0, String.class));
    		result.put("itemName", t.get(1, String.class));
    		return result;
    	}).collect(Collectors.toList());
    			
    }
    //수리 신청서 작성
    public Repair insertWriteNewRepairForm(RepairResponseDto repairForm) throws Exception  {
    	
    	//가맹점 조회
    	storeRepository.findById(repairForm.getStoreCode()).orElseThrow(()->new Exception("가맹점 조회 실패"));

    	return repairRepository.save(repairForm.toEntity());
    
    	
    }
   
}
