package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
