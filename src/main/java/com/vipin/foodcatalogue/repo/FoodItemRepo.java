package com.vipin.foodcatalogue.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vipin.foodcatalogue.entity.FoodItem;

public interface FoodItemRepo extends JpaRepository<FoodItem,Integer>{
    List<FoodItem> findByRestaurantId(Integer id);

}
