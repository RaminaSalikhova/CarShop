package com.innowise.carshopservice.repositories.user;

import com.innowise.carshopservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

}
