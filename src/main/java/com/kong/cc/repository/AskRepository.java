package com.kong.cc.repository;

import com.kong.cc.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Ask, Integer> {

    Ask findByAskNum(Integer askNum);
}
