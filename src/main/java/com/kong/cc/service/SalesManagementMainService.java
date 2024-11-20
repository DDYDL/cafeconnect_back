package com.kong.cc.service;

import com.kong.cc.dto.ShopOrderDto;

import java.time.LocalDate;
import java.util.List;

public interface SalesManagementMainService {

    List<ShopOrderDto> itemRevenue(LocalDate orderDate, Integer orderNum);



}
