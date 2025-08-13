package com.Hootsybit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Autowired
    private DataSource dataSource;
    
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try (Connection connection = dataSource.getConnection()) {
            logger.info("Database connection successful!");
            logger.info("Database: " + connection.getMetaData().getURL());
            logger.info("Username: " + connection.getMetaData().getUserName());
        } catch (SQLException e) {
            logger.error("Database connection failed!", e);
        }
    }
}