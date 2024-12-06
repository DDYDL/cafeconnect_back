package com.kong.cc.service;

import com.kong.cc.dto.*;

import java.sql.Date;
import java.util.List;

public interface SalesManagementService {

    List<MenuDto> menuList() throws Exception;
    void salesWrite(Date salesDate, Integer storeCode, List<SalesDto> salsList) throws Exception;

    List<SalesDto> salesTemp(Date salesDate, Integer storeCode) throws Exception;

    List<SalesAnnualDto> annualSAnalysis(Integer storeCode) throws Exception;

    List<SalesQuarterlyDto> quarterlyAnalysis(Integer storeCode) throws Exception;

    List<SalesMonthlyDto> monthlyAnalysis(Integer storeCode) throws Exception;

    List<SalesCustomDto> customAnalysis(Integer storeCode, Date startDate, Date endDate) throws Exception;
}
