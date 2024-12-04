package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseCopy {

    private Integer categoryNum;
    private String categoryName;
    private String categoryValue;

}
