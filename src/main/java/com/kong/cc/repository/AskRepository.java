package com.kong.cc.repository;

import com.kong.cc.entity.Ask;
import com.kong.cc.entity.Complain;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AskRepository extends JpaRepository<Ask, Integer> {

    Ask findByAskNum(Integer askNum);

    @Query("SELECT a FROM Ask a WHERE a.storeAs.storeCode = :storeCode")
    List<Ask> findByStoreCode(@Param("storeCode") Integer storeCode);

    @Query("SELECT a FROM Ask a WHERE a.storeAs.storeCode = :storeCode AND a.askNum = :askNum")
    Optional<Ask> findByStoreCodeAndAskNum(Integer storeCode, Integer askNum);
}
