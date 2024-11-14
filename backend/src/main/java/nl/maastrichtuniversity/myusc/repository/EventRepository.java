package nl.maastrichtuniversity.myusc.repository;

import nl.maastrichtuniversity.myusc.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
Optional<Event> findEventById(Long id);
Optional<Event> findBySportIdAndStartDateAndStartTime(Long sportId, LocalDate startDate, LocalTime startTime);




}
