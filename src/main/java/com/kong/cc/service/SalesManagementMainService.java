package com.kong.cc.service;

import com.kong.cc.dto.ShopOrderDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SalesManagementMainService {

    List<ShopOrderDto> itemRevenue(Date startDate, Date endDate, Integer storeCode);



}
