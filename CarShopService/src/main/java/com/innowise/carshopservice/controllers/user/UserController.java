package com.innowise.carshopservice.controllers.user;

import com.innowise.carshopservice.dto.user.AuthToken;
import com.innowise.carshopservice.dto.user.CreateUserDto;
import com.innowise.carshopservice.dto.user.GetUserDto;
import com.innowise.carshopservice.dto.user.LoginUserDto;
import com.innowise.carshopservice.enums.user.ACCOUNT_ACTIVITY_STATUS;
import com.innowise.carshopservice.enums.user.ROLE_ENUM;
import com.innowise.carshopservice.models.User;
import com.innowise.carshopservice.security.jwt.JwtTokenHelper;
import com.innowise.carshopservice.services.user.UserService;
import com.innowise.carshopservice.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/carshop")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final UserService userService;

    @Autowired
    private JwtTokenHelper jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @PostMapping(value = "/users/register",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(@RequestBody CreateUserDto createUserDto) {
        User user = modelMapper.map(createUserDto, User.class);
        Optional<User> existedUser = userService.findUserByEmail(user.getEmail());
        if(!ValidationUtil.validateEmail(createUserDto.getEmail())){
            return new ResponseEntity<>("The email view should be 'email@mail.com' view ", HttpStatus.BAD_REQUEST);
        }
        if (existedUser.isPresent()) {
            return new ResponseEntity<>("The email already exists", HttpStatus.BAD_REQUEST);
        }
        if(user.getPassword().length()<5){
            return new ResponseEntity<>("Password should have at least 5 symbols", HttpStatus.BAD_REQUEST);
        }
        String pass=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        user.setAccountActivityStatus(ACCOUNT_ACTIVITY_STATUS.active);
        user.setRole(ROLE_ENUM.user);
        userService.save(user);
        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
    }

    @PostMapping(value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginUser(@RequestBody LoginUserDto authenticationRequest) {
        final User userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());

        if (!bCryptPasswordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
            return new ResponseEntity<>("Invalid username or password", FORBIDDEN);
        }

        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthToken(token, userDetails.getRole().toString(), userDetails.getUserId()));
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.softDelete(id);
        return new ResponseEntity<>("Successful operation", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/users/")
    public List<GetUserDto> findAll() {
        List<User> users=userService.findAllActiveAccounts();
        List<GetUserDto> getUserDtos = new ArrayList<>();
        for(User user: users){
            getUserDtos.add(modelMapper.map(user, GetUserDto.class));
        }
        return getUserDtos;
    }

    @GetMapping(value = "/users/{userId}")
    public GetUserDto getUserById(@PathVariable("userId") Long id) {
        return modelMapper.map(userService.findById(id), GetUserDto.class);
    }
}
