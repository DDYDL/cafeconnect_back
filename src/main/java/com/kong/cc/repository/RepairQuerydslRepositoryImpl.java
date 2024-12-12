package com.kong.cc.repository;

import static com.kong.cc.entity.QRepair.repair;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.RepairResponseDto;
import com.kong.cc.dto.RepairSearchCondition;
import com.kong.cc.entity.QItem;
import com.kong.cc.entity.QItemMajorCategory;
import com.kong.cc.entity.QRepair;
import com.kong.cc.entity.Repair;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class RepairQuerydslRepositoryImpl implements RepairQuerydslRepository{


    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RepairQuerydslRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<RepairResponseDto> findRepairResponseDtoListByKeyword(String keyword, Pageable pageable) {
        QueryResults<Repair> repairQueryResults = queryFactory.select(repair)
                .from(repair)
                .where(storeNameContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<Repair> content = repairQueryResults.getResults();
        long total = repairQueryResults.getTotal();
        PageImpl<Repair> repairPage = new PageImpl<>(content, pageable, total);

        Page<RepairResponseDto> repairResponseDtoPage = repairPage.map(r ->
                RepairResponseDto.builder()
                        .repairNum(r.getRepairNum())
                        .repairType(r.getRepairType())
                        .repairTitle(r.getRepairTitle())
                        .repairContent(r.getRepairContent())
                        .repairDate(r.getRepairDate())
                        .repairStatus(r.getRepairStatus())
                        .repairAnswer(r.getRepairAnswer())
                        .repairAnswerDate(r.getRepairAnswerDate())
                        .storeName(r.getStoreR() != null ? r.getStoreR().getStoreName() : null)
                        .itemCode(r.getItemR() != null ? r.getItemR().getItemCode() : null)
                        .itemName(r.getItemR() != null ? r.getItemR().getItemName() : null)
                        .itemCategoryMajorName(r.getItemR() != null && r.getItemR().getItemMajorCategory() != null ?
                                r.getItemR().getItemMajorCategory().getItemCategoryName() : null)
                        .itemCategoryMiddleName(r.getItemR() != null && r.getItemR().getItemMiddleCategory() != null ?
                                r.getItemR().getItemMiddleCategory().getItemCategoryName() : null)
                        .build()

                );


        return repairResponseDtoPage;
    }

    private BooleanExpression storeNameContains(String keyword) {
        return StringUtils.hasText(keyword) ? repair.storeR.storeName.contains(keyword) : null;
    }

    @Override
    public Page<RepairResponseDto> findRepairResponseDtoListByCategory(RepairSearchCondition condition,Pageable pageable) {

        QueryResults<Repair> repairQueryResults = queryFactory
                .selectFrom(repair)
                .where(majorCategoryNameEq(condition.getItemCategoryMajorName()),
                        middleCategoryNameEq(condition.getItemCategoryMiddleName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();


        List<Repair> content = repairQueryResults.getResults();
        long total = repairQueryResults.getTotal();
        PageImpl<Repair> repairPage = new PageImpl<>(content, pageable, total);


        Page<RepairResponseDto> repairResponseDtoPage = repairPage.map(r ->
                RepairResponseDto.builder()
                        .repairNum(r.getRepairNum())
                        .repairType(r.getRepairType())
                        .repairTitle(r.getRepairTitle())
                        .repairContent(r.getRepairContent())
                        .repairDate(r.getRepairDate())
                        .repairStatus(r.getRepairStatus())
                        .repairAnswer(r.getRepairAnswer())
                        .repairAnswerDate(r.getRepairAnswerDate())
                        .storeName(r.getStoreR() != null ? r.getStoreR().getStoreName() : null)
                        .itemCode(r.getItemR() != null ? r.getItemR().getItemCode() : null)
                        .itemName(r.getItemR() != null ? r.getItemR().getItemName() : null)
                        .itemCategoryMajorName(r.getItemR() != null && r.getItemR().getItemMajorCategory() != null ?
                                r.getItemR().getItemMajorCategory().getItemCategoryName() : null)
                        .itemCategoryMiddleName(r.getItemR() != null && r.getItemR().getItemMiddleCategory() != null ?
                                r.getItemR().getItemMiddleCategory().getItemCategoryName() : null )
                        .build()

        );


        return repairResponseDtoPage;
    }

    private Predicate majorCategoryNameEq(String itemCategoryMajorName) {
        return StringUtils.hasText(itemCategoryMajorName) ? repair.itemR.itemMajorCategory.itemCategoryName.eq(itemCategoryMajorName) : null;
    }

    private Predicate middleCategoryNameEq(String itemCategoryMiddleName) {
        return StringUtils.hasText(itemCategoryMiddleName) ? repair.itemR.itemMiddleCategory.itemCategoryName.eq(itemCategoryMiddleName) : null;
    }

    // 가맹점 시작
    // 가맹점 수리 요청 리스트
    public List<RepairResponseDto> selectRepairRequestOfStore(Integer storeCode,PageRequest pageRequest) {
    	QRepair repair = QRepair.repair;
    	QItem item = QItem.item;

    	return queryFactory.select(Projections.bean(RepairResponseDto.class,
    			                   repair.repairNum,
    			                   repair.repairStatus,
    			                   repair.repairDate,
    			                   //Expressions.dateTemplate(Date.class,"DATE({0})",repair.repairDate).as("repairDate"),
    			                   //Expressions.stringTemplate("DATE_FORMAT({0}, {1})",repair.repairDate,ConstantImpl.create("%Y-%m-%d")).as("repairDate").as("repairDate"),
    			                   repair.repairType,
    			                   repair.repairTitle,
    			                   repair.repairContent,
    			                   repair.repairAnswer,
    			                   repair.repairAnswerDate,
    			                   repair.itemR.itemCode.as("itemCode"),
    			                   repair.itemR.itemName,
    			                   repair.itemR.itemImageFile.fileName.as(("itemFileName")),
    			                   repair.itemR.itemMajorCategory.itemCategoryName.as("itemCategoryMajorName"),
    			                   repair.itemR.itemMiddleCategory.itemCategoryName.as("itemCategoryMiddleName"),
    			                   repair.itemR.itemSubCategory.itemCategoryName.as("itemCategorySubName")
    			))
    			.from(repair)
    			.leftJoin(item).on(item.itemCode.eq(repair.itemR.itemCode))
		        .leftJoin(item.itemMajorCategory)
		        .leftJoin(item.itemMiddleCategory)
		        .leftJoin(item.itemSubCategory) // 소분류없는 상품의 경우가 있음, 따라서 crossjoin되면 안돼서 임의적으로 추가해줌 
    			.where(repair.storeR.storeCode.eq(storeCode))
    			.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.orderBy(repair.repairDate.desc())
    			.fetch();
    }
    // 수리 요청 리스트 카운트
    public Long findRepairRequestCount(Integer storeCode) {
    	QRepair repair = QRepair.repair;
    	return queryFactory.select(repair.count())
    			.from(repair)
    			.where(repair.storeR.storeCode.eq(storeCode))
    			.fetchOne();
    }
    //검색 조건 수리 요청 리스트 
    public List<RepairResponseDto> selectSearchRepairRequestOfStore(Integer storeCode,PageRequest pageRequest,String type,String word) {
    	QRepair repair = QRepair.repair;
    	QItem item = QItem.item;
    	List<RepairResponseDto> repairRequestList = null;
    	BooleanBuilder builder = new BooleanBuilder();
    	
    	//전체
    	if(type.equals("")) {
    		builder.and(repair.storeR.storeCode.eq(storeCode));
    	}else if(type.equals("name")) {
    		builder.and(repair.storeR.storeCode.eq(storeCode).and(repair.itemR.itemName.contains(word)));
    	}else if (type.equals("kind")) {
    		builder.and(repair.storeR.storeCode.eq(storeCode).and(repair.repairType.contains(word)));
    	}else if(type.equals("status")) {
    		builder.and(repair.storeR.storeCode.eq(storeCode).and(repair.repairStatus.contains(word)));
    	}
    	
    	return queryFactory.select(Projections.bean(RepairResponseDto.class,
					    			 repair.repairNum,
					                 repair.repairStatus,
					                 repair.repairDate,
					                 repair.repairType,
					                 repair.repairTitle,
					                 repair.repairContent,
					                 repair.repairAnswer,
					                 repair.repairAnswerDate,
					                 repair.itemR.itemCode.as("itemCode"),
					                 repair.itemR.itemName,
					                 repair.itemR.itemImageFile.fileName.as(("itemFileName")),
					                 repair.itemR.itemMajorCategory.itemCategoryName.as("itemCategoryMajorName"),
					                 repair.itemR.itemMiddleCategory.itemCategoryName.as("itemCategoryMiddleName"),
					                 repair.itemR.itemSubCategory.itemCategoryName.as("itemCategorySubName")
    			     
    			))
    			.from(repair)
    			.leftJoin(item).on(item.itemCode.eq(repair.itemR.itemCode))
		        .leftJoin(item.itemMajorCategory)
		        .leftJoin(item.itemMiddleCategory)
		        .leftJoin(item.itemSubCategory) // 소분류없는 상품의 경우가 있음, 따라서 crossjoin되면 안돼서 임의적으로 추가해줌 
    			.where(builder) // 동적쿼리 활용
    			.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.orderBy(repair.repairDate.desc())
    			.fetch();
    }
    
    public Long findSearchRepairRequestCount(Integer storeCode,String type,String word) {
    	QRepair repair = QRepair.repair;
    	QItem item = QItem.item;
    	List<RepairResponseDto> repairRequestList = null;
    	BooleanBuilder builder = new BooleanBuilder();
    	
    	//전체
    	if(type.equals("")) {
    		builder.and(repair.storeR.storeCode.eq(storeCode));
    	}else if(type.equals("name")) {
    		builder.and(repair.storeR.storeCode.eq(storeCode).and(repair.itemR.itemName.contains(word)));
    	}else if (type.equals("kind")) {
    		builder.and(repair.storeR.storeCode.eq(storeCode).and(repair.repairType.contains(word)));
    	}else if(type.equals("status")) {
    		builder.and(repair.storeR.storeCode.eq(storeCode).and(repair.repairStatus.contains(word)));
    	}
    	return queryFactory.select(repair.count())
    			.from(repair)
    			.where(builder)
    			.fetchOne();
    }
    
    
    //수리 신청 기기 조회에 사용될 전체 머신 리스트 - 대분류가 머신인!
    public List<Tuple> selectAllMachineInfoList() {
    	QItem item = QItem.item;
    	QItemMajorCategory major = QItemMajorCategory.itemMajorCategory;
    	return queryFactory.select(item.itemCode,item.itemName)
    						.from(item)
    						.join(major)
    						.on(item.itemMajorCategory.itemCategoryNum.eq(major.itemCategoryNum))
    						.where(major.itemCategoryName.eq("머신")).fetch();

    }


}
