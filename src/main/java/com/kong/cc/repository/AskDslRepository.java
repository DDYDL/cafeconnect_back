package com.kong.cc.repository;

import com.kong.cc.entity.Ask;
import com.kong.cc.entity.QAsk;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AskDslRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	public Long findAskCount() throws Exception {
		QAsk ask = QAsk.ask;
		return jpaQueryFactory.select(ask.count())
				.from(ask).fetchOne();
	}
	
	public List<Ask> findAskListByPaging(PageRequest pageRequest) throws Exception {
		QAsk ask = QAsk.ask;
		return jpaQueryFactory.selectFrom(ask)
				.orderBy(ask.askDate.desc())
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();
	}
}
