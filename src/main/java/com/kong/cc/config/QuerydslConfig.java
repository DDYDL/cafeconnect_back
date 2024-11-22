package com.kong.cc.config;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;


// 생성해야할 객체들을 이 클래스에 몰아놓는다.
@Configuration
public class QuerydslConfig {
	
	// 모든 jpa에서 같이 쓰는 매니저
	@Autowired
	EntityManager entityManager;
	
	@Bean // 자동 생성된 객체
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
