package com.innowise.carshopservice.services.photo;

import com.innowise.carshopservice.enums.photo.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Photo;
import com.innowise.carshopservice.repositories.photo.PhotoRepo;
import com.innowise.carshopservice.services.CommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PhotoService extends CommonServiceImpl<Photo, PhotoRepo> {
    public PhotoService(PhotoRepo repo){
        super(repo);
    }

    public List<Photo> findAllByActivityStatusAndCarId(Long carId){
        return repo.findAllByCar_CarIdAndActivityStatus(carId, ACTIVITY_STATUS.active);
    }

    public Photo findPhotoByPath(String path){
        return repo.findByPath(path);
    }

    @Transactional
    public void softDelete(Long id) {
        repo.softDelete(id);
    }
}
