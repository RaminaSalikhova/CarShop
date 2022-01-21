package com.innowise.carshopservice.models;

import com.innowise.carshopservice.enums.user.ACCOUNT_ACTIVITY_STATUS;
import com.innowise.carshopservice.enums.user.ROLE_ENUM;
import lombok.*;

import javax.persistence.*;

@Entity(name="User")
@Table(name="Users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ROLE_ENUM role;

    @Column(name = "accountactivitystatus")
    @Enumerated(EnumType.STRING)
    private ACCOUNT_ACTIVITY_STATUS accountActivityStatus;

}
