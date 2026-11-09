package com.vipin.foodcatalogue.service;

import com.vipin.foodcatalogue.repo.FoodItemRepo;

import org.springframework.http.MediaType;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vipin.foodcatalogue.dto.FoodCataloguePage;
import com.vipin.foodcatalogue.dto.FoodItemDTO;
import com.vipin.foodcatalogue.dto.Restaurant;
import com.vipin.foodcatalogue.entity.FoodItem;
import com.vipin.foodcatalogue.mapper.FoodItemMapper;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;

@Service 
public class FoodCatalogueService {

    private final FoodItemRepo foodItemRepo;
    private final RestClient foodCatalogueRestClient;
    FoodCatalogueService(FoodItemRepo foodItemRepo, RestClient restClientBuilder) {
        this.foodItemRepo = foodItemRepo;
        this.foodCatalogueRestClient=restClientBuilder;
    }

    public FoodItemDTO addFoodItem(FoodItemDTO foodItemDTO) {
        FoodItem savedFoodItem=foodItemRepo.save(FoodItemMapper.INSTANCE.mapFoodItemDTOToFoodItem(foodItemDTO));
        return FoodItemMapper.INSTANCE.mapFoodItemToFoodItemDTO(savedFoodItem);

    }

    public FoodCataloguePage fetchFoodCataloguePageDetails(Integer id) {
        //food Items
        List<FoodItem> foodItemList=fetchFoodItemsList(id);
        //restaurant details
        Restaurant restaurant=fetchRestaurantDetailsFromRestaurantMS(id);

        return createFoodCataloguePage(foodItemList,restaurant);
    }

    private Restaurant fetchRestaurantDetailsFromRestaurantMS(Integer id) {
        
        Restaurant restaurant= foodCatalogueRestClient
        .get()
        .uri("http://RESTAURANT-SERVICE/restaurant/fetchById/{id}",id)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(Restaurant.class);
        return restaurant;
    }

    private List<FoodItem> fetchFoodItemsList(Integer id) {
       return foodItemRepo.findByRestaurantId(id);
    }

    private FoodCataloguePage createFoodCataloguePage(List<FoodItem> foodItemList, Restaurant restaurant) {
        FoodCataloguePage foodCataloguePage=new FoodCataloguePage();
        foodCataloguePage.setFoodItemsList(foodItemList);
        foodCataloguePage.setRestaurant(restaurant);
        return foodCataloguePage;
    }

}
