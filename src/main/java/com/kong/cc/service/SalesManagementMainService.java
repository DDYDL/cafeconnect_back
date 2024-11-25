package com.kong.cc.service;


import com.kong.cc.dto.ItemMajorCategoryForm;

import java.util.Date;
import java.util.List;

import com.kong.cc.dto.ItemDto;

public interface SalesManagementMainService {


    List<ItemMajorCategoryForm> itemRevenue(Integer storeCode, Date startDate, Date endDate) throws Exception;




}
