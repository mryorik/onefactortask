package com.onefactor.config;

import com.onefactor.service.DataLoadingService;
import com.onefactor.service.DataLoadingServiceImpl;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 1:35
 */
@Configuration
@ComponentScan(basePackages = {"com.onefactor.controller, com.onefactor.api"})
@EnableWebMvc
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import(value = {ServiceBeansConfig.class, DaoBeansConfig.class, SessionFactoryConfig.class})
public class OneFactorTaskSpringConfig extends WebMvcConfigurerAdapter {
    @Bean
    DataLoadingService dataLoadingService() {
        return new DataLoadingServiceImpl();
    }
}
