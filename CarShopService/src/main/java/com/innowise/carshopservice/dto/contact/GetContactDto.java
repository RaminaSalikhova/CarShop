package com.innowise.carshopservice.dto.contact;

import lombok.Data;

@Data
public class GetContactDto {
    private Long contactId;
    private String number;
    private Long advertisementId;
}
