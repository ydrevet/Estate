package fr.openclassrooms.ydrevet.estate.controllers;

import fr.openclassrooms.ydrevet.estate.dto.EstateUserResponse;
import fr.openclassrooms.ydrevet.estate.dto.JwtResponse;
import fr.openclassrooms.ydrevet.estate.dto.LoginRequest;
import fr.openclassrooms.ydrevet.estate.dto.RegistrationRequest;
import fr.openclassrooms.ydrevet.estate.entities.EstateUser;
import fr.openclassrooms.ydrevet.estate.exceptions.UserAlreadyRegisteredException;
import fr.openclassrooms.ydrevet.estate.services.EstateUserService;
import fr.openclassrooms.ydrevet.estate.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth")
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

    @Operation(
            summary = "Connexion d’un utilisateur",
            description = "Connexion d’un utilisateur. Obtention d’un token JWT en échange d’un email et d’un mot de passe."
    )
    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        String jwtToken = jwtService.generateToken(authenticationResponse);
        return new JwtResponse(jwtToken);
    }

    @Operation(
            summary = "Qui suis-je ?",
            description = "Renvoie une description de l’utilisateur connecté.",
            security = @SecurityRequirement(name = "jwt")
    )
    @GetMapping("/me")
    public EstateUserResponse me(Authentication authentication) {
        EstateUser estateUser = estateUserService.getByEmail(authentication.getName());
        EstateUserResponse response = EstateUserResponse.fromEstateUser(estateUser);
        return response;
    }

    @Operation(
            summary = "Inscription d’un nouvel utilisateur.",
            description = "Enregistrement d’un nouvel utilisateur. Retourne un token JWT en cas de succès."
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtResponse register(@RequestBody RegistrationRequest registrationRequest) {
        if (this.estateUserService.isAlreadyRegisteres(registrationRequest.email())) {
            throw new UserAlreadyRegisteredException(registrationRequest.email());
        }

        this.estateUserService.register(registrationRequest);
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(registrationRequest.email(), registrationRequest.password());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        String jwtToken = jwtService.generateToken(authenticationResponse);
        return new JwtResponse(jwtToken);
    }
}
