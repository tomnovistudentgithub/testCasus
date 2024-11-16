package nl.maastrichtuniversity.myusc.controller;

import nl.maastrichtuniversity.myusc.dtos.UserLoginRequestDTO;
import nl.maastrichtuniversity.myusc.security.JwtService;
import nl.maastrichtuniversity.myusc.security.MyUSCUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager man, JwtService service) {
        this.authManager = man;
        this.jwtService = service;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO
    ) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getUserName(), userLoginRequestDTO.getPassword());

        try {
            Authentication auth = authManager.authenticate(usernamePasswordAuthenticationToken);

            var userDetails = (MyUSCUserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated");
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
