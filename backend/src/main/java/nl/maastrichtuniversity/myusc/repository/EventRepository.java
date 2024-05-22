package nl.maastrichtuniversity.myusc.repository;

import nl.maastrichtuniversity.myusc.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
Optional<Event> findByName(String name);
Optional<Event> findEventById(Long id);



}
