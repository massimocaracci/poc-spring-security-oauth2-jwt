package com.pantasoft.jwtauthserver.services;

import com.pantasoft.jwtauthserver.repositories.UserRepository;
import com.pantasoft.jwtauthserver.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void save(UserEntity user) {
        user.setPassword(getPasswordEncoder().encode(user.getPassword()));
        repo.save(user);
    }

}
