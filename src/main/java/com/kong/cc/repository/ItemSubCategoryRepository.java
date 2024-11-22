package com.kong.cc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.ItemSubCategory;

public interface ItemSubCategoryRepository extends JpaRepository<ItemSubCategory, Integer> {

    ItemSubCategory findByItemCategoryName(String itemCategoryName);

    List<ItemSubCategory> findByItemMiddleCategorySb_ItemCategoryNum(Integer itemCategoryMiddleNum);

}
