package com.kong.cc.service;


import com.kong.cc.dto.StoreDto;
import com.kong.cc.dto.StoreJoinDto;

public interface StoreJoinService {


    void joinStore(StoreJoinDto storeJoinDto) throws Exception;


    Boolean checkDoubleId(String username) throws Exception;

    StoreDto checkStoreCode(Integer storeCode) throws Exception;
}
