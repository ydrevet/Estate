package fr.openclassrooms.ydrevet.estate.services;

import fr.openclassrooms.ydrevet.estate.dto.RegistrationRequest;
import fr.openclassrooms.ydrevet.estate.entities.EstateUser;
import fr.openclassrooms.ydrevet.estate.exceptions.UserNotFoundException;
import fr.openclassrooms.ydrevet.estate.repositories.EstateUserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EstateUserService {
    private final EstateUserRepository estateUserRepository;
    private final PasswordEncoder passwordEncoder;

    public EstateUserService(EstateUserRepository estateUserRepository, PasswordEncoder passwordEncoder) {
        this.estateUserRepository = estateUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public EstateUser getByEmail(String email) {
        return estateUserRepository.findByEmail(email);
    }

    public EstateUser register(RegistrationRequest registrationRequest) {
        Instant now = Instant.now();
        String encodedPassword = this.passwordEncoder.encode(registrationRequest.password());
        EstateUser estateUser = new EstateUser();
        estateUser.setName(registrationRequest.name());
        estateUser.setEmail(registrationRequest.email());
        estateUser.setPassword(encodedPassword);
        estateUser.setCreatedAt(now);
        estateUser.setUpdatedAt(now);
        return estateUserRepository.save(estateUser);
    }

    public EstateUser getById(long id) {
        return this.estateUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
