package com.kong.cc.dto;

import com.kong.cc.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSubCategoryForm {

	private Integer itemCategoryNum;
    private String itemCategoryName;
    
    private String itemCategoryMiddleName;
    private Integer itemCategoryMiddleNum;

    private List<Item> itemList = new ArrayList<>();

}
