package com.innowise.carshopservice.dto.user;

import lombok.Data;

@Data
public class GetUserDto {
    private Long userId;
    private String email;
    private String name;
    private String surname;
}
