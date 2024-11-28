package com.kong.cc.repository;

import com.kong.cc.entity.ItemSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemSubCategoryRepository extends JpaRepository<ItemSubCategory, Integer> {

    ItemSubCategory findByItemCategoryName(String itemCategoryName);

    List<ItemSubCategory> findByItemMiddleCategorySb_ItemCategoryNum(Integer itemCategoryMiddleNum);

    //상민
    Optional<ItemSubCategory> findByItemCategoryNum(Integer subCategoryNum);
}
