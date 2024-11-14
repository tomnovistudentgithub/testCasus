package nl.maastrichtuniversity.myusc.service;


import nl.maastrichtuniversity.myusc.model.Location;
import nl.maastrichtuniversity.myusc.dtos.LocationDto;
import nl.maastrichtuniversity.myusc.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {


    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void addLocation(LocationDto locationDto) {

        locationRepository.findByNameIgnoreCase(locationDto.getName())
                .ifPresent(s -> {
                    throw new RuntimeException("Location with name " + locationDto.getName() + " already exists");
                });
        Location location = new Location();
        location.setName(locationDto.getName());
        location.setCapacity(locationDto.getCapacity());
        locationRepository.save(location);
    }

    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new RuntimeException("Location with id " + id + " does not exist");
        }
        locationRepository.deleteById(id);
    }
}

