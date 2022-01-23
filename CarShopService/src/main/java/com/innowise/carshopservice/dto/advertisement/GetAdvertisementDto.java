package com.innowise.carshopservice.dto.advertisement;

import com.innowise.carshopservice.enums.advertisement.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Car;
import com.innowise.carshopservice.models.Cost;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class GetAdvertisementDto {
    private Long advertisementId;
    private Car car;
    private Long userId;
    private String name;
    private Cost cost;
    private Date creationDate;
    private ACTIVITY_STATUS activityStatus;
    private List<String> contact;
}
