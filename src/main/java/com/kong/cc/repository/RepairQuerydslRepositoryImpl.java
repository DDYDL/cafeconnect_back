package com.kong.cc.repository;

import com.kong.cc.dto.RepairResponseDto;
import com.kong.cc.dto.RepairSearchCondition;
import com.kong.cc.entity.QRepair;
import com.kong.cc.entity.Repair;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.kong.cc.entity.QRepair.*;

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


}
