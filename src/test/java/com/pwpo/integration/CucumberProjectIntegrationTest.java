package com.pwpo.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber"},
        features = "src/test/resources/features/project",
        glue = {
                "com.pwpo.integration.steps.common",
                "com.pwpo.integration.steps.project"
        }
)
public class CucumberProjectIntegrationTest {
}