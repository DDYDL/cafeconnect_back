package com.kong.cc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class ItemController {

    @PostMapping("/addItem")  //ItemInsert.js
    public ResponseEntity<Object> addItem(){
        return null;
    }


    @PostMapping("/updateItem/{itemCode}") //ItemUpdate.js
    public ResponseEntity<Object> updateItem(@PathVariable String itemCode){
        return null;
    }

    @GetMapping("/selectItemByItemCode/{itemCode}")  //ItemList.js
    public ResponseEntity<Object> selectItemByItemCode(@PathVariable String itemCode){
        return null;
    }

    @PostMapping("/deleteItem/{itemCode}") //ItemDetail.js
    public ResponseEntity<Object> deleteItem(@PathVariable String itemCode){
        return null;
    }

    @GetMapping("/itemList") //ItemList.js
    public ResponseEntity<Object> itemList(){
        return null;
    }




}
