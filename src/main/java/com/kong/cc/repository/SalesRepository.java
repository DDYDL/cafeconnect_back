package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Sales;

public interface SalesRepository extends JpaRepository<Sales, Integer> {

}
