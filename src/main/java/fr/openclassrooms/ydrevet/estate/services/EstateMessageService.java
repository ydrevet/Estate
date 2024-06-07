package fr.openclassrooms.ydrevet.estate.services;

import fr.openclassrooms.ydrevet.estate.entities.EstateMessage;
import fr.openclassrooms.ydrevet.estate.repositories.EstateMessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EstateMessageService {
    private final EstateMessageRepository estateMessageRepository;

    public EstateMessageService(EstateMessageRepository estateMessageRepository) {
        this.estateMessageRepository = estateMessageRepository;
    }

    public void sendMessage(EstateMessage estateMessage) {
        estateMessage.setCreatedAt(Instant.now());
        estateMessage.setUpdatedAt(Instant.now());
        estateMessageRepository.save(estateMessage);
    }
}
