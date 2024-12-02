package com.kong.cc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kong.cc.dto.*;
import com.kong.cc.entity.MenuCategory;
import com.kong.cc.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kong.cc.entity.ItemMajorCategory;
import com.kong.cc.entity.ItemMiddleCategory;
import com.kong.cc.entity.ItemSubCategory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {

    private final ItemMajorCategoryRepository itemMajorCategoryRepository;
    private final ItemMiddleCategoryRepository itemMiddleCategoryRepository;
    private final ItemSubCategoryRepository itemSubCategoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    //소연
    private final ShopDSLRepository shopDslRepo;

    public Integer saveMajorCategory(ItemMajorCategoryForm itemMajorCategoryForm){

        ItemMajorCategory itemMajorCategory = ItemMajorCategory.builder()
                .itemCategoryName(itemMajorCategoryForm.getItemCategoryName())
                .build();

        ItemMajorCategory savedItemMajorCategory = itemMajorCategoryRepository.save(itemMajorCategory);
        return savedItemMajorCategory.getItemCategoryNum();
    }

    public Integer saveMiddleCategory(ItemMiddleCategoryForm itemMiddleCategoryForm){
        String itemCategoryMajorName = itemMiddleCategoryForm.getItemCategoryMajorName();
        String itemCategoryName = itemMiddleCategoryForm.getItemCategoryName();

        ItemMajorCategory itemMajorCategory = itemMajorCategoryRepository.findByItemCategoryName(itemCategoryMajorName);
        if(itemMajorCategory == null){
            throw new IllegalArgumentException("해당하는 상위 카테고리가 없습니다");

        }
        ItemMiddleCategory itemMiddleCategory = ItemMiddleCategory.builder()
                .itemMajorCategoryMd(itemMajorCategory)
                .itemCategoryName(itemCategoryName)
                .build();
        itemMajorCategory.getItemMiddelCategoryList().add(itemMiddleCategory);

        ItemMiddleCategory savedItemMiddleCategory = itemMiddleCategoryRepository.save(itemMiddleCategory);
        return savedItemMiddleCategory.getItemCategoryNum();


    }

    public Integer saveSubCategory(ItemSubCategoryForm itemSubCategoryForm){
        String itemCategoryMiddleName = itemSubCategoryForm.getItemCategoryMiddleName();
        String itemCategoryName = itemSubCategoryForm.getItemCategoryName();
        ItemMiddleCategory itemMiddleCategory = itemMiddleCategoryRepository.findByItemCategoryName(itemCategoryMiddleName);
        if(itemMiddleCategory == null){
            throw new IllegalArgumentException("해당하는 상위 카테고리가 없습니다");
        }
        ItemSubCategory itemSubCategory = ItemSubCategory.builder()
                .itemMiddleCategorySb(itemMiddleCategory)
                .itemCategoryName(itemCategoryName)
                .build();
        itemMiddleCategory.getItemSubCategoryList().add(itemSubCategory);
        ItemSubCategory savedItemSubCategory = itemSubCategoryRepository.save(itemSubCategory);
        return savedItemSubCategory.getItemCategoryNum();

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

        itemMajorCategory.getItemMiddelCategoryList().remove(itemMiddleCategory);
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

    
    //전체 카테고리 리스트가져오기  (대,중,소분류 카테고리 사이드바 출력용 )
    public List<ItemMajorCategoryForm> getAllCategoriesForItem () {

    	//쿼리 리팩토링N+1 문제 해결
    	//entity안에 만들어둔 toDto 활용해서 major로 한번에 처리
    	return shopDslRepo.selectAllCategoriesList()
                .stream()
                .map(ItemMajorCategory::toDto)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse> majorCategory() {

        return itemMajorCategoryRepository.findAllCategory();
    }

    public List<CategoryResponse> middleCategory(String categoryName) {
        return itemMiddleCategoryRepository.findAllCategory(categoryName);
    }

    public List<CategoryResponse> subCategory(String categoryName) {
        return itemSubCategoryRepository.findAllCategory(categoryName);
    }

    public List<MenuCategoryResponse> menuCategory() {
        return menuCategoryRepository.findMenuCategoryResponse();
    }

    public Integer addMenuCategory(AddMenuCategoryForm addMenuCategoryForm) {
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setMenuCategoryName(addMenuCategoryForm.getCategoryName());
        MenuCategory savedMenuCategory = menuCategoryRepository.save(menuCategory);
        return savedMenuCategory.getMenuCategoryNum();
    }

    public void updateMenuCategory(UpdateMenuCategoryForm updateMenuCategoryForm) {
        MenuCategory menuCategory = menuCategoryRepository.findById(updateMenuCategoryForm.getCategoryNum()).orElseThrow();
        menuCategory.setMenuCategoryName(updateMenuCategoryForm.getCategoryName());
    }

    public void deleteMenuCategory(Integer categoryNum) {
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryNum).orElseThrow();
        menuCategoryRepository.delete(menuCategory);
    }


    public List<MenuCategoryResponseCopy> menuCategoryCopy() {
        return menuCategoryRepository.findMenuCategoryResponseCopy();
    }

    public void updateItemMajorCategory(ItemMajorCategoryForm itemMajorCategoryForm) {
        ItemMajorCategory itemMajorCategory = itemMajorCategoryRepository.findById(itemMajorCategoryForm.getItemCategoryNum()).orElseThrow();
        itemMajorCategory.setItemCategoryName(itemMajorCategoryForm.getItemCategoryName());
    }

    public void updateItemMiddleCategory(ItemMiddleCategoryForm itemMiddleCategoryForm) {
        ItemMiddleCategory itemMiddleCategory = itemMiddleCategoryRepository.findById(itemMiddleCategoryForm.getItemCategoryNum()).orElseThrow();
        itemMiddleCategory.setItemCategoryName(itemMiddleCategoryForm.getItemCategoryName());
    }

    public void updateItemSubCategory(ItemSubCategoryForm itemSubCategoryForm) {
        ItemSubCategory itemSubCategory = itemSubCategoryRepository.findById(itemSubCategoryForm.getItemCategoryNum()).orElseThrow();
        itemSubCategory.setItemCategoryName(itemSubCategoryForm.getItemCategoryName());
    }

    public List<String> categoryList(Integer categoryNum) {
        List<String> body = new ArrayList<>();
        ItemMajorCategory itemMajorCategory = itemMajorCategoryRepository.findByItemCategoryNum(categoryNum).orElseThrow();
        List<ItemMiddleCategory> itemMiddelCategoryList = itemMajorCategory.getItemMiddelCategoryList();
        if(itemMiddelCategoryList.size() == 0 || itemMiddelCategoryList == null){
            String s = new String(itemMajorCategory.getItemCategoryName());
            body.add(s);
            return body;
        }else {
            for (ItemMiddleCategory itemMiddleCategory : itemMiddelCategoryList) {
                List<ItemSubCategory> itemSubCategoryList = itemMiddleCategory.getItemSubCategoryList();
                if(itemSubCategoryList.size() == 0 || itemSubCategoryList == null){
                    String s = new String(itemMajorCategory.getItemCategoryName() + " > " + itemMiddleCategory.getItemCategoryName());
                    body.add(s);
                }else {
                    for (ItemSubCategory itemSubCategory : itemSubCategoryList) {
                        String s = new String(itemMajorCategory.getItemCategoryName() + " > "
                                + itemMiddleCategory.getItemCategoryName() + " > "
                                + itemSubCategory.getItemCategoryName());
                        body.add(s);
                    }
                }
            }
            return body;
        }

    }
//
//    	List<ItemMajorCategoryForm> result = new ArrayList<>();
//
//    	List<ItemMajorCategory> majors = itemMajorCategoryRepository.findAll();
//
//
//    	for(ItemMajorCategory major : majors) {
//    		ItemMajorCategoryForm majorDto =  new ItemMajorCategoryForm();
//    		majorDto.setItemCategoryNum(major.getItemCategoryNum());
//    		majorDto.setItemCategoryName(major.getItemCategoryName());
//
//
//    		List<ItemMiddleCategory> middles = itemMiddleCategoryRepository.findByItemMajorCategoryMd_ItemCategoryNum(major.getItemCategoryNum());
//    		majorDto.setMidCategories(middles.stream().map(ItemMiddleCategory::toDto).collect(Collectors.toList()));
//
//    		for(ItemMiddleCategory middle : middles) {
//    			ItemMiddleCategoryForm middleDto =  new ItemMiddleCategoryForm();
//    			middleDto.setItemCategoryNum(middle.getItemCategoryNum());
//    			middleDto.setItemCategoryName(middle.getItemCategoryName());
//    			middleDto.setItemCategoryMajorNum(middle.getItemMajorCategoryMd().getItemCategoryNum());
//    			middleDto.setSubCategories(itemSubCategoryRepository.findByItemMiddleCategorySb_ItemCategoryNum(middle.getItemCategoryNum())
//    					.stream().map(ItemSubCategory::toDto).collect(Collectors.toList()));
//    	}
//    	result.add(majorDto);
//
//    	}
//		return result;
//
//    }

}
