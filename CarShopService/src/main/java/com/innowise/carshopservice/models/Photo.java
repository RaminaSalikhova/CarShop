package com.innowise.carshopservice.models;

import com.innowise.carshopservice.enums.photo.ACTIVITY_STATUS;
import lombok.*;

import javax.persistence.*;

@Entity(name="Photo")
@Table(name="photos")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Photo {
    @Id
    @Column(name = "photoid")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long photoId;

    @Column(name = "path")
    private String path;

    @ManyToOne
    @JoinColumn(name = "carid")
    private Car car;

    @Column(name = "activitystatus")
    @Enumerated(EnumType.STRING)
    private ACTIVITY_STATUS activityStatus;
}
