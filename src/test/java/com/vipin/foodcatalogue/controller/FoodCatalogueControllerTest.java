package com.vipin.foodcatalogue.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.vipin.foodcatalogue.dto.FoodCataloguePage;
import com.vipin.foodcatalogue.dto.FoodItemDTO;
import com.vipin.foodcatalogue.dto.Restaurant;
import com.vipin.foodcatalogue.entity.FoodItem;
import com.vipin.foodcatalogue.service.FoodCatalogueService;

class FoodCatalogueControllerTest {

    @Mock
    private FoodCatalogueService foodCatalogueService;

    @InjectMocks
    private FoodCatalogueController foodCatalogueController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFoodItemReturnsCreatedFoodItem() {
        FoodItemDTO foodItem = new FoodItemDTO(1, "Biryani", "Chicken biryani", false, 200, 1, 0);
        when(foodCatalogueService.addFoodItem(foodItem)).thenReturn(foodItem);

        ResponseEntity<FoodItemDTO> response = foodCatalogueController.addFoodItem(foodItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(foodItem, response.getBody());
        verify(foodCatalogueService, times(1)).addFoodItem(foodItem);
    }

    @Test
    void fetchRestaurantAndFoodItemsReturnsCataloguePage() {
        Integer restaurantId = 1;
        FoodCataloguePage cataloguePage = new FoodCataloguePage(
                List.of(new FoodItem(1, "Biryani", "Chicken biryani", false, 200, restaurantId, 0)),
                new Restaurant(restaurantId, "Address", "Restaurant", "City", "Description"));
        when(foodCatalogueService.fetchFoodCataloguePageDetails(restaurantId)).thenReturn(cataloguePage);

        ResponseEntity<FoodCataloguePage> response =
                foodCatalogueController.fetchRestaurantDetailsWithFoodMenu(restaurantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cataloguePage, response.getBody());
        verify(foodCatalogueService, times(1)).fetchFoodCataloguePageDetails(restaurantId);
    }
}
