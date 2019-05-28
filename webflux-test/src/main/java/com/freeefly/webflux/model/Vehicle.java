package com.freeefly.webflux.model;

import lombok.Data;

@Data
public class Vehicle {

    private String carPateNumber;
    private Integer weight;
    private Integer speed;
    private String color;
    private Integer modelYear;
    private String gasType;

}
