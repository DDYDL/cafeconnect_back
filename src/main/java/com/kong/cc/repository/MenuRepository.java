package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Optional<Menu> findByMenuName(String menuName);
}
