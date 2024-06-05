package fr.openclassrooms.ydrevet.estate.controllers;

import fr.openclassrooms.ydrevet.estate.dto.EstateUserResponse;
import fr.openclassrooms.ydrevet.estate.services.EstateUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final EstateUserService estateUserService;

    public UserController(EstateUserService estateUserService) {
        this.estateUserService = estateUserService;
    }

    @GetMapping("/{id}")
    public EstateUserResponse getUserById(@PathVariable Long id) {
        return EstateUserResponse.fromEstateUser(this.estateUserService.getById(id));
    }
}
