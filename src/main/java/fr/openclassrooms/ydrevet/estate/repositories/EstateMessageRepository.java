package fr.openclassrooms.ydrevet.estate.repositories;

import fr.openclassrooms.ydrevet.estate.entities.EstateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateMessageRepository extends JpaRepository<EstateMessage, Long> {
}
