package com.innowise.carshopservice.dto.car;

import com.innowise.carshopservice.enums.car.CAR_STATE;
import lombok.Data;

@Data
public class CreateCarDto {
    private String mark;
    private String model;
    private CAR_STATE state;
    private Double mileage;
    private String yearofproduction;
}
