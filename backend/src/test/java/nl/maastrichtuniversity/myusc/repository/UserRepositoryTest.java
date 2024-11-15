//package nl.maastrichtuniversity.myusc.repository;
//
//import nl.maastrichtuniversity.myusc.entities.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@DataJpaTest
//public class UserRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void testFindById() {
//        User user = new User();
//        user.setUserName("testuser");
//        entityManager.persistAndFlush(user);
//
//        Optional<User> found = userRepository.findById(user.getId());
//        assertTrue(found.isPresent());
//        assertEquals("testuser", found.get().getUserName());
//    }
//}
