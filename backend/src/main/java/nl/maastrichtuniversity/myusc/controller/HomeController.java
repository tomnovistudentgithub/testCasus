package nl.maastrichtuniversity.myusc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the home page of MyUsc! Please login or register to continue.";
    }

}
