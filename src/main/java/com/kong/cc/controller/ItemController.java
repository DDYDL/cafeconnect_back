package com.kong.cc.controller;

import com.kong.cc.dto.*;
import com.kong.cc.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Object> addItem(@RequestBody ItemSaveForm itemSaveForm, @RequestParam("file") MultipartFile file){
        try{
            itemService.saveItem(itemSaveForm,file);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/updateItem/{itemCode}") //ItemUpdate.js
    public ResponseEntity<Object> updateItem(@PathVariable String itemCode,
                                             @RequestBody ItemUpdateForm itemUpdateForm ,
                                             @RequestParam(name = "file", required = false) MultipartFile file){
        try{
            itemService.updateItem(itemCode,itemUpdateForm,file);
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

    @GetMapping("/itemListByKeyword") //ItemList.js
    public ResponseEntity<Object> itemListByKeyword(Integer pageNum, Integer pageSize, String keyword){
        try{
            Page<ItemResponseDto> page = itemService.itemResponseDtoListByKeyword(pageNum,pageSize,keyword);
            return new ResponseEntity<>(page,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/itemListByCategory") //ItemList.js
    public ResponseEntity<Object> itemListByCategory(Integer pageNum, Integer pageSize, @ModelAttribute ItemSearchCondition condition){
        try{
            Page<ItemResponseDto> page = itemService.itemResponseDtoListByCategory(pageNum,pageSize,condition);
            return new ResponseEntity<>(page,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




}
