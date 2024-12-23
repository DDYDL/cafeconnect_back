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
import com.kong.cc.repository.ItemRepository;
import com.kong.cc.repository.RepairQuerydslRepositoryImpl;
import com.kong.cc.repository.RepairRepository;
import com.kong.cc.repository.StoreRepository;
import com.kong.cc.util.PageInfo;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RepairService {

    private final RepairRepository repairRepository;
    private final StoreRepository storeRepository;
    private final RepairQuerydslRepositoryImpl repairQuerydslRepository;
    private final ItemRepository itemRepository;
    

    public RepairResponseDto selectRepairByRepairNum(Integer repairNum) {
        Repair repair = repairRepository.findByRepairNum(repairNum);
        if(repair == null){
            throw new IllegalArgumentException("해당하는 수리내역이 없습니다");

        }
        String storeName = repair.getStoreR() != null ? repair.getStoreR().getStoreName() : null;
        Integer storeCode = repair.getStoreR() != null ? repair.getStoreR().getStoreCode() : null;
        Item item = repair.getItemR();
        String itemCode = item != null ? item.getItemCode() : null;
        String itemCategoryMajorName=null;
        String itemCategoryMiddleName=null;
        String itemCategorySubName=null;
        if(item != null){
            itemCategoryMajorName = item.getItemMajorCategory() != null ? item.getItemMajorCategory().getItemCategoryName() : null;
            itemCategoryMiddleName = item.getItemMiddleCategory() != null ? item.getItemMiddleCategory().getItemCategoryName() : null;
            itemCategorySubName = item.getItemSubCategory() != null ? item.getItemSubCategory().getItemCategoryName() : null;
        }


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
                .storeCode(storeCode)
                .itemCode(itemCode)
                .itemCategoryMajorName(itemCategoryMajorName)
                .itemCategoryMiddleName(itemCategoryMiddleName)
                .itemCategorySubName(itemCategorySubName)
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
    public List<RepairResponseDto> selectAllRepairRequestList(Integer storeCode,String type,String word,PageInfo pageInfo) {	
    	PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 10);
    	List<RepairResponseDto> repairRequestList = null;
    	Long allCnt = 0L;
    	
    	//전체 조회
    	if(word == null || word.trim().equals("")) {
    		repairRequestList=repairQuerydslRepository.selectRepairRequestOfStore(storeCode,pageRequest);
    		allCnt = repairQuerydslRepository.findRepairRequestCount(storeCode);
    	
    	//검색 조건 조회 
    	}else {
    		repairRequestList=repairQuerydslRepository.selectSearchRepairRequestOfStore(storeCode,pageRequest,type,word);
    		allCnt = repairQuerydslRepository.findSearchRepairRequestCount(storeCode,type,word);
    	}
    	Integer allPage = (int) (Math.ceil(allCnt.doubleValue() / pageRequest.getPageSize()));
		Integer startPage = (pageInfo.getCurPage() - 1) / 10 * 10 + 1;
		Integer endPage = Math.min(startPage + 10 - 1, allPage);

		pageInfo.setAllPage(allPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
    	
    	return  repairRequestList; 
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
    	
    	repairForm.setRepairStatus("접수");
    	//가맹점 조회
    	Store store = storeRepository.findById(repairForm.getStoreCode()).orElseThrow(()->new Exception("가맹점 조회 실패"));
    	//아이템조회
    	 Item item = itemRepository.findById(repairForm.getItemCode()).orElseThrow(() -> new Exception("상품 조회 실패"));
    
    	return repairRepository.save(repairForm.toEntity());
    
    	
    }

    public void updateStateRepair(Integer repairNum,RepairUpdateForm repairUpdateForm) {
        Repair findRepair = repairRepository.findByRepairNum(repairNum);
        findRepair.setRepairAnswer(repairUpdateForm.getRepairAnswer());
        findRepair.setRepairStatus(repairUpdateForm.getRepairStatus());
        findRepair.setRepairAnswerDate(new Date(System.currentTimeMillis()));

    }
}
