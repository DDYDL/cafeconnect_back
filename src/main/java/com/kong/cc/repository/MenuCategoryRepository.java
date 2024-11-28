package com.kong.cc.repository;

import com.kong.cc.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Integer> {

    MenuCategory findByMenuCategoryName(String menuCategoryName);

}
