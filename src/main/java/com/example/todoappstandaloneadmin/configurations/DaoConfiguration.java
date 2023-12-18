package com.example.todoappstandaloneadmin.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
public class DaoConfiguration {
    private String databaseUrl;

    @Value("${DB_USERNAME}")
    private String databaseUsername;

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUsername() { // TODO this doesn't work i have no idea
        return databaseUsername;
    }
}
