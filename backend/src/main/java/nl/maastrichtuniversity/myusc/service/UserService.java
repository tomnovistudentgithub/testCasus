package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.model.*;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User register(UserDto userDto) {
//        if (userDto.getUserType() == null) {
//            throw new IllegalArgumentException("User type must not be null");
//        }

        User user;
        switch (userDto.getUserType()) {
            case STUDENT:
                user = new Student();
                ((Student) user).setDepartment(userDto.getDepartment());
                break;
            case STAFF:
                user = new Staff();
                ((Staff) user).setDepartment(userDto.getDepartment());
                break;
            case ADMIN:
                user = new Admin();
                ((Admin) user).setDepartment(userDto.getDepartment());
                break;
            default:
                throw new IllegalArgumentException("Invalid user type");
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUserType(userDto.getUserType());
        String unencryptedPassword = userDto.getPassword();
        String encodedPassword = passwordEncoder.encode(unencryptedPassword);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

}
