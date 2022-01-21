package com.innowise.carshopservice.services.user;


import com.innowise.carshopservice.dto.user.CreateUserDto;
import com.innowise.carshopservice.enums.user.ACCOUNT_ACTIVITY_STATUS;
import com.innowise.carshopservice.enums.user.ROLE_ENUM;
import com.innowise.carshopservice.models.User;
import com.innowise.carshopservice.repositories.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo repo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepo repo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repo = repo;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Transactional
    public User createUser(User user) {
        String pass=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        user.setAccountActivityStatus(ACCOUNT_ACTIVITY_STATUS.active);
        user.setRole(ROLE_ENUM.user);
        repo.save(user);
        return user;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(repo.findUserByEmail(email));
    }

    public User findById(Long id) {
        Optional<User> user = repo.findById(id);
        return user.orElse(null);
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