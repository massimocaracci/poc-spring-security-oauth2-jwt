package com.pantasoft.jwtauthserver.config;

import com.pantasoft.jwtauthserver.repositories.UserRepository;
import com.pantasoft.jwtauthserver.repositories.entities.RoleEntity;
import com.pantasoft.jwtauthserver.repositories.entities.UserEntity;
import com.pantasoft.jwtauthserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Password grants are switched on by injecting an AuthenticationManager.
     * Here, we setup the builder so that the userDetailsService is the one we coded.
     *
     * @param builder
     * @param repository
     * @throws Exception
     */
    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repository, UserService service) throws Exception {
        //Setup a default user if db is empty
        if (repository.count() == 0) {
            service.save(new UserEntity("pantasoft", "password", Arrays.asList(new RoleEntity("ROLE_USER"), new RoleEntity("ROLE_ADMIN"))));
            service.save(new UserEntity("massimo", "password", Arrays.asList(new RoleEntity("ROLE_USER"))));
        }

        builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
    }

    /**
     * We return an istance of our CustomUserDetails.
     *
     * @param repository
     * @return
     */
    private UserDetailsService userDetailsService(final UserRepository repository) {
        return username -> new CustomUserDetails(repository.findByUsername(username));
    }
}
