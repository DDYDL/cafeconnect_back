package com.kong.cc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
	
	@OneToMany(mappedBy="ItemMajorCategoryMd", fetch=FetchType.LAZY)
	private List<ItemMiddleCategory> itemMiddelCategoryList = new ArrayList<>();
	
	
}
