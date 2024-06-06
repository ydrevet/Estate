package fr.openclassrooms.ydrevet.estate.dto;

import fr.openclassrooms.ydrevet.estate.entities.Rental;
import fr.openclassrooms.ydrevet.estate.services.DateFormatterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record RentalResponse(Long id, String name, Long surface, Long price, List<String> picture, String description,
                             String created_at, String updated_ad) {
    public static RentalResponse fromRental(Rental rental) {
        String createdAt = DateFormatterService.formatter.format(rental.getCreatedAt());
        String updatedAt = DateFormatterService.formatter.format(rental.getUpdatedAt());
        return new RentalResponse(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                new ArrayList<>(Collections.singleton(rental.getPicture())),
                rental.getDescription(),
                createdAt,
                updatedAt
        );
    }

}
