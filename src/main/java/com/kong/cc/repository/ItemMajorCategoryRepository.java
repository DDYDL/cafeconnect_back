package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.ItemMajorCategory;

public interface ItemMajorCategoryRepository extends JpaRepository<ItemMajorCategory, Integer> {

    ItemMajorCategory findByItemCategoryName(String itemCategoryName);

}
