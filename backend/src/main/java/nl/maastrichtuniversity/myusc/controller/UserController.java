package nl.maastrichtuniversity.myusc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.maastrichtuniversity.myusc.dtos.UserDTO;
import nl.maastrichtuniversity.myusc.dtos.UserDTOMapper;
import nl.maastrichtuniversity.myusc.dtos.UserRequestDTO;
import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.helpers.UrlHelper;
import nl.maastrichtuniversity.myusc.model.*;
import nl.maastrichtuniversity.myusc.service.MyUSCUserDetailsService;
import nl.maastrichtuniversity.myusc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.mysql.cj.conf.PropertyKey.logger;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserDTOMapper userDTOMapper;
    private final MyUSCUserDetailsService myUSCUserDetailsService;
    private final HttpServletRequest request;

    @Autowired
    public UserController(UserService userService, UserDTOMapper userDTOMapper, MyUSCUserDetailsService myUSCUserDetailsService, HttpServletRequest request) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
        this.myUSCUserDetailsService = myUSCUserDetailsService;
        this.request = request;
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
    public ResponseEntity<?> CreateUser(@RequestBody @Valid UserRequestDTO userDTO) {

        var userModel = userDTOMapper.mapToModel(userDTO);
        userModel.setEnabled(true);
        if(!myUSCUserDetailsService.createUser(userModel, userDTO.getRoles())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(UrlHelper.getCurrentUrlWithId(request, userModel.getId())).build();
    }


//        TODO cleanup

//    after implementing to spring security is obsolete --> AuthController handles the login and the above the registration
//@PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody UserDTO userDto) {
//        try {
//            User user = userService.register(userDto);
//            return ResponseEntity.ok(user);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//        @PostMapping("/login")
//        public ResponseEntity<?> login(@RequestBody UserDto userDto) {
//            try {
//                User loggedInUser = userService.login(userDto.getEmail(), userDto.getPassword());
//
//                if (loggedInUser != null) {
//                    return ResponseEntity.ok(loggedInUser);
//                } else {
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to login, please correct your credentials.");
//                }
//            } catch (RuntimeException e) {
//                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//            }
//    }

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
