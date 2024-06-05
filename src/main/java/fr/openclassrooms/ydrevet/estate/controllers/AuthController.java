package fr.openclassrooms.ydrevet.estate.controllers;

import fr.openclassrooms.ydrevet.estate.dto.EstateUserResponse;
import fr.openclassrooms.ydrevet.estate.dto.JwtResponse;
import fr.openclassrooms.ydrevet.estate.dto.LoginRequest;
import fr.openclassrooms.ydrevet.estate.dto.RegistrationRequest;
import fr.openclassrooms.ydrevet.estate.entities.EstateUser;
import fr.openclassrooms.ydrevet.estate.services.EstateUserService;
import fr.openclassrooms.ydrevet.estate.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EstateUserService estateUserService;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, EstateUserService estateUserService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.estateUserService = estateUserService;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());

        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        if (authenticationResponse.isAuthenticated()) {
            String jwtToken = jwtService.generateToken(authenticationResponse);
            return new JwtResponse(jwtToken);
        } else {
            throw new BadCredentialsException("error");
        }
    }

    @GetMapping("/me")
    public EstateUserResponse me(Authentication authentication) {
        EstateUser estateUser = estateUserService.getByEmail(authentication.getName());
        EstateUserResponse response = EstateUserResponse.fromEstateUser(estateUser);
        return response;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponse register(@RequestBody RegistrationRequest registrationRequest) {
        this.estateUserService.register(registrationRequest);
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(registrationRequest.email(), registrationRequest.password());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        String jwtToken = jwtService.generateToken(authenticationResponse);
        return new JwtResponse(jwtToken);
    }
}
