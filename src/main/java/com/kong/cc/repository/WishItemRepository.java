package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.WishItem;

public interface WishItemRepository extends JpaRepository<WishItem, Integer> {

}
