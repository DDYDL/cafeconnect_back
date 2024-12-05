package com.kong.cc.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesWriteDto {
    private Date salesDate;
    private Integer storeCode;
    private List<SalesDto> salesList;
}
