package nl.maastrichtuniversity.myusc.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtRequestFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = extractJwtFromRequest(request);
        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            processTokenAndSetAuthentication(jwt, request);
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private void processTokenAndSetAuthentication(String jwt, HttpServletRequest request) {
        String username = jwtService.extractUsername(jwt);
        if (username != null && jwtService.validateToken(jwt)) {
            List<GrantedAuthority> roles = jwtService.extractSimpleGrantedAuthorities(jwt);
            setAuthentication(username, roles, request, jwt);
        }
    }

    private void setAuthentication(String username, List<GrantedAuthority> roles,
                                   HttpServletRequest request, String jwt) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, roles);

        MyUSCUserDetails userDetails = new MyUSCUserDetails(username, jwtService.extractRoles(jwt));
        authenticationToken.setDetails(userDetails);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }


}
