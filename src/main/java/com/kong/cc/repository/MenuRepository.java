package com.kong.cc.repository;

import com.kong.cc.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    
    @Query("select m from Menu m where m.menuName like '%:keyword%'")
    List<Menu> findMenuListByKeyword(@Param("keyword") String keyword);

    Menu findByMenuCode(String menuCode);
    List<Menu> findByMenuStatusIsNotNull();

    Optional<Menu> findByMenuName(String menuName); // 상민 (salesWrite에서 사용 중)

    //상민 - 메뉴 리스트 가져오기(salesWrite에서 사용 중)
    @Query("SELECT m.menuName FROM Menu m")
    List<String> findMenuNameList();
}
