package com.onefactor.config;

import com.onefactor.service.LocalityService;
import com.onefactor.service.LocalityServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 22:11
 */
@Configuration
public class ServiceBeansConfig {
    @Bean
    LocalityService localityService() {
        return new LocalityServiceImpl();
    }
}
