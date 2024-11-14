package nl.maastrichtuniversity.myusc.repository;

import nl.maastrichtuniversity.myusc.model.Event;
import nl.maastrichtuniversity.myusc.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByNameIgnoreCase(String name);


}
