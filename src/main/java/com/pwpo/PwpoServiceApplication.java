package com.pwpo;

import com.pwpo.common.service.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Locale;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class PwpoServiceApplication {

    static {
        Locale.setDefault(Locale.US);
    }

    public static void main(String[] args) {
        SpringApplication.run(PwpoServiceApplication.class, args);
    }
}
