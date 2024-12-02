package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryResponse {

    private String categoryName;
    private Integer categoryNum;
}
