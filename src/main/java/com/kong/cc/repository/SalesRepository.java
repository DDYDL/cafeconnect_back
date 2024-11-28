package com.kong.cc.repository;

import com.kong.cc.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {

    @Query("SELECT s FROM Sales s WHERE s.salesDate BETWEEN :firstDate AND :secondDate")
    List<Sales> findByDates(@Param("firstDate") Date firstDate, @Param("secondDate")Date secondDate);
    List<Sales> findBySalesDateAndStoreSa_StoreCode(Date salesDate, Integer storeCode);
//    List<Sales> findByMenuList(List<Menu> menuList);


}
