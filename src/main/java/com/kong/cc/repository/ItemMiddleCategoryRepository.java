package com.kong.cc.repository;

import com.kong.cc.entity.ItemMiddleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemMiddleCategoryRepository extends JpaRepository<ItemMiddleCategory, Integer> {

    ItemMiddleCategory findByItemCategoryName(String itemCategoryName);

    List<ItemMiddleCategory> findByItemMajorCategoryMd_ItemCategoryNum(Integer itemCategoryMajorNum);

    //상민
    Optional<ItemMiddleCategory> findByItemCategoryNum(Integer middleCategoryNum);
}
