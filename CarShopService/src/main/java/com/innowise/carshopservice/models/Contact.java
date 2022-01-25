package com.innowise.carshopservice.models;

import com.innowise.carshopservice.enums.contact.ACTIVITY_STATUS;
import lombok.*;

import javax.persistence.*;

@Entity(name="Contact")
@Table(name="contacts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Contact {
    @Id
    @Column(name = "contactid")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long contactId;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "advertisementid")
    private Advertisement advertisement;

    @Column(name = "activitystatus")
    @Enumerated(EnumType.STRING)
    private ACTIVITY_STATUS activityStatus;
}
