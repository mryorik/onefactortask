package com.onefactor.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 22:27
 */
@Configuration
public class SessionFactoryConfig {
    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:onefactortask;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

        sessionFactoryBean.setPackagesToScan("com.onefactor.dmodel");
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setHibernateProperties(hibernateProperties());

        return sessionFactoryBean;
    }

    @Bean
    PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    private Properties hibernateProperties() {
        return new Properties() {{
            setProperty("hibernate.connection.characterEncoding", "utf8");
            setProperty("hibernate.connection.charSet", "utf8");
            setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            setProperty("hibernate.show_sql", "false");
            setProperty("hibernate.hbm2ddl.auto", "create");
            setProperty("hibernate.connection.autocommit", "false");
            setProperty("hibernate.jdbc.batch_size", "20");
        }};
    }
}
