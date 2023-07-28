package com.pwpo;

import com.pwpo.common.service.PwpoBaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.Locale;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = PwpoBaseRepositoryImpl.class)
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true, proxyTargetClass = true)
@ComponentScan(basePackages = {"com.bervan.*", "com.pwpo.*"})
public class PwpoServiceApplication {

    static {
        Locale.setDefault(Locale.US);
    }

    public static void main(String[] args) {
        SpringApplication.run(PwpoServiceApplication.class, args);
    }
}
