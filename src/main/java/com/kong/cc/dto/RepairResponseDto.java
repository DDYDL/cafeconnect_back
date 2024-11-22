package com.kong.cc.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
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
    private String itemCode;
    private String itemName;
    private String itemCategoryMajorName;
    private String itemCategoryMiddleName;
}
