package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

}
