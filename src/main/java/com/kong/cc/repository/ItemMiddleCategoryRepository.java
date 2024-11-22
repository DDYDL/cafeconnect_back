package com.kong.cc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.ItemMiddleCategory;

public interface ItemMiddleCategoryRepository extends JpaRepository<ItemMiddleCategory, Integer> {

    ItemMiddleCategory findByItemCategoryName(String itemCategoryName);

    List<ItemMiddleCategory> findByItemMajorCategoryMd_ItemCategoryNum(Integer itemCategoryMajorNum);
}
