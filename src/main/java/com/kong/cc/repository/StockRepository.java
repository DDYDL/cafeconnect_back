package com.kong.cc.repository;

import com.kong.cc.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {
//	@Query(nativeQuery=true, value="select s.*, st.stock_count"
//			+ "from store s "
//			+ "left join stock st "
//			+ "on storeCode "
//			+ "where st.item_code = s.item_code "
//			+ "union all")
//	public List<Tuple> selectStockByItemCode(String itemCode);
}
