package com.kong.cc.repository;

import com.kong.cc.entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainRepository extends JpaRepository<Complain, Integer> {
	
}
