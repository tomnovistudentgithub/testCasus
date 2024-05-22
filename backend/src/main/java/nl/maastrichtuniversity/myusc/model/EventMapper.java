package nl.maastrichtuniversity.myusc.model;

import nl.maastrichtuniversity.myusc.repository.LocationRepository;
import nl.maastrichtuniversity.myusc.repository.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {



    private final SportRepository sportRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public EventMapper(SportRepository sportRepository, LocationRepository locationRepository) {
        this.sportRepository = sportRepository;
        this.locationRepository = locationRepository;
    }

    public EventDto toDto(Event event) {
        EventDto dto = new EventDto();

        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setStartDate(event.getStartDate());
        dto.setEndDate(event.getEndDate());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setTargetAudience(event.getTargetAudience());
        dto.setSport(event.getSport());
        dto.setLocation(event.getLocation());


        dto.setSportName(event.getSport().getName());
        dto.setSportId(event.getSport().getId());
        dto.setLocationName(event.getLocation().getName());
        dto.setLocationId(event.getLocation().getId());
        dto.setAvailablePlaces(event.getAvailablePlaces());

        List<Long> participantIds = event.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        dto.setParticipantIds(participantIds);

        return dto;
    }

    public Event toEntityForCreate(EventDto dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setTargetAudience(dto.getTargetAudience());

        Sport sport = sportRepository.findById(dto.getSport().getId())
                .orElseThrow(() -> new RuntimeException("Sport with id " + dto.getSport().getId() + " does not exist"));
        event.setSport(sport);

        Location location = locationRepository.findById(dto.getLocation().getId())
                .orElseThrow(() -> new RuntimeException("Location with id " + dto.getLocation().getId() + " does not exist"));
        event.setLocation(location);

        return event;
    }

    public Event toEntityForUpdate(EventDto dto) {
        Event event = new Event();
        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setTargetAudience(dto.getTargetAudience());

        return event;

    }
}