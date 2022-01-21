package com.innowise.carshopservice.controllers.user;

import com.innowise.carshopservice.dto.user.AuthToken;
import com.innowise.carshopservice.dto.user.CreateUserDto;
import com.innowise.carshopservice.dto.user.LoginUserDto;
import com.innowise.carshopservice.models.User;
import com.innowise.carshopservice.security.jwt.JwtTokenHelper;
import com.innowise.carshopservice.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/carshop")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenHelper jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping(value = "/user/register",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(@RequestBody CreateUserDto createUserDto) {
        User user = modelMapper.map(createUserDto, User.class);
        Optional<User> existedUser = userService.findUserByEmail(user.getEmail());
        if (existedUser.isPresent()) {
            return new ResponseEntity<>("The email already exists", HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword().length()<5){
            return new ResponseEntity<>("Password should have at least 5 symbols", HttpStatus.BAD_REQUEST);
        }
        userService.createUser(user);
        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
    }

    @PostMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginUser(@RequestBody LoginUserDto authenticationRequest) {
        final User userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());

        if (!bCryptPasswordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
            return new ResponseEntity<>("Invalid username or password", FORBIDDEN);
        }

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthToken(token, userDetails.getRole().toString(), userDetails.getUserId()));
    }

}
