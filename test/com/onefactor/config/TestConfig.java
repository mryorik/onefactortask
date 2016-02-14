package com.onefactor.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * User: Yaroslav Frolikov
 * Date: 15.02.16 0:10
 */
@Configuration
@ComponentScan(basePackages = {"com.onefactor.controller, com.onefactor.api"})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import(value = {ServiceBeansConfig.class, DaoBeansConfig.class, SessionFactoryConfig.class})
public class TestConfig {
}
