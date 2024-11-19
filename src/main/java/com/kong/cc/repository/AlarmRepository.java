package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

}
