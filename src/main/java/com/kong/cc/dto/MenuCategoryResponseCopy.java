package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryResponseCopy {
    private Integer categoryNum;
    private String categoryName;
    private String categoryValue;
}
