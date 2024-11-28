package com.kong.cc.repository;

import com.kong.cc.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    Optional<Notice> findByNoticeNum(Integer noticeNum);
}
