package com.innowise.carshopservice.dto.user;

import com.innowise.carshopservice.enums.user.ACCOUNT_ACTIVITY_STATUS;
import com.innowise.carshopservice.enums.user.ROLE_ENUM;
import lombok.Data;

@Data
public class CreateUserDto {
    private String email;
    private String name;
    private String surname;
    private String password;
}
