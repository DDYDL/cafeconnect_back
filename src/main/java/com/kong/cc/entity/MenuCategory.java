package com.kong.cc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.kong.cc.dto.MenuCategoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MenuCategory {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer menuCategoryNum;
	private String menuCategoryName;
	
	@OneToMany(mappedBy="menuCategory", fetch=FetchType.LAZY)
	private List<Menu> menuList = new ArrayList<>();
	
	public MenuCategoryDto toDto() {
		return MenuCategoryDto.builder()
				.menuCategoryNum(menuCategoryNum)
				.menuCategoryName(menuCategoryName)
				.build();
	}
}
