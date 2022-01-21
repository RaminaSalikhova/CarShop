package com.innowise.carshopservice.services.car;

import com.innowise.carshopservice.models.Car;
import com.innowise.carshopservice.repositories.car.CarRepo;
import com.innowise.carshopservice.services.CommonServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class CarService extends CommonServiceImpl<Car, CarRepo> {

    public CarService(CarRepo repo) {
        super(repo);
    }

}
