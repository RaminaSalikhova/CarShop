package com.innowise.carshopservice.services.advertisement;

import com.innowise.carshopservice.enums.advertisement.ACTIVITY_STATUS;
import com.innowise.carshopservice.models.Advertisement;
import com.innowise.carshopservice.repositories.advertisement.AdvertisementRepo;
import com.innowise.carshopservice.services.CommonServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Advertisement> filtration(Pageable pageable, String keyword){
        return repo.filtration(pageable,keyword);
    }

    public Page<Advertisement> findAllByPage(Pageable pageable){ return repo.findAllByActivityStatus(pageable, ACTIVITY_STATUS.active);}
}
