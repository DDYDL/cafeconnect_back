package com.kong.cc.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.kong.cc.entity.Cart;
import com.kong.cc.entity.ImageFile;
import com.kong.cc.entity.ItemMajorCategory;
import com.kong.cc.entity.ItemMiddleCategory;
import com.kong.cc.entity.ItemSubCategory;
import com.kong.cc.entity.ShopOrder;
import com.kong.cc.entity.Repair;
import com.kong.cc.entity.Stock;
import com.kong.cc.entity.WishItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
	private String itemCode;
	
	private String itemName;
	private Integer itemPrice;
	private String itemCapacity;
	private Integer itemUnitQuantity;
	private String itemUnit;
	private String itemStandard;
	private String itemStorage;
	private String itemCountryOrigin;

	private Integer itemMajorCategoryNum;
	private Integer itemMiddleCategoryNum;
	private Integer itemSubCategoryNum;
	private Integer itemFileNum;
}
