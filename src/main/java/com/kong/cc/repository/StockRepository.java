package com.kong.cc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.dto.StockDto;
import com.kong.cc.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
	
}
