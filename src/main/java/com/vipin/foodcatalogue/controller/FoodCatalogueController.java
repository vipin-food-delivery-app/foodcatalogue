package com.vipin.foodcatalogue.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vipin.foodcatalogue.dto.FoodCataloguePage;
import com.vipin.foodcatalogue.dto.FoodItemDTO;
import com.vipin.foodcatalogue.service.FoodCatalogueService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequestMapping ("/foodCatalogue")
@CrossOrigin 
public class FoodCatalogueController {
    private FoodCatalogueService foodCatalogueService;

    public FoodCatalogueController(FoodCatalogueService foodCatalogueService) {
        this.foodCatalogueService = foodCatalogueService;
    }

    @PostMapping("/addFoodItem")
    public ResponseEntity<FoodItemDTO> addFoodItem(@RequestBody FoodItemDTO foodItemDTO) {
            FoodItemDTO foodItemSaved=foodCatalogueService.addFoodItem(foodItemDTO);
            return new ResponseEntity<>(foodItemSaved,HttpStatus.CREATED);
    }


    @GetMapping("/fetchRestaurantAndFoodItemsById/{id}")
    public ResponseEntity<FoodCataloguePage> fetchRestaurantDetailsWithFoodMenu(@PathVariable  Integer id) {
        FoodCataloguePage foodCataloguePage=foodCatalogueService.fetchFoodCataloguePageDetails(id);
        return new ResponseEntity<>(foodCataloguePage,HttpStatus.OK);
    }
    
    


}
