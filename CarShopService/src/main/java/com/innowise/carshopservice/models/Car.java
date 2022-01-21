package com.innowise.carshopservice.models;

import com.innowise.carshopservice.enums.car.CAR_STATE;
import lombok.*;

import javax.persistence.*;

@Entity(name="Car")
@Table(name="cars")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Car {
    @Id
    @Column(name = "carid")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long carId;

    @Column(name = "mark")
    private String mark;

    @Column(name = "model")
    private String model;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private CAR_STATE state;

    @Column(name = "mileage")
    private Double mileage;

    @Column(name = "yearofproduction")
    private String yearOfProduction;

}
