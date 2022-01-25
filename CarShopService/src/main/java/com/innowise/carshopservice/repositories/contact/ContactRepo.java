package com.innowise.carshopservice.repositories.contact;

import com.innowise.carshopservice.enums.contact.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Contact;
import com.innowise.carshopservice.repositories.CommonRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepo extends CommonRepository<Contact> {
    List<Contact> findAllByAdvertisement_AdvertisementId(Long advertisementId);

    List<Contact> findAllByAdvertisement_AdvertisementIdAndActivityStatus(Long advertisementId, ACTIVITY_STATUS activityStatus);

    @Modifying
    @Query(value = "update Contact as u set u.activityStatus = 'deactivated' where u.contactId = :id")
    void softDelete(Long id);
}
