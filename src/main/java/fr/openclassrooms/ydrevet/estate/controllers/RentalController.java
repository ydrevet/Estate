package fr.openclassrooms.ydrevet.estate.controllers;

import fr.openclassrooms.ydrevet.estate.dto.MessageResponse;
import fr.openclassrooms.ydrevet.estate.dto.RentalResponse;
import fr.openclassrooms.ydrevet.estate.dto.RentalsResponse;
import fr.openclassrooms.ydrevet.estate.entities.EstateUser;
import fr.openclassrooms.ydrevet.estate.entities.Rental;
import fr.openclassrooms.ydrevet.estate.exceptions.UnAuthorizedException;
import fr.openclassrooms.ydrevet.estate.services.EstateUserService;
import fr.openclassrooms.ydrevet.estate.services.RentalService;
import fr.openclassrooms.ydrevet.estate.services.StorageBackend;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Rentals")
@SecurityRequirement(name = "jwt")
@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;
    private final StorageBackend storageBackend;
    private final EstateUserService estateUserService;

    public RentalController(RentalService rentalService, StorageBackend storageBackend, EstateUserService estateUserService) {
        this.rentalService = rentalService;
        this.storageBackend = storageBackend;
        this.estateUserService = estateUserService;
    }

    @Operation(
            summary = "Liste des locations.",
            description = "Retourne une liste de l’ensemble des locations présentes en base de données."
    )
    @GetMapping
    public RentalsResponse listRentals() {
        List<Rental> rentals = this.rentalService.getAll();
        List<RentalResponse> response = rentals.stream().map(RentalResponse::fromRental).collect(Collectors.toList());
        return new RentalsResponse(response);
    }

    @Operation(
            summary = "Détails d’une location.",
            description = "Retourne les détails d’une location en particulier."
    )
    @GetMapping("/{id}")
    public RentalResponse getRental(@PathVariable Long id) {
        Rental rental = this.rentalService.getRental(id);
        return RentalResponse.fromRental(rental);
    }

    @Operation(
            summary = "Création d’une location.",
            description = "Création d’une nouvelle location, incluant sa photographie."
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createRental(@RequestParam String name, @RequestParam String description, @RequestParam Long surface, @RequestParam Long price, MultipartFile picture, Authentication authentication) {
        this.storageBackend.store(picture);
        Rental newRental = new Rental();
        newRental.setName(name);
        newRental.setDescription(description);
        newRental.setSurface(surface);
        newRental.setPrice(price);
        String pictureUrl = MvcUriComponentsBuilder.fromMethodName(RentalController.class, "servePicture", picture.getOriginalFilename()).build().toUri().toString();
        newRental.setPicture(pictureUrl);
        String username = authentication.getName();
        EstateUser owner = this.estateUserService.getByEmail(username);
        newRental.setOwner(owner);
        rentalService.create(newRental);

        return new MessageResponse("Rental created!");
    }

    @Operation(
            summary = "Édition d’une location",
            description = "Édition d’une location déjà existante."
    )
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse updateRental(@PathVariable Long id, @RequestParam String name, @RequestParam String description, @RequestParam Long surface, @RequestParam Long price, Authentication authentication) {
        Rental rentalToUpdate = rentalService.getRental(id);
        EstateUser currentUser = this.estateUserService.getByEmail(authentication.getName());
        if (rentalToUpdate.getOwner() != currentUser) {
            throw new UnAuthorizedException("Vous n’êtes pas le propriétaire de cette location");
        }
        rentalToUpdate.setName(name);
        rentalToUpdate.setDescription(description);
        rentalToUpdate.setSurface(surface);
        rentalToUpdate.setPrice(price);
        this.rentalService.update(rentalToUpdate);

        return new MessageResponse("Rental updated!");
    }

    @Hidden
    @GetMapping("/pictures/{filename:.+}")
    public ResponseEntity<Resource> servePicture(@PathVariable String filename) {
        Resource resource = this.storageBackend.load(filename);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
