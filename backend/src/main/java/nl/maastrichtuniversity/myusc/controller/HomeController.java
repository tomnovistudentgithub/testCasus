package nl.maastrichtuniversity.myusc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the landingpage of the MyUsc backend. Please note that you need to register and login to continue. Consult our API documentation for more information on the available endpoints.";
    }

}
