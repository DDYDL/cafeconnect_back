package com.kong.cc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	public Optional<Member> findByUsername(String username);
	public Member findByProviderAndProviderId(String provider, String providerId);
}
