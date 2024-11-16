package nl.maastrichtuniversity.myusc.dtos;

import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.model.Event;
import nl.maastrichtuniversity.myusc.model.Location;
import nl.maastrichtuniversity.myusc.model.Sport;
import nl.maastrichtuniversity.myusc.repository.LocationRepository;
import nl.maastrichtuniversity.myusc.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventDTOMapper {

    private final SportRepository sportRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public EventDTOMapper(SportRepository sportRepository, LocationRepository locationRepository) {
        this.sportRepository = sportRepository;
        this.locationRepository = locationRepository;
    }

    public EventDto toDto(Event event) {
        EventDto dto = new EventDto();

        dto.setId(event.getId());
        dto.setStartDate(event.getStartDate());
        dto.setEndDate(event.getEndDate());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setSport(event.getSport());
        dto.setLocation(event.getLocation());
        dto.setAvailablePlaces(event.getAvailablePlaces());

        dto.setParticipants(event.getUsers().stream()
                .map(this::toUserDto)
                .collect(Collectors.toList()));


        return dto;
    }

    private UserDTO toUserDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        return dto;
    }

    public Event toEntityForCreate(EventDto dto) {
        Event event = new Event();
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());

        Sport sport = sportRepository.findById(dto.getSport().getId())
                .orElseThrow(() -> new RuntimeException("Sport with id " + dto.getSport().getId() + " does not exist"));
        event.setSport(sport);

        Location location = locationRepository.findById(dto.getLocation().getId())
                .orElseThrow(() -> new RuntimeException("Location with id " + dto.getLocation().getId() + " does not exist"));
        event.setLocation(location);

        return event;
    }




}