package com.kong.cc.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.kong.cc.entity.ImageFile;
import com.kong.cc.entity.MenuCategory;
import com.kong.cc.entity.Sales;

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
	private Integer menuFileNum; 
}
