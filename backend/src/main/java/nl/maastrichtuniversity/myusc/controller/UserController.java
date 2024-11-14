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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping("/uploadPicture")
    public ResponseEntity<?> uploadPicture(@RequestParam("file") MultipartFile file) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(username);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        long maxFileSize = 2 * 1024 * 1024;
        if (file.getSize() > maxFileSize) {
            return new ResponseEntity<>("File size exceeds the limit of 2MB", HttpStatus.BAD_REQUEST);
        }

        String contentType = file.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            return new ResponseEntity<>("Only JPEG and PNG formats are allowed", HttpStatus.BAD_REQUEST);
        }

        try {
            userService.saveUserPicture(user.getId(), file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/downloadPicture")
    public ResponseEntity<byte[]> downloadPicture() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] picture = userService.getUserPicture(user.getId());
        if (picture == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentDispositionFormData("attachment", "picture.jpg");

        return new ResponseEntity<>(picture, headers, HttpStatus.OK);
    }

    @DeleteMapping("/deletePicture/{id}")
    public ResponseEntity<?> deletePicture(@PathVariable Long id) {
        User user = userService.getUser(id);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userService.deleteUserPicture(id);
        return ResponseEntity.ok().build();
    }

}
