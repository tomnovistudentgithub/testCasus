package nl.maastrichtuniversity.myusc.repository;

import nl.maastrichtuniversity.myusc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findById(Long id);
    User findByEmail(String email);
    Optional<User> findByUserName(String username);
    void deleteById(Long id);
    Optional<User> findByUserNameAndPassword(String username, String password);

}
