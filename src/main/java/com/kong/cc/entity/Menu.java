package com.kong.cc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.kong.cc.dto.MenuDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Menu {
    @Id
    private String menuCode;

    private String menuName;
    private Integer menuPrice;
    private String menuCapacity;

    private String caffeine;
    private String calories;
    private String carbohydrate;
    private String sugar;
    private String natrium;
    private String fat;
    private String protein;
    private String menuStatus;

    @OneToMany(mappedBy="menu", fetch=FetchType.LAZY)
    private List<Sales> salesList = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="menuCategoryNum")
    private MenuCategory menuCategory;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="menuFileNum")
    private ImageFile menuImageFile;

    public MenuDto toDto() {
        return MenuDto.builder()
                .menuCode(menuCode)
                .menuName(menuName)
                .menuPrice(menuPrice)
                .menuCapacity(menuCapacity)
                .caffeine(caffeine)
                .calories(calories)
                .carbohydrate(carbohydrate)
                .sugar(sugar)
                .natrium(natrium)
                .fat(fat)
                .protein(protein)
                .menuStatus(menuStatus)
                .menuCategoryNum(menuCategory.getMenuCategoryNum())
                .menuCategoryName(menuCategory.getMenuCategoryName())
                .build();
    }
}