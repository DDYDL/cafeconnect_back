package com.kong.cc.repository;

import com.kong.cc.dto.MenuCategoryResponseCopy;
import com.kong.cc.dto.MenuCategoryResponse;
import com.kong.cc.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Integer> {

    MenuCategory findByMenuCategoryName(String menuCategoryName);

    @Query("select new com.kong.cc.dto.MenuCategoryResponse(m.menuCategoryNum,m.menuCategoryName) from MenuCategory m")
    List<MenuCategoryResponse> findMenuCategoryResponse();
    @Query("select new com.kong.cc.dto.MenuCategoryResponseCopy(m.menuCategoryNum,m.menuCategoryName,m.menuCategoryName) from MenuCategory m")
    List<MenuCategoryResponseCopy> findMenuCategoryResponseCopy();
}
