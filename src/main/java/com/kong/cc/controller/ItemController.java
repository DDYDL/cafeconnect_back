package com.kong.cc.controller;

import com.kong.cc.dto.ItemResponseDto;
import com.kong.cc.dto.ItemSaveForm;
import com.kong.cc.dto.ItemUpdateForm;
import com.kong.cc.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @PostMapping("/addItem")  //ItemInsert.js
    public ResponseEntity<Object> addItem(ItemSaveForm itemSaveForm, @RequestParam("file") MultipartFile multipartFile){
        try{
            itemService.saveItem(itemSaveForm,multipartFile);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/updateItem/{itemCode}") //ItemUpdate.js
    public ResponseEntity<Object> updateItem(@PathVariable String itemCode,
                                             ItemUpdateForm itemUpdateForm ,
                                             @RequestParam(name = "file", required = false) MultipartFile multipartFile){
        try{
            itemService.updateItem(itemCode,itemUpdateForm,multipartFile);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/selectItemByItemCode/{itemCode}")  //ItemList.js
    public ResponseEntity<ItemResponseDto> selectItemByItemCode(@PathVariable String itemCode){
        try{
            ItemResponseDto itemResponseDto = itemService.selectItemByItemCode(itemCode);
            return new ResponseEntity<>(itemResponseDto,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deleteItem/{itemCode}") //ItemDetail.js
    public ResponseEntity<Object> deleteItem(@PathVariable String itemCode){

        try{
            itemService.deleteItem(itemCode);;
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/itemList") //ItemList.js
    public ResponseEntity<Object> itemList(){
        return null;
    }




}
