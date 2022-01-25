package com.innowise.carshopservice.dto.advertisement;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GetPageDto {
    private List<GetAdvertisementDto> getAdvertisementDto;
    private long total;
}
