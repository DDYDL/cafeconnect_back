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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.kong.cc.dto.ItemMiddleCategoryForm;

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
public class ItemMiddleCategory {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemCategoryNum;
	private String itemCategoryName;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemCategoryMajorNum")
	private ItemMajorCategory itemMajorCategoryMd;

	@OneToMany(mappedBy="itemMiddleCategory", fetch=FetchType.LAZY)
	private List<Item> itemList = new ArrayList<>();
	
	@OneToMany(mappedBy="itemMiddleCategorySb", fetch=FetchType.LAZY)
	private List<ItemSubCategory> itemSubCategoryList = new ArrayList<>();

	
	public ItemMiddleCategoryForm toDto() {
		
		return ItemMiddleCategoryForm.builder()
				.itemCategoryNum(itemCategoryNum)
				.itemCategoryName(itemCategoryName)
				.itemCategoryMajorName(itemMajorCategoryMd.getItemCategoryName())
				.itemCategoryMajorNum(itemMajorCategoryMd.getItemCategoryNum())
				.subCategories(itemSubCategoryList.stream().map(ItemSubCategory::toDto).collect(Collectors.toList()))
				.build();
	}
	
	
}
