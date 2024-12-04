package com.kong.cc.repository;

import com.kong.cc.entity.Sales;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {

    @Query("SELECT s FROM Sales s WHERE s.salesDate BETWEEN :firstDate AND :secondDate")
    List<Sales> findByDates(@Param("firstDate") Date firstDate, @Param("secondDate")Date secondDate);
    @Query("SELECT s FROM Sales s WHERE s.salesDate = :salesDate AND s.storeSa.storeCode = :storeCode")
    List<Sales> findListBySalesDateAndStoreCode(@Param("salesDate") Date salesDate, @Param("storeCode") Integer storeCode);

    Sales findBySalesDateAndStoreSa_StoreCode(Date salesDate, Integer storeCode);


}
