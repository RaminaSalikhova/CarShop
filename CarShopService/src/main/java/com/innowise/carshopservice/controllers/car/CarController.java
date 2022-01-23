package com.innowise.carshopservice.controllers.car;

import com.innowise.carshopservice.dto.car.CreateCarDto;
import com.innowise.carshopservice.dto.car.UpdateCarDto;
import com.innowise.carshopservice.models.Car;
import com.innowise.carshopservice.services.car.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/carshop")
@CrossOrigin(origins = "http://localhost:3000")
public class CarController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CarService carService;


    public CarController(CarService carService) {
        super();
        this.carService = carService;
    }

//    @PostMapping(value = "/car/addCar/",
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity addCar(@RequestBody CreateCarDto createCarDto) {
//        Car createCar = modelMapper.map(createCarDto, Car.class);
//        carService.save(createCar);
//        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
//    }

//    @GetMapping("/car/getCarById/{carId}")
//    public Car getCarById(@PathVariable("carId") Long id) {
//        return carService.findById(id);
//    }
//
//    @PostMapping(value = "/car/editCarById/",
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity editCarById(@RequestBody UpdateCarDto updateCarDto) {
//        Optional<Car> car = Optional.ofNullable(carService.findById(updateCarDto.getCarId()));
//        if (car.isPresent()) {
//            Car updateCar = modelMapper.map(updateCarDto, Car.class);
//            carService.update(updateCar);
//            return new ResponseEntity<>("Successful operation", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("No such car", HttpStatus.BAD_REQUEST);
//    }

}
