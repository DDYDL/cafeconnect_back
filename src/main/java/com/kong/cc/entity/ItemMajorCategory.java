package com.kong.cc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.kong.cc.dto.ItemMajorCategoryForm;

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
public class ItemMajorCategory {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemCategoryNum;
	private String itemCategoryName;
	
	@OneToMany(mappedBy="itemMajorCategory", fetch=FetchType.LAZY)
	private List<Item> itemList = new ArrayList<>();
	
	@OneToMany(mappedBy="itemMajorCategoryMd", fetch=FetchType.LAZY)
	private List<ItemMiddleCategory> itemMiddelCategoryList = new ArrayList<>();
	

	public ItemMajorCategoryForm toDto() {
		
		
		return ItemMajorCategoryForm.builder()
				.itemCategoryNum(itemCategoryNum)
				.itemCategoryName(itemCategoryName)
				.midCategories(itemMiddelCategoryList.stream().map(ItemMiddleCategory::toDto).collect(Collectors.toList()))
				.build();
	}
	
	
}
