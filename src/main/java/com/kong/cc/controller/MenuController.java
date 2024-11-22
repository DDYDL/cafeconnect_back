package com.kong.cc.controller;

import com.kong.cc.dto.MenuResponseDto;
import com.kong.cc.dto.MenuSaveForm;
import com.kong.cc.dto.MenuUpdateForm;
import com.kong.cc.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping(value = "/addMenu" ,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})  //MenuInsert.js
    public ResponseEntity<Object> addMenu(@RequestPart MenuSaveForm menuSaveForm, @RequestPart("file") MultipartFile file ){
        try{
            menuService.saveMenu(menuSaveForm,file);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/updateMenu/{menuCode}",consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})  //MenuUpdate.js
    public ResponseEntity<Object> updateMenu(@PathVariable String menuCode,
                                             @RequestPart MenuUpdateForm menuUpdateForm,
                                             @RequestPart(name = "file") MultipartFile file){

        try{
            menuService.updateMenu(menuCode,menuUpdateForm,file);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/selectMenuByMenuCode/{menuCode}")  //MenuDetail.js
    public ResponseEntity<MenuResponseDto> selectMenuByMenuCode(@PathVariable String menuCode){

        try{
            MenuResponseDto menuResponseDto = menuService.selectMenuByMenuCode(menuCode);
            return new ResponseEntity<>(menuResponseDto,HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/menuListByCategory")  //MenuList.js
    public ResponseEntity<Object> menuListByCategory(Integer pageNum, Integer pageSize, String categoryName){
        try{
            Page<MenuResponseDto> page = menuService.menuResponseDtoListByCategory(pageNum,pageSize,categoryName);
            return new ResponseEntity<>(page,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/menuListByKeyword")  //MenuList.js
    public ResponseEntity<Object> menuListByKeyword(Integer pageNum, Integer pageSize, String keyword){

        try{
            Page<MenuResponseDto> page = menuService.menuResponseDtoListByKeyword(pageNum,pageSize,keyword);
            return new ResponseEntity<>(page,HttpStatus.OK);

        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/deleteMenu/{menuCode}") //ItemDetail.js
    public ResponseEntity<Object> deleteMenu(@PathVariable String menuCode){

        try{
            menuService.deleteMenu(menuCode);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
