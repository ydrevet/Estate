package fr.openclassrooms.ydrevet.estate.controllers;

import fr.openclassrooms.ydrevet.estate.dto.RentalResponse;
import fr.openclassrooms.ydrevet.estate.dto.RentalsResponse;
import fr.openclassrooms.ydrevet.estate.entities.Rental;
import fr.openclassrooms.ydrevet.estate.services.RentalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public RentalsResponse listRentals() {
        List<Rental> rentals = this.rentalService.getAll();
        List<RentalResponse> response = rentals.stream().map(RentalResponse::fromRental).collect(Collectors.toList());
        return new RentalsResponse(response);
    }
}
