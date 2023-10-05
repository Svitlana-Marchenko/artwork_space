package com.system.artworkspace.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByFirstName(String firstname);
    Optional<User> findBySecondName(String secondname);

}
