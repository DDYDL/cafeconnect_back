package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Menu findByMenuCode(String menuCode);
}
