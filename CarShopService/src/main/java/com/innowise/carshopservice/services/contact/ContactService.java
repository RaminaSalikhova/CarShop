package com.innowise.carshopservice.services.contact;

import com.innowise.carshopservice.enums.contact.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Contact;
import com.innowise.carshopservice.repositories.contact.ContactRepo;
import com.innowise.carshopservice.services.CommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContactService extends CommonServiceImpl<Contact, ContactRepo> {
    public ContactService(ContactRepo repo){
        super(repo);
    }

    public List<Contact> findByAdvertisement_AdvertisementId (Long advertisementId) {
        return repo.findAllByAdvertisement_AdvertisementId(advertisementId);
    }

    public List<Contact> findAllByAdvertisement_AdvertisementIdAndActivityStatus_Active (Long advertisementId) {
        return repo.findAllByAdvertisement_AdvertisementIdAndActivityStatus(advertisementId, ACTIVITY_STATUS.active);
    }

    @Transactional
    public void softDelete(Long id) {
        repo.softDelete(id);
    }
}
