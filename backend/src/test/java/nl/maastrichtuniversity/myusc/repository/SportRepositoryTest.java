//package nl.maastrichtuniversity.myusc.repository;
//
//import nl.maastrichtuniversity.myusc.model.Sport;
//import nl.maastrichtuniversity.myusc.model.SportType;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//@DataJpaTest
//public class SportRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private SportRepository sportRepository;
//
//    @Test
//    public void testFindById() {
//        Sport sport = new Sport();
//        sport.setName("Basketball");
//        sport.setDescription("A team sport");
//        sport.setSportType(SportType.SPORTS);
//        entityManager.persistAndFlush(sport);
//
//        Optional<Sport> found = sportRepository.findById(sport.getId());
//        assertTrue(found.isPresent());
//        assertEquals("Basketball", found.get().getName());
//    }
//
//    @Test
//    public void testFindByNameIgnoreCase() {
//        Sport sport = new Sport();
//        sport.setName("Basketball");
//        sport.setDescription("A team sport");
//        sport.setSportType(SportType.SPORTS);
//        entityManager.persistAndFlush(sport);
//
//        Optional<Sport> found = sportRepository.findByNameIgnoreCase("basketball");
//        assertTrue(found.isPresent());
//        assertEquals("Basketball", found.get().getName());
//    }
//}
//
