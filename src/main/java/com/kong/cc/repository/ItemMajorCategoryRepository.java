package com.kong.cc.repository;

import com.kong.cc.dto.CategoryResponse;
import com.kong.cc.dto.CategoryResponseCopy;
import com.kong.cc.entity.ItemMajorCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemMajorCategoryRepository extends JpaRepository<ItemMajorCategory, Integer> {

    ItemMajorCategory findByItemCategoryName(String itemCategoryName);

    //상민
    Optional<ItemMajorCategory> findByItemCategoryNum(Integer subCategoryNum);

    @Query("select new com.kong.cc.dto.CategoryResponse(m.itemCategoryName,m.itemCategoryNum) from ItemMajorCategory m")
    List<CategoryResponse> findAllCategory();
    @Query("select new com.kong.cc.dto.CategoryResponseCopy(m.itemCategoryNum,m.itemCategoryName,m.itemCategoryName) from ItemMajorCategory m")
    List<CategoryResponseCopy> findAllMajorCategoryCopy();

    @Query("select m from ItemMajorCategory m where m.itemCategoryName = :categoryName")
    ItemMajorCategory checkMajorCategory(@Param("categoryName") String categoryName);
}
