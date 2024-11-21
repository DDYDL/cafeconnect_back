package com.kong.cc.service;

import com.kong.cc.dto.SalesDto;

import java.util.List;

public interface SalesManagementService {
    void salesWrite(SalesDto body) throws Exception;

//    SalesDto anualSales(Integer itemCategoryNum) throws Exception;
//    SalesDto quarterlySales(Integer itemCategoryNum) throws Exception;
//    SalesDto monthlySales(Integer itemCategoryNum) throws Exception;
//    SalesDto customSales(Integer itemCategoryNum) throws Exception;

}
