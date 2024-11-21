package com.kong.cc.controller;

import com.kong.cc.dto.MenuResponseDto;
import com.kong.cc.dto.MenuSaveForm;
import com.kong.cc.dto.MenuUpdateForm;
import com.kong.cc.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/addMenu")  //MenuInsert.js
    public ResponseEntity<Object> addMenu(MenuSaveForm menuSaveForm, @RequestParam("file") MultipartFile multipartFile ){
        try{
            menuService.saveMenu(menuSaveForm,multipartFile);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/updateMenu/{menuCode}")  //MenuUpdate.js
    public ResponseEntity<Object> updateMenu(@PathVariable String menuCode,
                                             MenuUpdateForm menuUpdateForm,
                                             @RequestParam(name = "file",required = false) MultipartFile multipartFile){

        try{
            menuService.updateMenu(menuCode,menuUpdateForm,multipartFile);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/selectMenuByItemCode/{menuCode}")  //MenuDetail.js
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
    public ResponseEntity<Object> menuListByCategory(Integer categoryNum){
        return null;
    }


    @GetMapping("/menuListByKeyword")  //MenuList.js
    public ResponseEntity<Object> menuListByKeyword( String keyword){
        return null;
    }

    @GetMapping("/deleteItem/{menuCode}") //ItemDetail.js
    public ResponseEntity<Object> deleteMenu(@PathVariable String menuCode){

        try{
            menuService.deleteMenu(menuCode);;
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
