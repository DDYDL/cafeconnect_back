package com.kong.cc.repository;

import com.kong.cc.dto.MenuResponseDto;
import com.kong.cc.entity.Menu;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import static com.kong.cc.entity.QMenu.*;
import static com.kong.cc.entity.QMenuCategory.*;

@Repository
public class MenuQuerydslRepositoryImpl implements MenuQuerydslRepository {


    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MenuQuerydslRepositoryImpl(EntityManager em, MenuRepository menuRepository) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MenuResponseDto> findMenuResponseDtoListByKeyword(String keyword, Pageable pageable) {


        QueryResults<Menu> menuQueryResults = queryFactory.selectFrom(menu)
                .where(menu.menuName.like("%"+keyword+"%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Menu> content = menuQueryResults.getResults();
        long total = menuQueryResults.getTotal();

        Page<Menu> menuPage = new PageImpl<>(content,pageable,total);
        return menuPage.map(m -> {
                    try {
                        return MenuResponseDto.builder()
                                .menuCode(m.getMenuCode())
                                .menuName(m.getMenuName())
                                .menuPrice(m.getMenuPrice())
                                .menuCapacity(m.getMenuCapacity())
                                .caffeine(m.getCaffeine())
                                .calories(m.getCalories())
                                .carbohydrate(m.getCarbohydrate())
                                .sugar(m.getSugar())
                                .natrium(m.getNatrium())
                                .fat(m.getFat())
                                .protein(m.getProtein())
                                .menuStatus(m.getMenuStatus())
                                .menuCategoryName(m.getMenuCategory().getMenuCategoryName())
                                .imageUrl(imageUrl(m.getMenuImageFile().getFileDirectory(),m.getMenuImageFile().getFileName(),m.getMenuImageFile().getFileContentType()))
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );






    }

    @Override
    public Page<MenuResponseDto> findMenuResponseDtoListByCategory(String categoryName, Pageable pageable) {

        QueryResults<Menu> menuQueryResults = queryFactory
                .select(menu)
                .from(menu)
                .join(menu.menuCategory, menuCategory)
                .where(menuCategoryeq(categoryName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Menu> content = menuQueryResults.getResults();
        long total = menuQueryResults.getTotal();

        Page<Menu> menuPage = new PageImpl<>(content,pageable,total);
        Page<MenuResponseDto> menuResponseDtoPage = menuPage.map(m -> {
                    try {
                        return MenuResponseDto.builder()
                                .menuCode(m.getMenuCode())
                                .menuName(m.getMenuName())
                                .menuPrice(m.getMenuPrice())
                                .menuCapacity(m.getMenuCapacity())
                                .caffeine(m.getCaffeine())
                                .calories(m.getCalories())
                                .carbohydrate(m.getCarbohydrate())
                                .sugar(m.getSugar())
                                .natrium(m.getNatrium())
                                .fat(m.getFat())
                                .protein(m.getProtein())
                                .menuStatus(m.getMenuStatus())
                                .menuCategoryName(m.getMenuCategory().getMenuCategoryName())
                                .imageUrl(imageUrl(m.getMenuImageFile().getFileDirectory(),m.getMenuImageFile().getFileName(),m.getMenuImageFile().getFileContentType()))
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return menuResponseDtoPage;

    }

    private BooleanExpression menuCategoryeq(String categoryName) {
        return (categoryName != null && StringUtils.hasText(categoryName)) ? menuCategory.menuCategoryName.eq(categoryName) : null;
    }

    private String imageUrl(String fileDirectory,String fileName,String contentType) throws IOException {

        Path imagePath = Paths.get(fileDirectory+fileName);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        String imageUrl = "data:"+contentType+";base64,"+base64Image;

        return imageUrl;
    }
}
