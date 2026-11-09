package com.vipin.foodcatalogue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import com.vipin.foodcatalogue.dto.FoodCataloguePage;
import com.vipin.foodcatalogue.dto.FoodItemDTO;
import com.vipin.foodcatalogue.dto.Restaurant;
import com.vipin.foodcatalogue.entity.FoodItem;
import com.vipin.foodcatalogue.mapper.FoodItemMapper;
import com.vipin.foodcatalogue.repo.FoodItemRepo;

class FoodCatalogueServiceTest {

    @Mock
    private FoodItemRepo foodItemRepo;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec<?> restaurantRequest;

    @Mock
    private RestClient.RequestHeadersSpec<?> requestHeaders;

    @Mock
    private RestClient.ResponseSpec response;

    @InjectMocks
    private FoodCatalogueService foodCatalogueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doReturn(restaurantRequest).when(restClient).get();
        doReturn(requestHeaders).when(restaurantRequest)
                .uri("http://RESTAURANT-SERVICE/restaurant/fetchById/{id}", 1);
        doReturn(requestHeaders).when(requestHeaders).accept(MediaType.APPLICATION_JSON);
        when(requestHeaders.retrieve()).thenReturn(response);
    }

    @Test
    void addFoodItemSavesAndReturnsMappedFoodItem() {
        FoodItemDTO foodItemDTO = new FoodItemDTO(1, "Biryani", "Chicken biryani", false, 200, 1, 0);
        FoodItem foodItem = FoodItemMapper.INSTANCE.mapFoodItemDTOToFoodItem(foodItemDTO);
        when(foodItemRepo.save(foodItem)).thenReturn(foodItem);

        FoodItemDTO response = foodCatalogueService.addFoodItem(foodItemDTO);

        assertEquals(foodItemDTO, response);
        verify(foodItemRepo, times(1)).save(foodItem);
    }

    @Test
    void fetchFoodCataloguePageReturnsItemsAndRestaurant() {
        Integer restaurantId = 1;
        List<FoodItem> foodItems = List.of(
                new FoodItem(1, "Biryani", "Chicken biryani", false, 200, restaurantId, 0));
        Restaurant restaurant = new Restaurant(restaurantId, "Address", "Restaurant", "City", "Description");
        when(foodItemRepo.findByRestaurantId(restaurantId)).thenReturn(foodItems);
        when(response.body(Restaurant.class)).thenReturn(restaurant);

        FoodCataloguePage response = foodCatalogueService.fetchFoodCataloguePageDetails(restaurantId);

        assertEquals(foodItems, response.getFoodItemsList());
        assertEquals(restaurant, response.getRestaurant());
        verify(foodItemRepo, times(1)).findByRestaurantId(restaurantId);
        verify(restaurantRequest, times(1))
                .uri("http://RESTAURANT-SERVICE/restaurant/fetchById/{id}", restaurantId);
    }
}
