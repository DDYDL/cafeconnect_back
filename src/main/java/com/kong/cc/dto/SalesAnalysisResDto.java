package com.kong.cc.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesAnalysisResDto {

//    private Integer year;
    private List<SalesAnnualDto> salesAnnualDto;
    private List<SalesQuarterlyDto> salesQuarterlyDto;
    private List<SalesMonthlyDto> salesMonthlyDto;
    private List<SalesCustomDto> salesCustomDto;

}
