package com.kong.cc.repository;

import static com.kong.cc.entity.QRepair.repair;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.kong.cc.dto.ItemDto;
import com.kong.cc.dto.RepairResponseDto;
import com.kong.cc.dto.RepairSearchCondition;
import com.kong.cc.entity.QItem;
import com.kong.cc.entity.QItemMajorCategory;
import com.kong.cc.entity.QRepair;
import com.kong.cc.entity.Repair;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
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
                .where(repair.storeR.storeName.like("%" + keyword + "%"))
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
                        .storeName(r.getStoreR().getStoreName())
                        .itemCode(r.getItemR().getItemCode())
                        .itemName(r.getItemR().getItemName())
                        .itemCategoryMajorName(r.getItemR().getItemMajorCategory().getItemCategoryName())
                        .itemCategoryMiddleName(r.getItemR().getItemMiddleCategory().getItemCategoryName())
                        .build()

                );


        return repairResponseDtoPage;
    }

    @Override
    public Page<RepairResponseDto> findRepairResponseDtoListByCategory(RepairSearchCondition condition,Pageable pageable) {

        QueryResults<Repair> repairQueryResults = queryFactory
                .selectFrom(repair)
                .where(majorCategoryNameEq(condition.getItemCategoryMajorName()),
                        middleCategoryNameEq(condition.getItemCategoryMiddleName()))
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
                        .storeName(r.getStoreR().getStoreName())
                        .itemCode(r.getItemR().getItemCode())
                        .itemName(r.getItemR().getItemName())
                        .itemCategoryMajorName(r.getItemR().getItemMajorCategory().getItemCategoryName())
                        .itemCategoryMiddleName(r.getItemR().getItemMiddleCategory().getItemCategoryName())
                        .build()

        );


        return repairResponseDtoPage;
    }

    private Predicate majorCategoryNameEq(String itemCategoryMajorName) {
        return itemCategoryMajorName != null ? repair.itemR.itemMajorCategory.itemCategoryName.eq(itemCategoryMajorName) : null;
    }

    private Predicate middleCategoryNameEq(String itemCategoryMiddleName) {
        return itemCategoryMiddleName != null ? repair.itemR.itemMiddleCategory.itemCategoryName.eq(itemCategoryMiddleName) : null;
    }

    // 가맹점 시작
    // 가맹점 수리 요청 리스트
    public List<RepairResponseDto> selectRepairRequestOfStore(Integer storeCode) {
    	QRepair repair = QRepair.repair;
    	QItem item = QItem.item;

    	return queryFactory.select(Projections.bean(RepairResponseDto.class,
    			                   repair.repairNum,
    			                   repair.repairStatus,
    			                   repair.repairType,
    			                   repair.repairTitle,
    							   Projections.bean(ItemDto.class,
    										item.itemName,
    										item.itemCode,
    	    								item.itemMajorCategory.itemCategoryName,
    	    								item.itemMiddleCategory.itemCategoryName,
    	    								item.itemSubCategory.itemCategoryName)))
    			.from(repair)
    			.join(item).on(item.itemCode.eq(repair.itemR.itemCode))
    			.where(repair.storeR.storeCode.eq(storeCode))
    			.fetch();
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
