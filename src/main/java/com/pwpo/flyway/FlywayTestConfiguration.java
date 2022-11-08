package com.pwpo.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class FlywayTestConfiguration {

    @Autowired
    public FlywayTestConfiguration(DataSource dataSource) {
        Flyway.configure().locations("db/test")
                .baselineOnMigrate(true).dataSource(dataSource).load().migrate();
    }

}