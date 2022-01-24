package com.innowise.carshopservice.repositories.advertisement;

import com.innowise.carshopservice.enums.advertisement.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Advertisement;
import com.innowise.carshopservice.repositories.CommonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdvertisementRepo extends CommonRepository<Advertisement> {
    @Modifying
    @Query(value = "update Advertisement as u set u.activityStatus = 'deactivated' where u.advertisementId = :id")
    void softDelete(Long id);

    Page<Advertisement> findAllByActivityStatus(Pageable pageable, ACTIVITY_STATUS activityStatus);

    @Query("SELECT p FROM Advertisement p WHERE p.car.model LIKE %?1%"
            + " OR p.car.mark LIKE %?1%"
            + " OR CONCAT(p.car.mileage, '')  LIKE %?1%"
            + " OR CONCAT(p.car.state, '')  LIKE %?1%"
            + " OR p.car.yearOfProduction LIKE %?1%"
            + " OR  CONCAT(p.cost.value, '')  LIKE %?1%"
            + " OR  CONCAT(p.cost.currency, '')LIKE %?1%")
    Page<Advertisement> filtration(Pageable pageable, String keyword);

}
