package com.kong.cc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kong.cc.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Menu findByMenuCode(String menuCode);
    
    @Query("select m from Menu m where m.menuName like '%:keyword%'")
    List<Menu> findMenuListByKeyword(@Param("keyword") String keyword);
}
