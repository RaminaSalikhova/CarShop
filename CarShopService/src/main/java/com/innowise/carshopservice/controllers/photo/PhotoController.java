package com.innowise.carshopservice.controllers.photo;


import com.innowise.carshopservice.enums.photo.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Car;
import com.innowise.carshopservice.models.Photo;
import com.innowise.carshopservice.services.car.CarService;
import com.innowise.carshopservice.services.photo.PhotoService;
import com.innowise.carshopservice.utils.FileUploadUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carshop")
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoController {

    @Autowired
    private final CarService carService;

    @Autowired
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService, CarService carService) {
        super();
        this.photoService = photoService;
        this.carService = carService;
    }

    @PostMapping(value = "/cars/{carId}/photos/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity addPhoto(@RequestParam("carId") Long carId, @RequestParam(value = "image", required = false) MultipartFile image) {
        Optional<Car> car = Optional.ofNullable(carService.findById(carId));
        if (car.isPresent()) {
            Date date = new Date();
            String fileName = date.getTime() + ".jpg";
            String uploadDir = "C:\\Users\\Administrator\\Desktop\\TEST\\Photos";

            FileUploadUtil.saveFile(uploadDir, fileName, image);

            Photo photo = new Photo();
            photo.setPath(fileName);
            photo.setCar(car.get());
            photo.setActivityStatus(ACTIVITY_STATUS.active);
            photoService.save(photo);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/cars/{carId}/photos/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Photo> getPhotosByCarId(@PathVariable("carId") Long carId) {
        return photoService.findAllByActivityStatusAndCarId(carId);
    }

    @GetMapping(value = "/photos/{filename}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPhotoByPath(@PathVariable("filename") String filename) {
        String path="C:\\Users\\Administrator\\Desktop\\TEST\\Photos\\" +filename;
        Optional<Photo> photo = Optional.ofNullable(photoService.findPhotoByPath(filename));
        byte[] byteImage;
        if (photo.isPresent()) {
            try {
                File file = new File(path);
                byteImage = FileUtils.readFileToByteArray(file);
                return byteImage;
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return new byte[0];
    }

    @DeleteMapping(value = "/photos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable Long id) {
        photoService.softDelete(id);
        return ResponseEntity.ok().build();
    }

}
