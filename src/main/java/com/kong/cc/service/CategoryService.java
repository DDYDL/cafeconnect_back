package com.kong.cc.service;

import com.kong.cc.dto.ItemMajorCategoryForm;
import com.kong.cc.dto.ItemMiddleCategoryForm;
import com.kong.cc.entity.ItemMajorCategory;
import com.kong.cc.entity.ItemMiddleCategory;
import com.kong.cc.entity.ItemSubCategory;
import com.kong.cc.repository.ItemMajorCategoryRepository;
import com.kong.cc.repository.ItemMiddleCategoryRepository;
import com.kong.cc.repository.ItemSubCategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryService {

    private final ItemMajorCategoryRepository itemMajorCategoryRepository;
    private final ItemMiddleCategoryRepository itemMiddleCategoryRepository;
    private final ItemSubCategoryRepository itemSubCategoryRepository;

    public ItemMajorCategory saveMajorCategory(ItemMajorCategoryForm itemMajorCategoryForm){

        ItemMajorCategory itemMajorCategory = ItemMajorCategory.builder()
                .itemCategoryName(itemMajorCategoryForm.getItemCategoryName())
                .build();

        return itemMajorCategoryRepository.save(itemMajorCategory);
    }

    public ItemMiddleCategory saveMiddleCategory(ItemMiddleCategoryForm itemMiddleCategoryForm){
        return null;

    }

    public ItemSubCategory saveSubCategory(){
        return null;
    }




}
