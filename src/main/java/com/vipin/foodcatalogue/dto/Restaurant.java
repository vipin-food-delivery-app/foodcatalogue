package com.vipin.foodcatalogue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor 
public class Restaurant {
    private int id;
    private String address;
    private String name;
    private String city;
    private String restaurantDescription;
}
