package com.innowise.carshopservice.repositories.advertisement;

import com.innowise.carshopservice.models.Advertisement;
import com.innowise.carshopservice.repositories.CommonRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdvertisementRepo extends CommonRepository<Advertisement> {
    @Modifying
    @Query(value = "update Advertisement as u set u.activityStatus = 'deactivated' where u.advertisementId = :id")
    void softDelete(Long id);
}
