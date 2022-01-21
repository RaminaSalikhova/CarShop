package com.innowise.carshopservice.services.user;


import com.innowise.carshopservice.models.User;
import com.innowise.carshopservice.repositories.user.UserRepo;
import com.innowise.carshopservice.services.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService extends CommonServiceImpl<User, UserRepo> {

    @Autowired
    public UserService(UserRepo repo) {
        super(repo);
    }

    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(repo.findUserByEmail(email));
    }

    public User loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> u = findUserByEmail(login);
        if (u.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return u.get();
    }

    @Transactional
    public void softDelete(Long id) {
        repo.softDelete(id);
    }

}