package com.kong.cc.repository;

import com.kong.cc.dto.ItemResponseDto;
import com.kong.cc.dto.ItemSearchCondition;
import com.kong.cc.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


public interface ItemQuerydslRepository {

    Page<ItemResponseDto> findItemResponseDtoListByKeyword(String keyword, Pageable pageable);

    Page<ItemResponseDto> findItemResponseDtoListByCategory(Page<Item> page);

    Page<Item> findItemListByCategory(ItemSearchCondition condition, Pageable pageable);

}
