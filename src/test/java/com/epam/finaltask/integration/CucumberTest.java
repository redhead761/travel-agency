package com.epam.finaltask.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.epam.finaltask.integration.steps")
@SpringBootTest
public class CucumberTest {

}
