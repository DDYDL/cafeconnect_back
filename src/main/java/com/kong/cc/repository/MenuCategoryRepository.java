package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.MenuCategory;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Integer> {

}
