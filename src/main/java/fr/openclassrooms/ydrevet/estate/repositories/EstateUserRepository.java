package fr.openclassrooms.ydrevet.estate.repositories;

import fr.openclassrooms.ydrevet.estate.entities.EstateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateUserRepository extends JpaRepository<EstateUser, Long> {
    EstateUser findByEmail(String email);
}
