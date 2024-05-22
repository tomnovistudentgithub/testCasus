package nl.maastrichtuniversity.myusc.controller;

import nl.maastrichtuniversity.myusc.model.Event;
import nl.maastrichtuniversity.myusc.model.EventDto;
import nl.maastrichtuniversity.myusc.model.EventMapper;
import nl.maastrichtuniversity.myusc.model.User;
import nl.maastrichtuniversity.myusc.repository.EventRepository;
import nl.maastrichtuniversity.myusc.repository.LocationRepository;
import nl.maastrichtuniversity.myusc.repository.SportRepository;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import nl.maastrichtuniversity.myusc.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nl.maastrichtuniversity.myusc.model.Sport;
import nl.maastrichtuniversity.myusc.model.Location;

import java.util.List;

import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final SportRepository sportRepository;
    private final LocationRepository locationRepository;
    private final EventMapper eventMapper;


    @Autowired
    public EventController(EventService eventService, EventRepository eventRepository, UserRepository userRepository, SportRepository sportRepository, LocationRepository locationRepository, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.sportRepository = sportRepository;
        this.locationRepository = locationRepository;
        this.eventMapper = eventMapper;
    }

    @GetMapping("/{eventId}/details")
    public ResponseEntity<?> getEventDetails(@PathVariable Long eventId) {
        try {
            EventDto response = eventService.getEventDto(eventId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEvents() {
        try {
            List<Event> events = eventRepository.findAll();
            List<EventDto> eventDtos = events.stream()
                    .map(eventMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(eventDtos);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/addEvent", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addEvent(@RequestBody EventDto eventDto) {
        try {
            EventDto savedEventDto = eventService.addEvent(eventDto);
            return ResponseEntity.ok(savedEventDto);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{eventId}/delete")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId) {
        try {
            eventService.deleteEvent(eventId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{eventId}/register/{userId}")
    public ResponseEntity<?> registerUser(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        try {
            Event event = eventRepository.findEventById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event with id " + eventId + " does not exist"));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with id " + userId + " does not exist"));
            eventService.registerUser(event, user);
            EventDto updatedEvent = eventService.getEventDto(eventId);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{eventId}/deregister/{userId}")
    public ResponseEntity<?> deregisterUser(@PathVariable("eventId") Long eventId, @PathVariable("userId") Long userId) {
        try {
            Event event = eventRepository.findEventById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event with id " + eventId + " does not exist"));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with id " + userId + " does not exist"));
            eventService.deregisterUser(event, user);
            EventDto updatedEvent = eventService.getEventDto(eventId);
            return ResponseEntity.ok(updatedEvent);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}





