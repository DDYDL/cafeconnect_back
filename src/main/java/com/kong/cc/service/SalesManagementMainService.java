package com.kong.cc.service;

import java.util.Date;
import java.util.List;

import com.kong.cc.dto.ItemDto;

public interface SalesManagementMainService {

    List<ItemDto> itemRevenue(Date startDate, Date endDate, Integer storeCode);



}
