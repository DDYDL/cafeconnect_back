package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Repair;

public interface RepairRepository extends JpaRepository<Repair, Integer> {

    Repair findByrepairNum(Integer repairNum);

}
