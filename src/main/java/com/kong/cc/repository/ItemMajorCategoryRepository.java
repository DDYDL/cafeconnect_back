package com.kong.cc.repository;

import com.kong.cc.entity.ItemMajorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemMajorCategoryRepository extends JpaRepository<ItemMajorCategory, Integer> {

    ItemMajorCategory findByItemCategoryName(String itemCategoryName);

    //상민
    Optional<ItemMajorCategory> findByItemCategoryNum(Integer subCategoryNum);

}
