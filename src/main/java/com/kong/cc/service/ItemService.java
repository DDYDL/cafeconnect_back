package com.kong.cc.service;

import com.kong.cc.dto.ItemResponseDto;
import com.kong.cc.dto.ItemSaveForm;
import com.kong.cc.dto.ItemUpdateForm;
import com.kong.cc.entity.*;
import com.kong.cc.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ImageFileRepository imageFileRepository;
    private final ItemMajorCategoryRepository itemMajorCategoryRepository;
    private final ItemMiddleCategoryRepository itemMiddleCategoryRepository;
    private final ItemSubCategoryRepository itemSubCategoryRepository;

    public void saveItem(ItemSaveForm itemSaveForm, MultipartFile multipartFile) {

        ImageFile imageFile = null;
        imageFileRepository.save(imageFile);
        ItemMajorCategory itemMajorCategory = itemMajorCategoryRepository.findByItemCategoryName(itemSaveForm.getItemCategoryMajorName());
        ItemMiddleCategory itemMiddleCategory = itemMiddleCategoryRepository.findByItemCategoryName(itemSaveForm.getItemCategoryMiddleName());
        ItemSubCategory itemSubCategory = itemSubCategoryRepository.findByItemCategoryName(itemSaveForm.getItemCategorySubName());

        Item.ItemBuilder itemBuilder = Item.builder()
                .itemName(itemSaveForm.getItemName())
                .itemPrice(itemSaveForm.getItemPrice())
                .itemCapacity(itemSaveForm.getItemCapacity())
                .itemUnitQuantity(itemSaveForm.getItemUnitQuantity())
                .itemUnit(itemSaveForm.getItemUnit())
                .itemStandard(itemSaveForm.getItemStandard())
                .itemStorage(itemSaveForm.getItemStorage())
                .itemCountryOrigin(itemSaveForm.getItemCountryOrigin())
                .itemImageFile(imageFile);

        if(itemSubCategory != null){
            itemBuilder.itemSubCategory(itemSubCategory);
        }
        if(itemMiddleCategory != null){
            itemBuilder.itemMiddleCategory(itemMiddleCategory);
        }
        if(itemMajorCategory != null){
            itemBuilder.itemMajorCategory(itemMajorCategory);
        }
        Item item = itemBuilder.build();

        itemRepository.save(item);
    }

    public void updateItem(String itemCode, ItemUpdateForm itemUpdateForm, MultipartFile multipartFile) {
        ImageFile imageFile = null;
        imageFileRepository.save(imageFile);

        ItemMajorCategory itemMajorCategory = itemMajorCategoryRepository.findByItemCategoryName(itemUpdateForm.getItemCategoryMajorName());
        ItemMiddleCategory itemMiddleCategory = itemMiddleCategoryRepository.findByItemCategoryName(itemUpdateForm.getItemCategoryMiddleName());
        ItemSubCategory itemSubCategory = itemSubCategoryRepository.findByItemCategoryName(itemUpdateForm.getItemCategorySubName());
        Item item = itemRepository.findByItemCode(itemCode);

        if(item == null){
            throw new IllegalArgumentException("해당하는 아이템이 없습니다");
        }


        item.setItemName(itemUpdateForm.getItemName());
        item.setItemPrice(itemUpdateForm.getItemPrice());
        item.setItemCapacity(itemUpdateForm.getItemCapacity());
        item.setItemUnitQuantity(itemUpdateForm.getItemUnitQuantity());
        item.setItemUnit(itemUpdateForm.getItemUnit());
        item.setItemStandard(itemUpdateForm.getItemStandard());
        item.setItemStorage(itemUpdateForm.getItemStorage());
        item.setItemCountryOrigin(itemUpdateForm.getItemCountryOrigin());
        item.setItemImageFile(imageFile);

        if(itemSubCategory != null){
            item.setItemSubCategory(itemSubCategory);
        }
        if(itemMiddleCategory != null){
            item.setItemMiddleCategory(itemMiddleCategory);
        }
        if(itemMajorCategory != null){
            item.setItemMajorCategory(itemMajorCategory);
        }




    }

    public void deleteItem(String itemCode) {
        Item item = itemRepository.findByItemCode(itemCode);
        if(item == null){
            throw new IllegalArgumentException("해당하는 아이템이 없습니다");
        }

        int wishItemListSize = item.getWishItemList().size();
        int cartListSize = item.getCartList().size();
        int orderListSize = item.getOrderList().size();
        int repairListSize = item.getRepairList().size();
        int stockListSize = item.getStockList().size();

        if(wishItemListSize > 0){
            throw new IllegalArgumentException("wishItemList 존재합니다");
        }
        if(cartListSize > 0){
            throw new IllegalArgumentException("cartList 존재합니다");
        }
        if(orderListSize > 0){
            throw new IllegalArgumentException("orderList 존재합니다");
        }
        if(repairListSize > 0){
            throw new IllegalArgumentException("repairList 존재합니다");
        }
        if(stockListSize > 0){
            throw new IllegalArgumentException("stockList 존재합니다");
        }

        itemRepository.delete(item);
    }

    public ItemResponseDto selectItemByItemCode(String itemCode) {
        Item item = itemRepository.findByItemCode(itemCode);
        if(item == null){
            throw new IllegalArgumentException("해당하는 아이템이 없습니다");
        }
        String itemMajorCategoryName = item.getItemMajorCategory().getItemCategoryName();
        String itemMiddleCategoryName = item.getItemMiddleCategory().getItemCategoryName();
        String itemSubCategoryName = item.getItemSubCategory().getItemCategoryName();
        String imageUrl = null;
        return  ItemResponseDto.builder()
                .itemCode(item.getItemCode())
                .itemName(item.getItemName())
                .itemCapacity(item.getItemCapacity())
                .itemUnitQuantity(item.getItemUnitQuantity())
                .itemUnit(item.getItemUnit())
                .itemStandard(item.getItemStandard())
                .itemStorage(item.getItemStorage())
                .itemCountryOrigin(item.getItemCountryOrigin())
                .itemMajorCategoryName(itemMajorCategoryName)
                .itemMiddleCategoryName(itemMiddleCategoryName)
                .itemSubCategoryName(itemSubCategoryName)
                .imageUrl(imageUrl)
                .build();
    }
}
