package fr.openclassrooms.ydrevet.estate.controllers;

import fr.openclassrooms.ydrevet.estate.dto.EstateUserResponse;
import fr.openclassrooms.ydrevet.estate.services.EstateUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users")
@SecurityRequirement(name = "jwt")
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final EstateUserService estateUserService;

    public UserController(EstateUserService estateUserService) {
        this.estateUserService = estateUserService;
    }

    @Operation(
            summary = "Détails d’un utilisateur",
            description = "Obtenir les détails d’un utilisateur."
    )
    @GetMapping("/{id}")
    public EstateUserResponse getUserById(@PathVariable Long id) {
        return EstateUserResponse.fromEstateUser(this.estateUserService.getById(id));
    }
}
