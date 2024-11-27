package com.kong.cc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.ItemExpenseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {
	@Id
	private String itemCode;
	
	private String itemName;
	private Integer itemPrice;
	private String itemCapacity;
	private Integer itemUnitQuantity;
	private String itemUnit;
	private String itemStandard;
	private String itemStorage;
	private String itemCountryOrigin;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemMajorCategoryNum")
	private ItemMajorCategory itemMajorCategory;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemMiddleCategoryNum")
	private ItemMiddleCategory itemMiddleCategory;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemSubCategoryNum")
	private ItemSubCategory itemSubCategory;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="itemFileNum")
	private ImageFile itemImageFile;
	
	@OneToMany(mappedBy="itemR", fetch=FetchType.LAZY)
	private List<Repair> repairList = new ArrayList<>();
	
	@OneToMany(mappedBy="itemW", fetch=FetchType.LAZY)
	private List<WishItem> wishItemList = new ArrayList<>();
	
	@OneToMany(mappedBy="itemCa", fetch=FetchType.LAZY)
	private List<Cart> cartList = new ArrayList<>();
	
	@OneToMany(mappedBy="itemO", fetch=FetchType.LAZY)
	private List<ShopOrder> orderList = new ArrayList<>();
	
	@OneToMany(mappedBy="itemS", fetch=FetchType.LAZY)
	private List<Stock> stockList = new ArrayList<>();
	
	public ItemDto toDto() {
	    return ItemDto.builder()
	            .itemCode(itemCode)
	            .itemName(itemName)
	            .itemPrice(itemPrice)
	            .itemCapacity(itemCapacity)
	            .itemUnitQuantity(itemUnitQuantity)
	            .itemUnit(itemUnit)
	            .itemStandard(itemStandard)
	            .itemStorage(itemStorage)
	            .itemCountryOrigin(itemCountryOrigin)
	            .itemMajorCategoryNum(itemMajorCategory != null ? itemMajorCategory.getItemCategoryNum() : null)
	            .itemMajorCategoryName(itemMajorCategory != null ? itemMajorCategory.getItemCategoryName() : null)
	            .itemMiddleCategoryNum(itemMiddleCategory != null ? itemMiddleCategory.getItemCategoryNum() : null)
	            .itemMiddleCategoryName(itemMiddleCategory != null ? itemMiddleCategory.getItemCategoryName() : null)
	            .itemSubCategoryNum(itemSubCategory != null ? itemSubCategory.getItemCategoryNum() : null)
	            .itemSubCategoryName(itemSubCategory != null ? itemSubCategory.getItemCategoryName() : null)
	            .itemFileNum(itemImageFile != null ? itemImageFile.getFileNum() : null)
	            .build();
	}

}
