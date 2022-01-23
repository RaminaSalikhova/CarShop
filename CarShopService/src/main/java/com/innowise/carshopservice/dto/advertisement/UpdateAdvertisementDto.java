package com.innowise.carshopservice.dto.advertisement;

import com.innowise.carshopservice.enums.car.CAR_STATE;
import com.innowise.carshopservice.enums.cost.CURRENCY;
import lombok.Data;

import java.util.List;

@Data
public class UpdateAdvertisementDto {
    private Long advertisementId;

    private Long carId;
    private String mark;
    private String model;
    private CAR_STATE state;
    private Double mileage;
    private String yearofproduction;

    private Long userId;

    private Long costId;
    private Double value;
    private CURRENCY currency;
}
