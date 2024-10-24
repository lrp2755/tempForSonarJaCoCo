package com.masai.config;

import com.masai.factories.AdminFactory;
import com.masai.factories.DefaultAdminFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public AdminFactory adminFactory() {
        return new DefaultAdminFactory(); // Use the concrete implementation of AdminFactory
    }
}
