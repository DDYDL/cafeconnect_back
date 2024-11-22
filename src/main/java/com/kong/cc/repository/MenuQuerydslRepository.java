package com.kong.cc.repository;

import com.kong.cc.dto.MenuResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuQuerydslRepository {

    Page<MenuResponseDto> findMenuResponseDtoListByKeyword(String keyword, Pageable pageable);

    Page<MenuResponseDto> findMenuResponseDtoListByCategory(String categoryName, Pageable pageable);
}
