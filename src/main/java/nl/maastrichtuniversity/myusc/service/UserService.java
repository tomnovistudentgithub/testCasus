package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.dtos.UserDTO;
import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.model.*;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public User getUserByUserName(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("User with username " + username + " not found"));
    }

    @Transactional(readOnly = true)
    public byte[] getUserPicture(Long userId) {
        User user = getUserById(userId);
        return user.getPicture();
    }

    public void saveUserPicture(Long userId, MultipartFile file) throws IOException {
        User user = getUserById(userId);
        user.setPicture(file.getBytes());
        userRepository.save(user);
    }

    public void deleteUserPicture(Long userId) {
        User user = getUserById(userId);
        user.setPicture(null);
        userRepository.save(user);
    }

}
