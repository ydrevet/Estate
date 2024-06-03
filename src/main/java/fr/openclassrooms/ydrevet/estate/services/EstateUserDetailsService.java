package fr.openclassrooms.ydrevet.estate.services;

import fr.openclassrooms.ydrevet.estate.entities.EstateUser;
import fr.openclassrooms.ydrevet.estate.repositories.EstateUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EstateUserDetailsService implements UserDetailsService {
    private final EstateUserRepository userRepository;

    public EstateUserDetailsService(EstateUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EstateUser estateUser = userRepository.findByEmail(username);
        if (estateUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(estateUser.getEmail(), estateUser.getPassword(), new ArrayList<>());
    }
}
