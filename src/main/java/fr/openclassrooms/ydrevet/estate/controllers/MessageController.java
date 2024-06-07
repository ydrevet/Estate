package fr.openclassrooms.ydrevet.estate.controllers;

import fr.openclassrooms.ydrevet.estate.dto.EstateMessageRequest;
import fr.openclassrooms.ydrevet.estate.dto.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse sendMessage(@RequestBody EstateMessageRequest messageRequest) {
        logger.info("Message envoyé : {}", messageRequest.message());

        return new MessageResponse("Message sent");
    }
}
