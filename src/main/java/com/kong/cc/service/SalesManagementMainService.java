package com.kong.cc.service;


import com.kong.cc.dto.ItemMajorCategoryForm;

import com.kong.cc.dto.ItemRevenueDto;
import com.kong.cc.dto.SalesDetailDto;
import java.sql.Date;
import java.util.List;

import com.kong.cc.dto.ItemDto;

public interface SalesManagementMainService {

    List<SalesDetailDto> itemRevenue(Integer storeCode, Date startDate, Date endDate) throws Exception;



}
