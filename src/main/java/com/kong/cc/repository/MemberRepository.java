package com.kong.cc.repository;

import java.util.Optional;
import com.kong.cc.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kong.cc.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	public Optional<Member> findByUsername(String username);
	public Member findByProviderAndProviderId(String provider, String providerId);

    Menu findBymenuCode(String menuCode);

    @Query("select m from Menu m where m.menuName like '%:keyword%'")
    List<Menu> findMenuListByKeyword(@Param("keyword") String keyword);
}
