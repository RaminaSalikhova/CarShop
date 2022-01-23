package com.innowise.carshopservice.services.advertisement;

import com.innowise.carshopservice.models.Advertisement;
import com.innowise.carshopservice.repositories.advertisement.AdvertisementRepo;
import com.innowise.carshopservice.services.CommonServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AdvertisementService extends CommonServiceImpl<Advertisement, AdvertisementRepo> {
    public AdvertisementService(AdvertisementRepo repo) {
        super(repo);
    }

    @Transactional
    public void softDelete(Long id) {
        repo.softDelete(id);
    }
}
