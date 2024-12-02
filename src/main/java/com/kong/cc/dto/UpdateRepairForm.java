package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class UpdateRepairForm {

    private String repairAnswer;
    private Date repairAnswerDate;
    private String repairStatus;
}
