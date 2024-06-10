package fr.openclassrooms.ydrevet.estate.controllers;

import fr.openclassrooms.ydrevet.estate.dto.EstateMessageRequest;
import fr.openclassrooms.ydrevet.estate.dto.MessageResponse;
import fr.openclassrooms.ydrevet.estate.entities.EstateMessage;
import fr.openclassrooms.ydrevet.estate.entities.EstateUser;
import fr.openclassrooms.ydrevet.estate.entities.Rental;
import fr.openclassrooms.ydrevet.estate.exceptions.UnAuthorizedException;
import fr.openclassrooms.ydrevet.estate.services.EstateMessageService;
import fr.openclassrooms.ydrevet.estate.services.EstateUserService;
import fr.openclassrooms.ydrevet.estate.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "Messages")
@SecurityRequirement(name = "jwt")
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final EstateUserService estateUserService;
    private final RentalService rentalService;
    private final EstateMessageService estateMessageService;
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageController(EstateUserService estateUserService, RentalService rentalService, EstateMessageService estateMessageService) {
        this.estateUserService = estateUserService;
        this.rentalService = rentalService;
        this.estateMessageService = estateMessageService;
    }

    @Operation(
            summary = "Envoie un message à une location."
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse sendMessage(@RequestBody EstateMessageRequest messageRequest, Authentication authentication) {
        EstateUser currentUser = this.estateUserService.getByEmail(authentication.getName());
        Rental currentRental = this.rentalService.getRental(messageRequest.rental_id());

        if (!Objects.equals(currentUser.getId(), messageRequest.user_id())) {
            throw new UnAuthorizedException("Tentative d’envoyer un message en tant qu’un autre utilisateur.");
        }

        EstateMessage message = new EstateMessage();
        message.setMessage(messageRequest.message());
        message.setUser(currentUser);
        message.setRental(currentRental);
        this.estateMessageService.sendMessage(message);
        logger.info("Message envoyé : {}", messageRequest.message());
        return new MessageResponse("Message sent");
    }
}
