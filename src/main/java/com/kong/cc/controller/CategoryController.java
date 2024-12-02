package com.kong.cc.controller;

import com.kong.cc.dto.*;
import com.kong.cc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(value = "/addMajorCategory")  //Category.js
    public ResponseEntity<Object> addMajorCategory(@RequestPart ItemMajorCategoryForm itemMajorCategoryForm){
        try{
            Integer num = categoryService.saveMajorCategory(itemMajorCategoryForm);
            Map<String, Integer> body = Map.of("num", num);
            return new ResponseEntity<>(body,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addMiddleCategory")  //Category.js
    public ResponseEntity<Object> addMiddleCategory(@RequestPart ItemMiddleCategoryForm itemMiddleCategoryForm){
        try{
            Integer num = categoryService.saveMiddleCategory(itemMiddleCategoryForm);
            Map<String, Integer> body = Map.of("num", num);
            return new ResponseEntity<>(body,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addSubCategory")  //Category.js
    public ResponseEntity<Object> addSubCategory(@RequestPart ItemSubCategoryForm itemSubCategoryForm){
        try{
            Integer num = categoryService.saveSubCategory(itemSubCategoryForm);
            Map<String, Integer> body = Map.of("num", num);
            return new ResponseEntity<>(body,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateItemMajorCategory")  //Category.js
    public ResponseEntity<Object> updateItemMajorCategory(@RequestPart ItemMajorCategoryForm itemMajorCategoryForm){
        try{
            categoryService.updateItemMajorCategory(itemMajorCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateItemMiddleCategory")  //Category.js
    public ResponseEntity<Object> updateItemMiddleCategory(@RequestPart ItemMiddleCategoryForm itemMiddleCategoryForm){
        try{
            categoryService.updateItemMiddleCategory(itemMiddleCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateItemSubCategory")  //Category.js
    public ResponseEntity<Object> updateItemSubCategory(@RequestPart ItemSubCategoryForm itemSubCategoryForm){
        try{
            categoryService.updateItemSubCategory(itemSubCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/categoryList")  //Category.js
    public ResponseEntity<Object> categoryList(@RequestParam Integer categoryNum){


        try{
            List<String> body = categoryService.categoryList(categoryNum);
            return new ResponseEntity<>(body,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/deleteMajorCategory")  //Category.js
    public ResponseEntity<Object> deleteMajorCategory(@RequestPart ItemMajorCategoryForm itemMajorCategoryForm){
        try{
            categoryService.deleteMajorCategory(itemMajorCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/deleteMiddleCategory")  //Category.js
    public ResponseEntity<Object> deleteMiddleCategory(@RequestPart ItemMiddleCategoryForm itemMiddleCategoryForm){
        try{
            categoryService.deleteMiddleCategory(itemMiddleCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/deleteSubCategory")  //Category.js
    public ResponseEntity<Object> deleteSubCategory(@RequestPart ItemSubCategoryForm itemSubCategoryForm){
        try{
            categoryService.deleteSubCategory(itemSubCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/majorCategory")
    public ResponseEntity<Object> majorCategory(){
        try{
            List<CategoryResponse> body = categoryService.majorCategory();
            return new ResponseEntity<>(body,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/majorCategoryCopy")
    public ResponseEntity<Object> majorCategoryCopy(){
        try{
            List<CategoryResponseCopy> body = categoryService.majorCategoryCopy();
            body.add(0,new CategoryResponseCopy(0,"대분류",""));
            return new ResponseEntity<>(body,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/middleCategory")
    public ResponseEntity<Object> middleCategory(@RequestParam String categoryName){
        try{
            List<CategoryResponse> body = categoryService.middleCategory(categoryName);
            return new ResponseEntity<>(body,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/middleCategoryCopy")
    public ResponseEntity<Object> middleCategoryCopy(@RequestParam String categoryName){
        try{
            List<CategoryResponseCopy> body = categoryService.middleCategoryCopy(categoryName);
            body.add(0,new CategoryResponseCopy(0,"중분류",""));
            return new ResponseEntity<>(body,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/subCategory")
    public ResponseEntity<Object> majorCategoryCopy(@RequestParam String categoryName){
        try{
            List<CategoryResponseCopy> body = categoryService.subCategoryCopy(categoryName);
            body.add(0,new CategoryResponseCopy(0,"중분류",""));
            return new ResponseEntity<>(body,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/subCategoryCopy")
    public ResponseEntity<Object> majorCategory(@RequestParam String categoryName){
        try{
            List<CategoryResponseCopy> body = categoryService.subCategoryCopy(categoryName);
            return new ResponseEntity<>(body,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/menuCategory")
    public ResponseEntity<Object> menuCategory(){
        try{
            List<MenuCategoryResponse> body = categoryService.menuCategory();
            return new ResponseEntity<>(body,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/menuCategoryCopy")
    public ResponseEntity<Object> menuCategoryCopy(){
        try{
            List<MenuCategoryResponseCopy> body = categoryService.menuCategoryCopy();
            body.add(0, new MenuCategoryResponseCopy(0,"대분류",""));
            return new ResponseEntity<>(body,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/addMenuCategory")  //Category.js
    public ResponseEntity<Object> addMenuCategory(@RequestPart AddMenuCategoryForm addMenuCategoryForm){
        try{
            Integer num = categoryService.addMenuCategory(addMenuCategoryForm);
            Map<String, Integer> body = Map.of("num", num);
            return new ResponseEntity<>(body,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/updateMenuCategory")  //Category.js
    public ResponseEntity<Object> updateMenuCategory(@RequestPart UpdateMenuCategoryForm updateMenuCategoryForm){
        try{
            categoryService.updateMenuCategory(updateMenuCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/deleteMenuCategory/{categoryNum}")  //Category.js
    public ResponseEntity<Object> deleteMenuCategory(@PathVariable Integer categoryNum){
        try{
            categoryService.deleteMenuCategory(categoryNum);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
