package com.kong.cc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateMenuCategoryForm {

    private Integer categoryNum;
    private String categoryName;
}
