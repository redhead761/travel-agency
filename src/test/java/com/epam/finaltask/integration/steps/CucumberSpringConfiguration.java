package com.epam.finaltask.integration.steps;

import com.epam.finaltask.Application;
import com.epam.finaltask.ApplicationTests;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ApplicationTests.class})
public class CucumberSpringConfiguration {
}
