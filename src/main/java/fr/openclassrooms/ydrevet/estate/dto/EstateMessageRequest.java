package fr.openclassrooms.ydrevet.estate.dto;

public record EstateMessageRequest(Long user_id, Long rental_id, String message) {
}
