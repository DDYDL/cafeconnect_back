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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}
