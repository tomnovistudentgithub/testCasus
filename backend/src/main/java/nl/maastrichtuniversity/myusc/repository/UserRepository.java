package nl.maastrichtuniversity.myusc.repository;

import nl.maastrichtuniversity.myusc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findById(Long id);
    User findByFirstName(String name);
    User findByLastName(String name);
    User findByEmail(String email);

    void deleteById(Long id);

    Optional<User> findByUserName(String username);
    Optional<User> findByUserNameAndPassword(String username, String password);

}
