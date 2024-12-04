package com.kong.cc.repository;

import com.kong.cc.entity.Complain;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ComplainRepository extends JpaRepository<Complain, Integer> {

    Optional<Complain> findByComplainNum(Integer complainNum);

    @Query("SELECT c FROM Complain c WHERE c.storeCo.storeCode = :storeCode")
    List<Complain> findByStoreCode(@Param("storeCode") Integer storeCode);
}
