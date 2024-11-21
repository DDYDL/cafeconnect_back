package com.kong.cc.service;

import com.kong.cc.dto.MenuResponseDto;
import com.kong.cc.dto.MenuSaveForm;
import com.kong.cc.dto.MenuUpdateForm;
import com.kong.cc.entity.ImageFile;
import com.kong.cc.entity.Menu;
import com.kong.cc.entity.MenuCategory;
import com.kong.cc.repository.ImageFileRepository;
import com.kong.cc.repository.MenuCategoryRepository;
import com.kong.cc.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final ImageFileRepository imageFileRepository;

    public void saveMenu(MenuSaveForm menuSaveForm, MultipartFile multipartFile) {
        ImageFile imageFile = null;
        imageFileRepository.save(imageFile);
        MenuCategory menuCategory = menuCategoryRepository.findByMenuCategoryName(menuSaveForm.getMenuCategoryName());
        Menu.MenuBuilder menuBuilder = Menu.builder()
                .menuName(menuSaveForm.getMenuName())
                .menuPrice(menuSaveForm.getMenuPrice())
                .menuCapacity(menuSaveForm.getMenuCapacity())
                .caffeine(menuSaveForm.getCaffeine())
                .calories(menuSaveForm.getCalories())
                .carbohydrate(menuSaveForm.getCarbohydrate())
                .sugar(menuSaveForm.getSugar())
                .natrium(menuSaveForm.getNatrium())
                .fat(menuSaveForm.getFat())
                .protein(menuSaveForm.getProtein())
                .menuStatus(menuSaveForm.getMenuStatus());

        if(menuCategory != null){
            menuBuilder.menuCategory(menuCategory);
        }

        Menu menu = menuBuilder.build();
        menuRepository.save(menu);


    }

    public void updateMenu(String menuCode, MenuUpdateForm menuUpdateForm, MultipartFile multipartFile) {
        ImageFile imageFile = null;
        imageFileRepository.save(imageFile);
        MenuCategory menuCategory = menuCategoryRepository.findByMenuCategoryName(menuUpdateForm.getMenuCategoryName());
        Menu menu = menuRepository.findByMenuCode(menuCode);
        if(menu == null){
            throw new IllegalArgumentException("해당하는 메뉴가 없습니다");

        }


        menu.setMenuName(menuUpdateForm.getMenuName());
        menu.setMenuPrice(menuUpdateForm.getMenuPrice());
        menu.setMenuCapacity(menuUpdateForm.getMenuCapacity());
        menu.setCaffeine(menuUpdateForm.getCaffeine());
        menu.setCalories(menuUpdateForm.getCalories());
        menu.setCarbohydrate(menuUpdateForm.getCarbohydrate());
        menu.setSugar(menuUpdateForm.getSugar());
        menu.setNatrium(menuUpdateForm.getNatrium());
        menu.setFat(menuUpdateForm.getFat());
        menu.setProtein(menuUpdateForm.getProtein());
        menu.setMenuStatus(menuUpdateForm.getMenuStatus());

        if(menuCategory != null){
            menu.setMenuCategory(menuCategory);
        }


    }

    public void deleteMenu(String menuCode) {
        Menu menu = menuRepository.findByMenuCode(menuCode);
        if(menu == null){
            throw new IllegalArgumentException("해당하는 메뉴가 없습니다");

        }

        int salesListSize = menu.getSalesList().size();
        if(salesListSize >0){
            throw new IllegalArgumentException("salesList 존재합니다");
        }

        menuRepository.delete(menu);


    }

    public MenuResponseDto selectMenuByMenuCode(String menuCode) {
        Menu menu = menuRepository.findByMenuCode(menuCode);
        if(menu == null){
            throw new IllegalArgumentException("해당하는 메뉴가 없습니다");
        }

        String menuCategoryName = menu.getMenuCategory().getMenuCategoryName();
        String imageUrl = null;


        return MenuResponseDto.builder()
                .menuCode(menu.getMenuCode())
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .menuCapacity(menu.getMenuCapacity())
                .caffeine(menu.getCaffeine())
                .calories(menu.getCalories())
                .carbohydrate(menu.getCarbohydrate())
                .sugar(menu.getSugar())
                .natrium(menu.getNatrium())
                .fat(menu.getFat())
                .protein(menu.getProtein())
                .menuStatus(menu.getMenuStatus())
                .menuCategoryName(menuCategoryName)
                .imageUrl(imageUrl)
                .build();



    }
}
