package com.kong.cc.repository;

import com.kong.cc.entity.Repair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairRepository extends JpaRepository<Repair, Integer> {

    Repair findByRepairNum(Integer repairNum);
    

}
