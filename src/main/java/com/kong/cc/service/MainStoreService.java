package com.kong.cc.service;

import com.kong.cc.dto.MainStoreDto;
import com.kong.cc.entity.MainStore;
import com.kong.cc.repository.MainStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MainStoreService {

    private final MainStoreRepository mainStoreRepository;

    public MainStore join(MainStoreDto mainStoreDto){

        MainStore mainStore = MainStore.builder()
                .username(mainStoreDto.getUsername())
                .password(mainStoreDto.getPassword())
                .deptName(mainStoreDto.getDeptName())
                .build();

        return mainStoreRepository.save(mainStore);

    }

}
