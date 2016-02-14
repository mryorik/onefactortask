package com.onefactor.config;

import com.onefactor.dao.MapCellDao;
import com.onefactor.dao.MapCellJdbcDao;
import com.onefactor.dao.PlacemarkDao;
import com.onefactor.dao.PlacemarkJdbcDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 23:45
 */
@Configuration
public class DaoBeansConfig {
    @Bean
    PlacemarkDao placemarkDao() {
        return new PlacemarkJdbcDao();
    }

    @Bean
    MapCellDao mapCellDao() {
        return new MapCellJdbcDao();
    }
}
