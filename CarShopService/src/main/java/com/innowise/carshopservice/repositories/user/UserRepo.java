package com.innowise.carshopservice.repositories.user;

import com.innowise.carshopservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Modifying
    @Query(value = "update User as u set u.accountActivityStatus = 'deactivated' where u.userId = :id")
    void softDelete(Long id);

}
