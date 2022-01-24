package com.innowise.carshopservice.dto.photo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddPhotoDto {
    private Long carId;
    private MultipartFile image ;
}
