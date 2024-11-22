package com.kong.cc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kong.cc.dto.StockDto;
import com.kong.cc.entity.Stock;
import com.querydsl.core.Tuple;

public interface StockRepository extends JpaRepository<Stock, Integer> {
//	@Query(nativeQuery=true, value="select s.*, st.stock_count"
//			+ "from store s "
//			+ "left join stock st "
//			+ "on storeCode "
//			+ "where st.item_code = s.item_code "
//			+ "union all")
//	public List<Tuple> selectStockByItemCode(String itemCode);
}
