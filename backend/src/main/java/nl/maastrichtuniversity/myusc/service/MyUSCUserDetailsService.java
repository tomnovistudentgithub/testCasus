package nl.maastrichtuniversity.myusc.service;

import jakarta.transaction.Transactional;
import nl.maastrichtuniversity.myusc.entities.Role;
import nl.maastrichtuniversity.myusc.mappers.RoleMapper;
import nl.maastrichtuniversity.myusc.mappers.UserMapper;
import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.model.UserModel;
import nl.maastrichtuniversity.myusc.repository.RoleRepository;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import nl.maastrichtuniversity.myusc.security.MyUSCUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MyUSCUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public MyUSCUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Transactional
    public boolean createUser(UserModel userModel, List<String> roles) {

        if (userRepository.findByUserName(userModel.getUserName()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        var validRoles = roleRepository.findByRoleNameIn(roles);

        var user = userMapper.toEntity(userModel);
        for (Role role: validRoles ) {
            user.getRoles().add(role);
        }
        updateRolesWithUser(user);
        var savedUser = userRepository.save(user);
        userModel.setId(savedUser.getId());
        return savedUser != null;
    }

    private void updateRolesWithUser(User user) {
        for (Role role: user.getRoles()) {
            role.getUsers().add(user);
        }
    }

    @Transactional
    public boolean createUser(UserModel userModel, String[] roles) {
        return createUser(userModel, Arrays.asList(roles));
    }

    public Optional<UserModel> getUserByUserName(String username) {
        var user = userRepository.findByUserName(username);
        return getOptionalUserModel(user);
    }

    public Optional<UserModel> getUserByUserNameAndPassword(String username, String password) {
        var user = userRepository.findByUserNameAndPassword(username, password);
        return getOptionalUserModel(user);
    }

    private Optional<UserModel> getOptionalUserModel(Optional<User> user) {
        if (user.isPresent()) {
            return Optional.of(userMapper.fromEntity(user.get()));
        }
        return Optional.empty();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = getUserByUserName(username);
        if (user.isEmpty()) { throw new UsernameNotFoundException(username);}
        return new MyUSCUserDetails(user.get());

    }

    public boolean updatePassword(UserModel userModel) {
        Optional<User> user = userRepository.findById(userModel.getId());
        if (user.isEmpty()) { throw new UsernameNotFoundException(userModel.getId().toString());}
        // convert to entity to get the encode password
        var update_user = userMapper.toEntity(userModel);
        var entity = user.get();
        entity.setPassword(update_user.getPassword());
        return userRepository.save(entity) != null;
    }
}
