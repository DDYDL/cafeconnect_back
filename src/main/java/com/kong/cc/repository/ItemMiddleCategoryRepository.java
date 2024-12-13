package com.kong.cc.repository;

import com.kong.cc.dto.CategoryResponse;
import com.kong.cc.dto.CategoryResponseCopy;
import com.kong.cc.entity.ItemMiddleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemMiddleCategoryRepository extends JpaRepository<ItemMiddleCategory, Integer> {

    ItemMiddleCategory findByItemCategoryName(String itemCategoryName);

    List<ItemMiddleCategory> findByItemMajorCategoryMd_ItemCategoryNum(Integer itemCategoryMajorNum);

    //상민
    Optional<ItemMiddleCategory> findByItemCategoryNum(Integer middleCategoryNum);

    @Query("select new com.kong.cc.dto.CategoryResponse(m.itemCategoryName,m.itemCategoryNum) from ItemMiddleCategory m join m.itemMajorCategoryMd n  where n.itemCategoryName = :categoryName")
    List<CategoryResponse> findAllCategory(@Param("categoryName") String categoryName);
    @Query("select new com.kong.cc.dto.CategoryResponseCopy(m.itemCategoryNum,m.itemCategoryName,m.itemCategoryName) from ItemMiddleCategory m join m.itemMajorCategoryMd n  where n.itemCategoryName = :categoryName")
    List<CategoryResponseCopy> findAllMiddleCategoryCopy(@Param("categoryName") String categoryName);
    @Query("select new com.kong.cc.dto.CategoryResponseCopy(m.itemCategoryNum,m.itemCategoryName,m.itemCategoryName) from ItemMiddleCategory m ")
    List<CategoryResponseCopy> findAllMiddleCategoryCopy();
    @Query("select m from ItemMiddleCategory m where m.itemCategoryName = :categoryName")
    ItemMiddleCategory checkMiddleCategory(@Param("categoryName") String categoryName);
}
