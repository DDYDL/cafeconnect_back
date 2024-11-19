package com.kong.cc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class CategoryController {


    @PostMapping("/addCategory")  //Category.js
    public ResponseEntity<Object> addCategory(){
        return null;
    }

    @GetMapping("/categoryList")  //Category.js
    public ResponseEntity<Object> categoryList(){
        return null;
    }


    @PostMapping("/deleteCategory/{categoryNum}")  //Category.js
    public ResponseEntity<Object> deleteCategory(@PathVariable Integer categoryNum){
        return null;
    }


}
