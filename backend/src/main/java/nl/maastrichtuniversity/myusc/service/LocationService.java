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
        Location location = new Location();
        location.setName(locationDto.getName());
        location.setBuildingSection(locationDto.getBuildingSection());
        location.setCapacity(locationDto.getCapacity());
        locationRepository.save(location);
    }
}
