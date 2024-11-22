package com.kong.cc.repository;

import com.kong.cc.dto.ItemSearchCondition;
import com.kong.cc.dto.RepairResponseDto;
import com.kong.cc.dto.RepairSearchCondition;
import com.kong.cc.entity.Item;
import com.kong.cc.entity.Repair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RepairQuerydslRepository {

    Page<RepairResponseDto> findRepairResponseDtoListByKeyword(String keyword, Pageable pageable);

    Page<RepairResponseDto> findRepairResponseDtoListByCategory(RepairSearchCondition condition,Pageable pageable);


}
