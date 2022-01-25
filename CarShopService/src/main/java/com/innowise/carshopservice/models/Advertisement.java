package com.innowise.carshopservice.models;

import com.innowise.carshopservice.enums.advertisement.ACTIVITY_STATUS;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity(name="Advertisement")
@Table(name="advertisements")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Advertisement {
    @Id
    @Column(name = "advertisementid")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long advertisementId;

    @ManyToOne
    @JoinColumn(name = "carid")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "costid")
    private Cost cost;

    @Column(name = "creationdate")
    private Date creationDate;

    @Column(name = "activitystatus")
    @Enumerated(EnumType.STRING)
    private ACTIVITY_STATUS activityStatus;
}
