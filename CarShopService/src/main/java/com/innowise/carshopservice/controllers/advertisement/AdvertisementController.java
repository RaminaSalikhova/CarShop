package com.innowise.carshopservice.controllers.advertisement;

import com.innowise.carshopservice.dto.advertisement.CreateAdvertisementDto;
import com.innowise.carshopservice.dto.advertisement.GetAdvertisementDto;
import com.innowise.carshopservice.dto.advertisement.UpdateAdvertisementDto;
import com.innowise.carshopservice.enums.advertisement.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.*;
import com.innowise.carshopservice.services.advertisement.AdvertisementService;
import com.innowise.carshopservice.services.car.CarService;
import com.innowise.carshopservice.services.contact.ContactService;
import com.innowise.carshopservice.services.cost.CostService;
import com.innowise.carshopservice.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/carshop")
@CrossOrigin(origins = "http://localhost:3000")
public class AdvertisementController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private CostService costService;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    public AdvertisementController(AdvertisementService advertisementService, ContactService contactService,
                                   CostService costService, CarService carService, UserService userService) {
        super();
        this.advertisementService = advertisementService;
        this.contactService = contactService;
        this.costService = costService;
        this.carService = carService;
        this.userService = userService;
    }

    @PostMapping(value = "/advertisement/addAdvertisement/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addAdvertisement(@RequestBody CreateAdvertisementDto createAdvertisementDto) {
        Advertisement advertisement = new Advertisement();
        if(!validateYear(createAdvertisementDto.getYearofproduction())){
            return new ResponseEntity<>("Incorrect year input", HttpStatus.BAD_REQUEST);
        }
        if(createAdvertisementDto.getValue()<0){
            return new ResponseEntity<>("Incorrect cost input", HttpStatus.BAD_REQUEST);
        }

        User user = userService.findById(createAdvertisementDto.getUserId());
        advertisement.setUser(user);
        Car car = modelMapper.map(createAdvertisementDto, Car.class);
        Cost cost = modelMapper.map(createAdvertisementDto, Cost.class);
        advertisement.setCost(cost);
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        advertisement.setCreationDate(date);
        advertisement.setActivityStatus(ACTIVITY_STATUS.active);

        advertisement.setCar(carService.save(car));
        costService.save(cost);
        advertisementService.save(advertisement);

        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
    }

    @GetMapping("/advertisement/getAdvertisementById/{advertisementId}")
    public GetAdvertisementDto getAdvertisementById(@PathVariable("advertisementId") Long id) {
        Advertisement advertisement = advertisementService.findById(id);
        GetAdvertisementDto getAdvertisementDto = modelMapper.map(advertisement, GetAdvertisementDto.class);
        getAdvertisementDto.setName(advertisement.getUser().getName());
        List<Contact> contacts = contactService.findAllByAdvertisement_AdvertisementIdAndActivityStatus_Active(advertisement.getAdvertisementId());
        List<String> numbers=new ArrayList<>();
        for (Contact el : contacts) {
            numbers.add(el.getNumber());
        }
        getAdvertisementDto.setContact(numbers);
        return getAdvertisementDto;
    }

    @GetMapping("/advertisement/getAllAdvertisements/")
    public List<GetAdvertisementDto> getAllAdvertisements() {
        List<Advertisement> allAdvertisements = advertisementService.findAll();
        List<GetAdvertisementDto> activeAdvertisements = new ArrayList<>();
        for (Advertisement advertisement : allAdvertisements) {
            if (advertisement.getActivityStatus().equals(ACTIVITY_STATUS.active)) {
                GetAdvertisementDto getAdvertisementDto = modelMapper.map(advertisement, GetAdvertisementDto.class);
                getAdvertisementDto.setName(advertisement.getUser().getName());
                List<Contact> contacts = contactService.findAllByAdvertisement_AdvertisementIdAndActivityStatus_Active(advertisement.getAdvertisementId());
                List<String> numbers=new ArrayList<>();
                for (Contact el : contacts) {
                    numbers.add(el.getNumber());
                }
                getAdvertisementDto.setContact(numbers);
                activeAdvertisements.add(getAdvertisementDto);
            }
        }
        return activeAdvertisements;
    }

    @PostMapping(value = "/advertisement/editAdvertisementById/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editAdvertisementById(@RequestBody UpdateAdvertisementDto updateAdvertisementDto) {
        Optional<Advertisement> advertisement = Optional.ofNullable(advertisementService.findById(updateAdvertisementDto.getAdvertisementId()));
        if (advertisement.isPresent()) {
            Advertisement updateAdvertisement = new Advertisement();
            if(!validateYear(updateAdvertisementDto.getYearofproduction())){
                return new ResponseEntity<>("Incorrect year input", HttpStatus.BAD_REQUEST);
            }
            if(updateAdvertisementDto.getValue()<0){
                return new ResponseEntity<>("Incorrect cost input", HttpStatus.BAD_REQUEST);
            }

            updateAdvertisement.setAdvertisementId(updateAdvertisementDto.getAdvertisementId());

            User user = userService.findById(updateAdvertisementDto.getUserId());
            updateAdvertisement.setUser(user);

            Car car = modelMapper.map(updateAdvertisementDto, Car.class);
            Optional<Car> foundCar=Optional.ofNullable(carService.findById(updateAdvertisementDto.getUserId()));
            if(foundCar.isEmpty()){
                return new ResponseEntity<>("No such car", HttpStatus.BAD_REQUEST);
            }
            car.setCarId(foundCar.get().getCarId());

            Cost cost = modelMapper.map(updateAdvertisementDto, Cost.class);
            Optional<Cost> foundCost=Optional.ofNullable(costService.findById(updateAdvertisementDto.getCostId()));
            if(foundCost.isEmpty()){
                return new ResponseEntity<>("No such cost", HttpStatus.BAD_REQUEST);
            }
            cost.setCostId(foundCost.get().getCostId());
            updateAdvertisement.setCreationDate(advertisement.get().getCreationDate());
            updateAdvertisement.setActivityStatus(advertisement.get().getActivityStatus());

            updateAdvertisement.setCar(carService.update(car));
            updateAdvertisement.setCost(costService.update(cost));
            advertisementService.update(updateAdvertisement);

            return new ResponseEntity<>("Successful operation", HttpStatus.OK);
        }
        return new ResponseEntity<>("No such advertisement", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/advertisement/deleteAdvertisementById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAdvertisementById(@PathVariable Long id) {
        advertisementService.softDelete(id);
        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
    }

    public boolean validateYear(String number) {
        if(!isNumeric(number)){
            return false;
        }
        if (Integer.parseInt(number)>2022 ||  Integer.parseInt(number)<1900){
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
