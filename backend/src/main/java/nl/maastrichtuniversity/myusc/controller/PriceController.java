package nl.maastrichtuniversity.myusc.controller;

import nl.maastrichtuniversity.myusc.model.PriceRequestDto;
import nl.maastrichtuniversity.myusc.entities.User;
import nl.maastrichtuniversity.myusc.repository.UserRepository;
import nl.maastrichtuniversity.myusc.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceService priceService;
    private final UserRepository userRepository;


    @Autowired
    public PriceController(PriceService priceService, UserRepository userRepository) {
        this.priceService = priceService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Double> setPrice(@RequestBody PriceRequestDto priceRequestDto) {
        try {
            User user = userRepository.findById(priceRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            double price = priceService.setPrice(user, priceRequestDto.getMembershipType(), priceRequestDto.getEnrollmentMonth());
            return ResponseEntity.ok(price);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
