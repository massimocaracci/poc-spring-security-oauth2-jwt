package com.pantasoft.jwtauthserver.repositories;


import com.pantasoft.jwtauthserver.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User repository for CRUD operations.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
