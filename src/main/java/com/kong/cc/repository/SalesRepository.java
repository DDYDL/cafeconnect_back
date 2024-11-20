package com.kong.cc.repository;

import com.kong.cc.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Sales;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {

//    List<Sales> findByMenuList(List<Menu> menuList);
}
