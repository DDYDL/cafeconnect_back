package com.kong.cc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

}
