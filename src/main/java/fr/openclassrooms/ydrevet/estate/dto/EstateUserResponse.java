package fr.openclassrooms.ydrevet.estate.dto;

import fr.openclassrooms.ydrevet.estate.entities.EstateUser;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public record EstateUserResponse(Long id, String name, String email, String created_at, String updated_at) {
    public static EstateUserResponse fromEstateUser(EstateUser estateUser) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.from(ZoneOffset.UTC));
        String createdAt = formatter.format(estateUser.getCreatedAt());
        String updatedAt = formatter.format(estateUser.getUpdatedAt());
        return new EstateUserResponse(
                estateUser.getId(),
                estateUser.getName(),
                estateUser.getEmail(),
                createdAt,
                updatedAt
        );
    }
}
