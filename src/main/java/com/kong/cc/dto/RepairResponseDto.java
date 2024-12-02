package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import com.kong.cc.entity.Item;
import com.kong.cc.entity.Repair;
import com.kong.cc.entity.Store;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepairResponseDto {

    private Integer repairNum;

    private String repairType;
    private String repairTitle;
    private String repairContent;
    private Date repairDate;
    private String repairStatus;
    private String repairAnswer;
    private Date repairAnswerDate;
    private String storeName;
    private Integer storeCode;
    private String itemCode;
    private String itemName;
    private String itemCategoryMajorName;
    private String itemCategoryMiddleName;
    private String itemCategorySubName;
    
    public Repair toEntity() {
    	return Repair.builder()
    			.repairType(repairType)
    			.repairContent(repairContent)
    			.storeR(Store.builder().storeCode(storeCode).build())
    			.itemR(Item.builder().itemCode(itemCode).build())
    			.build();
    	
    }  
}
