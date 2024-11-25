package com.kong.cc.controller;

import com.kong.cc.dto.ItemMajorCategoryForm;
import com.kong.cc.dto.ItemMiddleCategoryForm;
import com.kong.cc.dto.ItemSubCategoryForm;
import com.kong.cc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addMajorCategory")  //Category.js
    public ResponseEntity<Object> addMajorCategory(@RequestBody ItemMajorCategoryForm itemMajorCategoryForm){
        try{
            categoryService.saveMajorCategory(itemMajorCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addMiddleCategory")  //Category.js
    public ResponseEntity<Object> addMiddleCategory(@RequestBody ItemMiddleCategoryForm itemMiddleCategoryForm){
        try{
            categoryService.saveMiddleCategory(itemMiddleCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addSubCategory")  //Category.js
    public ResponseEntity<Object> addSubCategory(@RequestBody ItemSubCategoryForm itemSubCategoryForm){
        try{
            categoryService.saveSubCategory(itemSubCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/categoryList")  //Category.js
    public ResponseEntity<Object> categoryList(){
        return null;
    }


    @PostMapping("/deleteMajorCategory")  //Category.js
    public ResponseEntity<Object> deleteMajorCategory(@RequestBody ItemMajorCategoryForm itemMajorCategoryForm){
        try{
            categoryService.deleteMajorCategory(itemMajorCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/deleteMiddleCategory")  //Category.js
    public ResponseEntity<Object> deleteMiddleCategory(@RequestBody ItemMiddleCategoryForm itemMiddleCategoryForm){
        try{
            categoryService.deleteMiddleCategory(itemMiddleCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/deleteSubCategory")  //Category.js
    public ResponseEntity<Object> deleteSubCategory(@RequestBody ItemSubCategoryForm itemSubCategoryForm){
        try{
            categoryService.deleteSubCategory(itemSubCategoryForm);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
