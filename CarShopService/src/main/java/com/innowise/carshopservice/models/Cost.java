package com.innowise.carshopservice.models;

import com.innowise.carshopservice.enums.cost.CURRENCY;
import lombok.*;

import javax.persistence.*;

@Entity(name="Cost")
@Table(name="costs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cost {
    @Id
    @Column(name = "costid")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long costId;

    @Column(name = "value")
    private Double value;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CURRENCY currency;
}
