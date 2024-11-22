package com.kong.cc.repository;

import java.util.List;

import com.kong.cc.dto.MenuDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kong.cc.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{

    Menu findByMenuCode(String menuCode);


}