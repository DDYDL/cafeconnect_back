package com.kong.cc.service;


import com.kong.cc.dto.StoreDto;

public interface StoreJoinService {


    void joinStore(StoreDto storeDto) throws Exception;


    Boolean checkDoubleId(String username) throws Exception;

    StoreDto checkStoreCode(Integer storeCode) throws Exception;
}
