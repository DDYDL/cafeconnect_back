package com.kong.cc.service;

import com.kong.cc.dto.MenuResponseDto;
import com.kong.cc.dto.MenuSaveForm;
import com.kong.cc.dto.MenuUpdateForm;
import com.kong.cc.entity.ImageFile;
import com.kong.cc.entity.Menu;
import com.kong.cc.entity.MenuCategory;
import com.kong.cc.repository.ImageFileRepository;
import com.kong.cc.repository.MenuCategoryRepository;
import com.kong.cc.repository.MenuQuerydslRepositoryImpl;
import com.kong.cc.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.sql.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuQuerydslRepositoryImpl menuQuerydslRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final ImageFileRepository imageFileRepository;
    private static AtomicLong sequence = new AtomicLong(1L);


    @Value("${upload.path}")
    private String uploadDir;



    public void saveMenu(MenuSaveForm menuSaveForm, MultipartFile file) {
        ImageFile imageFile = null;
        if (file.isEmpty()){
            throw new RuntimeException("해당하는 파일이 없습니다");
        }

        try{
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            int index = originalFilename.indexOf(".");
            String originalName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(index);
            File saveFile = new File(directory, originalName);
            file.transferTo(saveFile);

            imageFile = ImageFile.builder()
                    .fileContentType(file.getContentType())
                    .fileUploadDate(new Date(System.currentTimeMillis()))
                    .fileName(originalName)
                    .fileSize(file.getSize())
                    .fileDirectory(uploadDir)
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e);
        }


        imageFileRepository.save(imageFile);

        MenuCategory menuCategory = menuCategoryRepository.findByMenuCategoryName(menuSaveForm.getMenuCategoryName());
        Menu.MenuBuilder menuBuilder = Menu.builder()
                .menuCode("B"+sequence.incrementAndGet())
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
                .menuStatus(menuSaveForm.getMenuStatus())
                .menuImageFile(imageFile);

        if(menuCategory != null){
            menuBuilder.menuCategory(menuCategory);
        }

        Menu menu = menuBuilder.build();
        menuRepository.save(menu);


    }

    public void updateMenu(String menuCode, MenuUpdateForm menuUpdateForm, MultipartFile file) {
        ImageFile imageFile = null;
        try{
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            int index = originalFilename.indexOf(".");
            String originalName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(index);
            File saveFile = new File(directory, originalName);
            file.transferTo(saveFile);

            imageFile = ImageFile.builder()
                    .fileContentType(file.getContentType())
                    .fileUploadDate(new Date(System.currentTimeMillis()))
                    .fileName(originalName)
                    .fileSize(file.getSize())
                    .fileDirectory(uploadDir)
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e);
        }



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
        menu.setMenuImageFile(imageFile);

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

    public MenuResponseDto selectMenuByMenuCode(String menuCode) throws IOException {
        Menu menu = menuRepository.findByMenuCode(menuCode);
        if(menu == null){
            throw new IllegalArgumentException("해당하는 메뉴가 없습니다");
        }
        ImageFile imageFile = menu.getMenuImageFile();
        String fileDirectory = imageFile.getFileDirectory();
        String fileName = imageFile.getFileName();
        String fileContentType = imageFile.getFileContentType();
        Path imagePath = Paths.get(fileDirectory+fileName);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        String menuCategoryName = menu.getMenuCategory().getMenuCategoryName();
        String imageUrl = "data:"+fileContentType+";base64,"+base64Image;



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

    public Page<MenuResponseDto> menuResponseDtoListByKeyword(int pageNum, int pageSize, String keyword) {
        PageRequest pageRequest = PageRequest.of(pageNum , pageSize, Sort.by(Sort.Direction.ASC, "menuCode"));
        return menuQuerydslRepository.findMenuResponseDtoListByKeyword(keyword,pageRequest);
    }

    public Page<MenuResponseDto> menuResponseDtoListByCategory(int pageNum, int pageSize, String categoryName) {
        PageRequest pageRequest = PageRequest.of(pageNum , pageSize, Sort.by(Sort.Direction.ASC, "menuCode"));
        return menuQuerydslRepository.findMenuResponseDtoListByCategory(categoryName,pageRequest);
    }
}
