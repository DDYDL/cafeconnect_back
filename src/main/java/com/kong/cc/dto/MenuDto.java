package com.kong.cc.dto;

import com.kong.cc.entity.Menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto {
	private String menuCode;

	private String menuName;
	private Integer menuPrice;
	private String menuCapacity;
	private String caffeine;
	private String calories;
	private String carbohydrate;
	private String sugar;
	private String natrium;
	private String fat;
	private String protein;
	private String menuStatus;

	private Integer menuCategoryNum;
	private String menuCategoryName;
	private Integer menuFileNum;

	public Menu toEntity() {
		return Menu.builder()
				.menuCode(menuCode)
				.menuName(menuName)
				.menuPrice(menuPrice)
				.menuCapacity(menuCapacity)
				.caffeine(caffeine)
				.calories(calories)
				.carbohydrate(carbohydrate)
				.sugar(sugar)
				.natrium(natrium)
				.fat(fat)
				.protein(protein)
				.menuStatus(menuStatus)
				.build();
	}
}
