package com.kong.cc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class MenuController {

    @PostMapping("/addMenu")  //MenuInsert.js
    public ResponseEntity<Object> addMenu(){
        return null;
    }


    @PostMapping("/updateMenu/{menuCode}")  //MenuUpdate.js
    public ResponseEntity<Object> updateMenu(@PathVariable String menuCode){
        return null;
    }

    @GetMapping("/selectMenuByItemCode/{menuCode}")  //MenuDetail.js
    public ResponseEntity<Object> selectMenuByMenuCode(@PathVariable String menuCode){
        return null;
    }

    @GetMapping("/menuListByCategory")  //MenuList.js
    public ResponseEntity<Object> menuListByCategory(Integer categoryNum){
        return null;
    }


    @GetMapping("/menuListByKeyword")  //MenuList.js
    public ResponseEntity<Object> menuListByKeyword( String keyword){
        return null;
    }


}
