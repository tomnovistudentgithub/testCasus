package nl.maastrichtuniversity.myusc.service;

import nl.maastrichtuniversity.myusc.dtos.EventDto;
import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.dtos.EventDTOMapper;
import nl.maastrichtuniversity.myusc.model.Event;
import nl.maastrichtuniversity.myusc.model.Sport;
import nl.maastrichtuniversity.myusc.repository.EventRepository;
import nl.maastrichtuniversity.myusc.repository.LocationRepository;
import nl.maastrichtuniversity.myusc.repository.SportRepository;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SportRepository sportRepository;
    private final UserRepository userRepository;
    private final EventDTOMapper eventMapper;
    private final MembershipService membershipService;

    @Autowired
    public EventService(EventRepository eventRepository, SportRepository sportRepository, LocationRepository locationRepository, UserRepository userRepository, EventDTOMapper eventMapper, MembershipService membershipService) {
        this.eventRepository = eventRepository;
        this.sportRepository = sportRepository;
        this.userRepository = userRepository;
        this.eventMapper = eventMapper;
        this.membershipService = membershipService;
    }

    public EventDto getEventDto(Long eventId) {
        Event event = findEventById(eventId);
        return eventMapper.toDto(event);
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event with id " + eventId + " does not exist"));
    }


    public EventDto createEvent(EventDto eventDto) {
        Event event = eventMapper.toEntityForCreate(eventDto);
        validateEvent(event);
        setEventDetails(event, eventDto);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toDto(savedEvent);
    }

    private void validateEvent(Event event) {
        Long sportId = event.getSport().getId();
        LocalDate startDate = event.getStartDate();
        LocalTime startTime = event.getStartTime();
        if (eventRepository.findBySportIdAndStartDateAndStartTime(sportId, startDate, startTime).isPresent()) {
            throw new RuntimeException("An event with the same sport already exists at the same date and time.");
        }
    }

    private void setEventDetails(Event event, EventDto eventDto) {
        Sport sport = sportRepository.findById(event.getSport().getId())
                .orElseThrow(() -> new RuntimeException("Sport with id " + event.getSport().getId() + " does not exist"));
        event.setSport(sport);

        List<User> users = eventDto.getParticipants().stream()
                .map(userDTO -> userRepository.findById(userDTO.getId()).orElseThrow(() -> new RuntimeException("User with id " + userDTO.getId() + " does not exist")))
                .collect(Collectors.toList());
        event.setUsers(users);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }


    private void validateUserRegistration(Event event, User user) {
        if (event.getUsers().contains(user)) {
            throw new RuntimeException("User is already registered for this event.");
        }

        if (!hasActiveMembershipForSport(user, event.getSport())) {
            throw new RuntimeException("User does not have an active membership for the sport.");
        }

        if (event.getUsers().size() >= event.getLocation().getCapacity()) {
            throw new RuntimeException("The event is full.");
        }
    }

    public void addUserToEvent(Event event, User user) {
        validateUserRegistration(event, user);
        event.getUsers().add(user);
        event.setAvailablePlaces(event.getAvailablePlaces() - 1);
        eventRepository.save(event);
    }

    private boolean hasActiveMembershipForSport(User user, Sport sport) {
        return membershipService.hasActiveMembership(user.getId(), sport.getSportType());
    }

    public void deregisterUser(Event event, User user) {
        event.getUsers().remove(user);
        event.setAvailablePlaces(event.getAvailablePlaces() + 1);
        eventRepository.save(event);

    }


}

