package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByItemCode(String itemCode);
}
