package com.innowise.carshopservice.controllers.advertisement;

import com.innowise.carshopservice.dto.advertisement.CreateAdvertisementDto;
import com.innowise.carshopservice.dto.advertisement.GetAdvertisementDto;
import com.innowise.carshopservice.dto.advertisement.GetPageDto;
import com.innowise.carshopservice.dto.advertisement.UpdateAdvertisementDto;
import com.innowise.carshopservice.enums.advertisement.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.*;
import com.innowise.carshopservice.services.advertisement.AdvertisementService;
import com.innowise.carshopservice.services.car.CarService;
import com.innowise.carshopservice.services.contact.ContactService;
import com.innowise.carshopservice.services.cost.CostService;
import com.innowise.carshopservice.services.user.UserService;
import com.innowise.carshopservice.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carshop")
@CrossOrigin(origins = "http://localhost:3000")
public class AdvertisementController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final AdvertisementService advertisementService;

    @Autowired
    private final ContactService contactService;

    @Autowired
    private final CostService costService;

    @Autowired
    private final CarService carService;

    @Autowired
    private final UserService userService;

    public AdvertisementController(AdvertisementService advertisementService, ContactService contactService,
                                   CostService costService, CarService carService, UserService userService) {
        super();
        this.advertisementService = advertisementService;
        this.contactService = contactService;
        this.costService = costService;
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping(path = "/advertisements/")
    GetPageDto loadPage(Pageable pageable, Optional<String> filtrationValue) {
        Page<Advertisement> allAdvertisements;
        if( filtrationValue.isEmpty() || filtrationValue.equals("")) {
            allAdvertisements = advertisementService.findAllByPage(pageable);
        }else {
            allAdvertisements = advertisementService.filtration(pageable, filtrationValue.get());
        }
        long advertisementsTotalCount = allAdvertisements.getTotalElements();

        List<GetAdvertisementDto> activeAdvertisements = new ArrayList<>();
        for (Advertisement advertisement : allAdvertisements) {
                GetAdvertisementDto getAdvertisementDto = modelMapper.map(advertisement, GetAdvertisementDto.class);
                getAdvertisementDto.setName(advertisement.getUser().getName());
                List<Contact> contacts = contactService.findAllByAdvertisementId(advertisement.getAdvertisementId());
                List<String> numbers=new ArrayList<>();
                for (Contact el : contacts) {
                    numbers.add(el.getNumber());
                }
                getAdvertisementDto.setContact(numbers);
                activeAdvertisements.add(getAdvertisementDto);
        }

        GetPageDto getPageDto=new GetPageDto(activeAdvertisements, advertisementsTotalCount);
        return getPageDto;
    }

    @PostMapping(value = "/advertisements/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addAdvertisement(@RequestBody CreateAdvertisementDto createAdvertisementDto) {
        Advertisement advertisement = new Advertisement();
        if(!ValidationUtil.validateYear(createAdvertisementDto.getYearofproduction())){
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

    @GetMapping("/advertisements/{id}")
    public GetAdvertisementDto getAdvertisementById(@PathVariable("id") Long id) {
        Advertisement advertisement = advertisementService.findById(id);
        GetAdvertisementDto getAdvertisementDto = modelMapper.map(advertisement, GetAdvertisementDto.class);
        getAdvertisementDto.setName(advertisement.getUser().getName());
        List<Contact> contacts = contactService.findAllByAdvertisementId(advertisement.getAdvertisementId());
        List<String> numbers=new ArrayList<>();
        for (Contact el : contacts) {
            numbers.add(el.getNumber());
        }
        getAdvertisementDto.setContact(numbers);
        return getAdvertisementDto;
    }

    @GetMapping("/users/{id}/advertisements/")
    public List<GetAdvertisementDto> getAdvertisementByUserId(@PathVariable("id") Long id) {
        List<Advertisement> advertisements = advertisementService.findAllByUserId(id);
        List<GetAdvertisementDto> getAdvertisementDtoList=new ArrayList<>();
        for(Advertisement advertisement: advertisements) {
            GetAdvertisementDto getAdvertisementDto = modelMapper.map(advertisement, GetAdvertisementDto.class);
            getAdvertisementDto.setName(advertisement.getUser().getName());
            List<Contact> contacts = contactService.findAllByAdvertisementId(advertisement.getAdvertisementId());
            List<String> numbers = new ArrayList<>();
            for (Contact el : contacts) {
                numbers.add(el.getNumber());
            }

            getAdvertisementDto.setContact(numbers);
            getAdvertisementDtoList.add(getAdvertisementDto);
        }
        return getAdvertisementDtoList;
    }

    @PutMapping(value = "/advertisements/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editAdvertisementById(@PathVariable Long id, @RequestBody UpdateAdvertisementDto updateAdvertisementDto) {
        Optional<Advertisement> advertisement = Optional.ofNullable(advertisementService.findById(id));
        if (advertisement.isPresent()) {
            Advertisement updateAdvertisement = new Advertisement();
            if(!ValidationUtil.validateYear(updateAdvertisementDto.getYearofproduction())){
                return new ResponseEntity<>("Incorrect year input", HttpStatus.BAD_REQUEST);
            }
            if(updateAdvertisementDto.getValue()<0){
                return new ResponseEntity<>("Incorrect cost input", HttpStatus.BAD_REQUEST);
            }

            updateAdvertisement.setAdvertisementId(id);

            User user = userService.findById(updateAdvertisementDto.getUserId());
            updateAdvertisement.setUser(user);

            Car car = modelMapper.map(updateAdvertisementDto, Car.class);
            Optional<Car> foundCar=Optional.ofNullable(carService.findById(updateAdvertisementDto.getCarId()));
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

    @DeleteMapping(value = "/advertisements/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAdvertisementById(@PathVariable Long id) {
        advertisementService.softDelete(id);
        return ResponseEntity.ok().build();
    }

}
