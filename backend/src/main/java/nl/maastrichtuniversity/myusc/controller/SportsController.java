package nl.maastrichtuniversity.myusc.controller;


import nl.maastrichtuniversity.myusc.dtos.SportDto;
import nl.maastrichtuniversity.myusc.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sports")
public class SportsController {

    SportService sportsService;

    @Autowired
    public SportsController(SportService sportsService) {
        this.sportsService = sportsService;
    }

    @PostMapping("/addSport")
    public ResponseEntity<?> addSports(@RequestBody SportDto sportDto) {
        try {

            sportsService.addSport(sportDto);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteSport/{id}")
    public ResponseEntity<?> deleteSport(@PathVariable Long id) {
        try {
            sportsService.deleteSport(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
