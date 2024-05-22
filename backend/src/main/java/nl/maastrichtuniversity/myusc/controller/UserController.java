package nl.maastrichtuniversity.myusc.controller;

import nl.maastrichtuniversity.myusc.model.*;
import nl.maastrichtuniversity.myusc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        try {
            User user = userService.register(userDto);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody UserDto userDto) {
            try {
                User loggedInUser = userService.login(userDto.getEmail(), userDto.getPassword());

                if (loggedInUser != null) {
                    return ResponseEntity.ok(loggedInUser);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to login, please correct your credentials.");
                }
            } catch (RuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
    }

    @PutMapping("/updateUserType/{id}")
    public ResponseEntity<User> updateUserType(@PathVariable Long id, @RequestBody UserType newUserType) {
        User user = userService.getUser(id);
        if (user != null) {
            user.setUserType(newUserType);
            userService.updateUser(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }



}
