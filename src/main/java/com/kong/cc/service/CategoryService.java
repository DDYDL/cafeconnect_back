package com.kong.cc.service;

import com.kong.cc.entity.ItemMajorCategory;
import com.kong.cc.repository.ItemMajorCategoryRepository;
import com.kong.cc.repository.ItemMiddleCategoryRepository;
import com.kong.cc.repository.ItemSubCategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryService {

    private final ItemMajorCategoryRepository itemMajorCategoryRepository;
    private final ItemMiddleCategoryRepository itemMiddleCategoryRepository;
    private final ItemSubCategoryRepository itemSubCategoryRepository;




}
