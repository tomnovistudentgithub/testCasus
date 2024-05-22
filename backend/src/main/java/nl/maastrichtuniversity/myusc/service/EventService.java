package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.model.*;
import nl.maastrichtuniversity.myusc.model.Event;
import nl.maastrichtuniversity.myusc.repository.EventRepository;
import nl.maastrichtuniversity.myusc.repository.LocationRepository;
import nl.maastrichtuniversity.myusc.repository.SportRepository;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SportRepository sportRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    @Autowired
    public EventService(EventRepository eventRepository, SportRepository sportRepository, LocationRepository locationRepository, UserRepository userRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.sportRepository = sportRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.eventMapper = eventMapper;
    }

    public EventDto getEventDto(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event with id " + eventId + " does not exist"));
        return eventMapper.toDto(event);
    }

    public EventDto addEvent(EventDto eventDto) {
        Event event = eventMapper.toEntityForCreate(eventDto);

        List<User> users = eventDto.getParticipantIds().stream()
                .map(id -> userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist")))
                .collect(Collectors.toList());
        event.setUsers(users);

        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }


    public void registerUser(Event event, User user) {
        if (event.getUsers().size() < event.getLocation().getCapacity()) {
            event.getUsers().add(user);
            event.setAvailablePlaces(event.getAvailablePlaces() - 1);
            eventRepository.save(event);
        } else {
            throw new RuntimeException("The event is full.");
        }
    }

    public void deregisterUser(Event event, User user) {
        event.getUsers().remove(user);
        event.setAvailablePlaces(event.getAvailablePlaces() + 1);
        eventRepository.save(event);

    }


}

