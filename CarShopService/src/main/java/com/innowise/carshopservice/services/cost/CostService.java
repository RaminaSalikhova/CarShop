package com.innowise.carshopservice.services.cost;

import com.innowise.carshopservice.models.Cost;
import com.innowise.carshopservice.repositories.cost.CostRepo;
import com.innowise.carshopservice.services.CommonServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CostService extends CommonServiceImpl<Cost, CostRepo> {
    public CostService(CostRepo repo){
        super(repo);
    }
}
