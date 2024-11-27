package com.kong.cc.repository;

import com.kong.cc.dto.ItemResponseDto;
import com.kong.cc.dto.ItemSearchCondition;
import com.kong.cc.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import static com.kong.cc.entity.QItem.*;
import static com.kong.cc.entity.QItemMajorCategory.*;
import static com.kong.cc.entity.QItemMiddleCategory.*;
import static com.kong.cc.entity.QItemSubCategory.*;
@Repository
public class ItemQuerydslRepositoryImpl implements ItemQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ItemQuerydslRepositoryImpl(EntityManager em, ItemRepository itemRepository) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ItemResponseDto> findItemResponseDtoListByKeyword(String keyword, Pageable pageable) {
        QueryResults<Item> itemQueryResults = queryFactory
                .selectFrom(item)
                .where(item.itemName.like("%"+keyword+"%"))
                .fetchResults();

        List<Item> content = itemQueryResults.getResults();
        long total = itemQueryResults.getTotal();


        Page<Item> itemPage = new PageImpl<>(content,pageable,total);

        return itemPage.map(i -> {
                    try {
                        return ItemResponseDto.builder()
                                .itemCode(i.getItemCode())
                                .itemName(i.getItemName())
                                .itemPrice(i.getItemPrice())
                                .itemUnitQuantity(i.getItemUnitQuantity())
                                .itemUnit(i.getItemUnit())
                                .itemStandard(i.getItemStandard())
                                .itemStorage(i.getItemStorage())
                                .itemCountryOrigin(i.getItemCountryOrigin())
                                .itemMajorCategoryName(i.getItemMajorCategory().getItemCategoryName())
                                .itemMiddleCategoryName(i.getItemMiddleCategory().getItemCategoryName())
                                .itemSubCategoryName(i.getItemSubCategory().getItemCategoryName())
                                .imageUrl(imageUrl(i.getItemImageFile().getFileDirectory(),i.getItemImageFile().getFileName(),i.getItemImageFile().getFileContentType()))
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );


    }

    @Override
    public Page<ItemResponseDto> findItemResponseDtoListByCategory(Page<Item> page) {

        return page.map(i -> {
            try{
                return ItemResponseDto.builder()
                        .itemCode(i.getItemCode())
                        .itemName(i.getItemName())
                        .itemPrice(i.getItemPrice())
                        .itemCapacity(i.getItemCapacity())
                        .itemUnitQuantity(i.getItemUnitQuantity())
                        .itemUnit(i.getItemUnit())
                        .itemStandard(i.getItemStandard())
                        .itemStorage(i.getItemStorage())
                        .itemCountryOrigin(i.getItemCountryOrigin())
                        .itemMajorCategoryName(i.getItemMajorCategory().getItemCategoryName())
                        .itemMiddleCategoryName(i.getItemMiddleCategory().getItemCategoryName())
                        .itemSubCategoryName(i.getItemSubCategory().getItemCategoryName())
                        .imageUrl(imageUrl(i.getItemImageFile().getFileDirectory(),i.getItemImageFile().getFileName(),i.getItemImageFile().getFileContentType()))
                        .build();
            }catch (Exception e){
                throw new RuntimeException(e);
            }





        }
        );



    }

    @Override
    public Page<Item> findItemListByCategory(ItemSearchCondition condition, Pageable pageable) {
        QueryResults<Item> itemQueryResults = queryFactory
                .select(item)
                .from(item)
                .join(item.itemMajorCategory, itemMajorCategory)
                .join(item.itemMiddleCategory, itemMiddleCategory)
                .join(item.itemSubCategory, itemSubCategory)
                .where(majorCategoryEq(condition.getItemCategoryMajorName()),
                        middleCategoryEq(condition.getItemCategoryMiddleName()),
                        subCategoryEq(condition.getItemCategorySubName()))
                .fetchResults();

        List<Item> content = itemQueryResults.getResults();
        long total = itemQueryResults.getTotal();
        return new PageImpl<Item>(content,pageable,total);


    }

    private BooleanExpression majorCategoryEq(String itemCategoryMajorName) {
        return itemCategoryMajorName != null ? itemMajorCategory.itemCategoryName.eq(itemCategoryMajorName) : null;
    }

    private BooleanExpression middleCategoryEq(String itemCategoryMiddleName) {

        return itemCategoryMiddleName != null ? itemMiddleCategory.itemCategoryName.eq(itemCategoryMiddleName) : null;
    }

    private BooleanExpression subCategoryEq(String itemCategorySubName) {
        return itemCategorySubName != null ? itemSubCategory.itemCategoryName.eq(itemCategorySubName) : null;
    }

    private String imageUrl(String fileDirectory,String fileName,String contentType) throws IOException {
        Path imagePath = Paths.get(fileDirectory+fileName);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        base64Image = "data:"+contentType+";base64,"+base64Image;
        return base64Image;
    }
}
