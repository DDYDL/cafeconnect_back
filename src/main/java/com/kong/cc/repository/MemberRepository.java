package com.kong.cc.repository;

import com.kong.cc.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	public Optional<Member> findByUsername(String username);
	public Member findByProviderAndProviderId(String provider, String providerId);
	public Boolean existsByUsername(String username);

}
