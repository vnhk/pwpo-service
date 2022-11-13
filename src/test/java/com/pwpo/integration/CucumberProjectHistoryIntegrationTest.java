package com.pwpo.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/CucumberProjectHistoryIntegrationTest.html"},
        features = "src/test/resources/features/project/ProjectHistory.feature"
)
public class CucumberProjectHistoryIntegrationTest {
}