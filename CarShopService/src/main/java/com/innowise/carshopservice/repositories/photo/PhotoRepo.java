package com.innowise.carshopservice.repositories.photo;

import com.innowise.carshopservice.enums.photo.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Photo;
import com.innowise.carshopservice.repositories.CommonRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotoRepo extends CommonRepository<Photo> {
    List<Photo> findAllByCar_CarIdAndActivityStatus(Long carId, ACTIVITY_STATUS activityStatus);

    Photo findByPath(String path);

    @Modifying
    @Query(value = "update Photo as u set u.activityStatus = 'deactivated' where u.photoId = :id")
    void softDelete(Long id);
}
