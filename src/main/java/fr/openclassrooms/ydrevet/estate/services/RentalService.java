package fr.openclassrooms.ydrevet.estate.services;

import fr.openclassrooms.ydrevet.estate.entities.Rental;
import fr.openclassrooms.ydrevet.estate.exceptions.RentalNotFoundException;
import fr.openclassrooms.ydrevet.estate.repositories.RentalRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getAll() {
        return this.rentalRepository.findAll();
    }

    public Rental getRental(Long id) {
        return this.rentalRepository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
    }

    public void create(Rental newRental) {
        newRental.setCreatedAt(Instant.now());
        newRental.setUpdatedAt(Instant.now());
        this.rentalRepository.save(newRental);
    }
}
