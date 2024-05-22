package nl.maastrichtuniversity.myusc.repository;

import nl.maastrichtuniversity.myusc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findById(Long id);
    User findByFirstName(String name);
    User findByLastName(String name);
    User findByEmail(String email);

    void deleteById(Long id);

}
