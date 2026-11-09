package com.vipin.foodcatalogue.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vipin.foodcatalogue.dto.FoodItemDTO;
import com.vipin.foodcatalogue.entity.FoodItem;

@Mapper 
public interface FoodItemMapper {
    FoodItemMapper INSTANCE=Mappers.getMapper(FoodItemMapper.class);
    FoodItem mapFoodItemDTOToFoodItem(FoodItemDTO foodItemDTO);
    FoodItemDTO mapFoodItemToFoodItemDTO(FoodItem foodItem);
}
