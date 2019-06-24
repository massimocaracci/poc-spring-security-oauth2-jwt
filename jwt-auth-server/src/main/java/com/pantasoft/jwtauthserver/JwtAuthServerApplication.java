package com.pantasoft.jwtauthserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtAuthServerApplication {

    static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthServerApplication.class);

    public static void main(String[] args) {

        LOGGER.debug("JwtAuthServerApplication.main");
        SpringApplication.run(JwtAuthServerApplication.class, args);
    }

}
