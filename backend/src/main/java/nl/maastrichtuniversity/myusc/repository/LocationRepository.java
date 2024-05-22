package nl.maastrichtuniversity.myusc.repository;

import nl.maastrichtuniversity.myusc.model.Event;
import nl.maastrichtuniversity.myusc.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByName(String name);
    Location findByBuildingSection(String buildingSection);
    Location findByCapacity(int capacity);
    Location findByEvents(List<Event> events);



}
