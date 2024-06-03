package fr.openclassrooms.ydrevet.estate.services;

import fr.openclassrooms.ydrevet.estate.repositories.EstateUserRepository;
import org.springframework.stereotype.Service;

@Service
public class EstateUserService {
    private final EstateUserRepository estateUserRepository;
    public EstateUserService(EstateUserRepository estateUserRepository) {
        this.estateUserRepository = estateUserRepository;
    }


}
