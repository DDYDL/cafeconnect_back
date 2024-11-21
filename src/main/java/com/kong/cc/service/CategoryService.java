package com.kong.cc.service;

import com.kong.cc.dto.ItemMajorCategoryForm;
import com.kong.cc.dto.ItemMiddleCategoryForm;
import com.kong.cc.dto.ItemSubCategoryForm;
import com.kong.cc.entity.ItemMajorCategory;
import com.kong.cc.entity.ItemMiddleCategory;
import com.kong.cc.entity.ItemSubCategory;
import com.kong.cc.repository.ItemMajorCategoryRepository;
import com.kong.cc.repository.ItemMiddleCategoryRepository;
import com.kong.cc.repository.ItemSubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
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
        String itemCategoryMajorName = itemMiddleCategoryForm.getItemCategoryMajorName();
        String itemCategoryName = itemMiddleCategoryForm.getItemCategoryName();

        ItemMajorCategory itemMajorCategory = itemMajorCategoryRepository.findByItemCategoryName(itemCategoryMajorName);
        if(itemMajorCategory == null){
            throw new IllegalArgumentException("해당하는 상위 카테고리가 없습니다");

        }
        ItemMiddleCategory itemMiddleCategory = ItemMiddleCategory.builder()
                .ItemMajorCategoryMd(itemMajorCategory)
                .itemCategoryName(itemCategoryName)
                .build();
        itemMajorCategory.getItemMiddelCategoryList().add(itemMiddleCategory);

        return itemMiddleCategoryRepository.save(itemMiddleCategory);


    }

    public ItemSubCategory saveSubCategory(ItemSubCategoryForm itemSubCategoryForm){
        String itemCategoryMiddleName = itemSubCategoryForm.getItemCategoryMiddleName();
        String itemCategoryName = itemSubCategoryForm.getItemCategoryName();
        ItemMiddleCategory itemMiddleCategory = itemMiddleCategoryRepository.findByItemCategoryName(itemCategoryMiddleName);
        if(itemMiddleCategory == null){
            throw new IllegalArgumentException("해당하는 상위 카테고리가 없습니다");
        }
        ItemSubCategory itemSubCategory = ItemSubCategory.builder()
                .ItemMiddleCategorySb(itemMiddleCategory)
                .itemCategoryName(itemCategoryName)
                .build();
        itemMiddleCategory.getItemSubCategoryList().add(itemSubCategory);
        return itemSubCategoryRepository.save(itemSubCategory);

    }

    public void deleteMajorCategory(ItemMajorCategoryForm itemMajorCategoryForm){
        String itemCategoryName = itemMajorCategoryForm.getItemCategoryName();
        ItemMajorCategory itemMajorCategory = itemMajorCategoryRepository.findByItemCategoryName(itemCategoryName);
        if(itemMajorCategory == null){
            throw new IllegalArgumentException("해당하는 카테고리가 없습니다");
        }
        if(itemMajorCategory.getItemMiddelCategoryList().size() >0){
            throw new IllegalArgumentException("하위 카테고리가 항목이 있습니다");
        }
        itemMajorCategoryRepository.delete(itemMajorCategory);
    }

    public void deleteMiddleCategory(ItemMiddleCategoryForm itemMiddleCategoryForm){
        String itemCategoryMajorName = itemMiddleCategoryForm.getItemCategoryMajorName();
        String itemCategoryName = itemMiddleCategoryForm.getItemCategoryName();
        ItemMiddleCategory itemMiddleCategory = itemMiddleCategoryRepository.findByItemCategoryName(itemCategoryName);
        ItemMajorCategory itemMajorCategory = itemMajorCategoryRepository.findByItemCategoryName(itemCategoryMajorName);
        if(itemMajorCategory == null){
            throw new IllegalArgumentException("해당하는 상위 카테고리가 없습니다");
        }
        if(itemMiddleCategory == null){
            throw new IllegalArgumentException("해당하는 카테고리가 없습니다");
        }
        if(itemMiddleCategory.getItemSubCategoryList().size() > 0){
            throw new IllegalArgumentException("하위 카테고리의 항목이 있습니다.");
        }

        itemMiddleCategory.getItemSubCategoryList().remove(itemMiddleCategory);
        itemMiddleCategoryRepository.delete(itemMiddleCategory);




    }

    public void deleteSubCategory(ItemSubCategoryForm itemSubCategoryForm ){

        String itemCategoryMiddleName = itemSubCategoryForm.getItemCategoryMiddleName();
        String itemCategoryName = itemSubCategoryForm.getItemCategoryName();
        ItemSubCategory itemSubCategory = itemSubCategoryRepository.findByItemCategoryName(itemCategoryName);
        ItemMiddleCategory itemMiddleCategory = itemMiddleCategoryRepository.findByItemCategoryName(itemCategoryMiddleName);
        if(itemMiddleCategory == null){
            throw new IllegalArgumentException("해당하는 상위 카테고리가 없습니다");
        }
        if(itemSubCategory == null){
            throw new IllegalArgumentException("해당하는 카테고리가 없습니다");
        }

        itemMiddleCategory.getItemSubCategoryList().remove(itemSubCategory);
        itemSubCategoryRepository.delete(itemSubCategory);

    }






}
