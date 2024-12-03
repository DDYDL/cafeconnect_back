package com.kong.cc.repository;

import com.kong.cc.entity.Complain;
import com.kong.cc.entity.QComplain;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComplainDslRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	public Long findComplainCount() throws Exception {
		QComplain complain = QComplain.complain;
		return jpaQueryFactory.select(complain.count())
				.from(complain).fetchOne();
	}
	
	public List<Complain> findComplainListByPaging(PageRequest pageRequest) throws Exception {
		QComplain complain = QComplain.complain;
		return jpaQueryFactory.selectFrom(complain)
				.orderBy(complain.complainStatus.asc(), complain.complainDate.desc())
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();
	}
}
