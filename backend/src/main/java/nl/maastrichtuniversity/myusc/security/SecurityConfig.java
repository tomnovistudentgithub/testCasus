package nl.maastrichtuniversity.myusc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
                .httpBasic(hp -> hp.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/events/{eventId}/register/{userId}").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/events/{eventId}/deregister/{userId}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/events/**").authenticated()
                        .requestMatchers(HttpMethod.POST,  "/api/events/**").hasAnyRole("PLANNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE,  "/api/events/**").hasRole("PLANNER")
                        .requestMatchers("/api/sports/**").hasRole("PLANNER")
                        .requestMatchers("/api/locations/**").hasRole("PLANNER")
                        .requestMatchers("/api/memberships/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        ;
        return  http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService myUSCUserDetails) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(myUSCUserDetails).passwordEncoder(encoder);
        return builder.build();
    }
}

