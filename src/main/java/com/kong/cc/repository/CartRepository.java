package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
