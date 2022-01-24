package com.innowise.carshopservice.services.photo;

import com.innowise.carshopservice.enums.car.CAR_STATE;
import com.innowise.carshopservice.enums.photo.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Car;
import com.innowise.carshopservice.models.Photo;
import com.innowise.carshopservice.repositories.photo.PhotoRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PhotoServiceTest {

    private PhotoRepo photoRepo;

    private PhotoService photoService;

    public PhotoServiceTest() {
        photoRepo = Mockito.mock(PhotoRepo.class);
        photoService = new PhotoService(photoRepo);
    }

    @Test
    void findAllByActivityStatusAndCarId() {
        Photo img1 = new Photo();
        img1.setPhotoId(1L);
        Car car = new Car();
        car.setCarId(1L);
        car.setMileage(2330D);
        car.setState(CAR_STATE.onParts);
        car.setYearOfProduction("2000");
        img1.setCar(car);
        img1.setPath("image1");
        img1.setActivityStatus(ACTIVITY_STATUS.active);
        Photo img2 = new Photo();
        img2.setPhotoId(2L);
        img2.setCar(car);
        img2.setPath("image2");
        img2.setActivityStatus(ACTIVITY_STATUS.active);
        Photo img3 = new Photo();
        img3.setPhotoId(3L);
        img3.setCar(car);
        img3.setPath("image3");
        img3.setActivityStatus(ACTIVITY_STATUS.active);
        List<Photo> expectedResult = new ArrayList<>();
        expectedResult.add(img1);
        expectedResult.add(img2);
        expectedResult.add(img3);

        Mockito.doReturn(expectedResult).when(photoRepo).findAllByCar_CarIdAndActivityStatus(img1.getCar().getCarId(), ACTIVITY_STATUS.active);

        List<Photo> result = photoService.findAllByActivityStatusAndCarId(1L);
        assertEquals(3, result.size());
    }

    @Test
    void findPhotoByPath() {
        Photo expectedResult = new Photo();
        expectedResult.setPhotoId(1L);
        Car car = new Car();
        car.setCarId(1L);
        car.setMileage(2330D);
        car.setState(CAR_STATE.onParts);
        car.setYearOfProduction("2000");
        expectedResult.setCar(car);
        expectedResult.setPath("image1");
        expectedResult.setActivityStatus(ACTIVITY_STATUS.active);
        Mockito.doReturn(expectedResult).when(photoRepo).findByPath("image1");

        Photo result = photoService.findPhotoByPath("image1");
        assertEquals(expectedResult, result);
    }
}