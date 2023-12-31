package com.pwpo.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Profile("production")
public class FlywayProdConfiguration {

    @Autowired
    public FlywayProdConfiguration(DataSource dataSource) throws SQLException {
        Flyway.configure().locations("db/dev")
                .baselineOnMigrate(true)
                .dataSource(dataSource)
                .load()
                .migrate();
    }

}