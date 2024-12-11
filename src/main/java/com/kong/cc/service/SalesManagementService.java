package com.kong.cc.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.kong.cc.dto.MenuDto;
import com.kong.cc.dto.MenuSalesDto;
import com.kong.cc.dto.SalesDto;

public interface SalesManagementService {

    List<MenuDto> menuList() throws Exception;
    void salesWrite(Date salesDate, Integer storeCode, List<SalesDto> salsList) throws Exception;

    List<SalesDto> salesTemp(Date salesDate, Integer storeCode) throws Exception;
    List<MenuSalesDto> salesAnalisisByBetween(Integer storeCode, Date start, Date end) throws Exception;
    Map<String,List<MenuSalesDto>> salesAnalysis(Integer storeCode, String period)throws Exception;
}
