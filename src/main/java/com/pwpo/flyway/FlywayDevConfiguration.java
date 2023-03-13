package com.pwpo.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@Profile("dev")
public class FlywayDevConfiguration {

    @Autowired
    public FlywayDevConfiguration(DataSource dataSource) throws SQLException {
        dataSource.getConnection().prepareStatement("DELETE FROM flyway_schema_history").execute();

        Flyway.configure().locations("db/dev")
                .baselineOnMigrate(true)
                .dataSource(dataSource)
                .load()
                .migrate();
    }

}