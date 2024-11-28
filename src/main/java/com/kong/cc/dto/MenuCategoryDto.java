package com.kong.cc.dto;

import com.kong.cc.entity.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCategoryDto {
	private Integer menuCategoryNum;
	private String menuCategoryName;
	
	public MenuCategory toEntity() {
		return MenuCategory.builder()
				.menuCategoryNum(menuCategoryNum)
				.menuCategoryName(menuCategoryName)
				.build();
	}
}
