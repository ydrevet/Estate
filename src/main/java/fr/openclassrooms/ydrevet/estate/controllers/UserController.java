package fr.openclassrooms.ydrevet.estate.controllers;

import fr.openclassrooms.ydrevet.estate.dto.EstateUserResponse;
import fr.openclassrooms.ydrevet.estate.entities.EstateUser;
import fr.openclassrooms.ydrevet.estate.services.EstateUserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final EstateUserService estateUserService;

    public UserController(EstateUserService estateUserService) {
        this.estateUserService = estateUserService;
    }

    @GetMapping("/api/auth/me")
    public EstateUserResponse me(Authentication authentication) {
        EstateUser estateUser = estateUserService.getByEmail(authentication.getName());
        EstateUserResponse response = EstateUserResponse.fromEstateUser(estateUser);
        return response;
    }
}
