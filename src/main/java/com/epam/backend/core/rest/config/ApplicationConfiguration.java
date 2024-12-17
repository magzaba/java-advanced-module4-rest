package com.epam.backend.core.rest.config;

import com.epam.backend.core.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CommandLineRunner initializeDB(@Autowired UserService service) {
        return args -> service.initializeDatabase();
    }
}
