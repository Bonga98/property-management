package com.mycompany.property_management.dto;

import lombok.Getter;
import lombok.Setter;


//Uses Lombok to generate the getters and setters
@Getter
@Setter
public class PropertyDTO {

    private Long id;
    private String title;
    private String description;
    private double price;
    private String address;
    private Long userId;
}
