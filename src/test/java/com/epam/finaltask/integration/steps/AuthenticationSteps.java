package com.epam.finaltask.integration.steps;

import com.epam.finaltask.model.Role;
import com.epam.finaltask.model.User;
import com.epam.finaltask.repository.JwtBlackListRepository;
import com.epam.finaltask.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class AuthenticationSteps {
    private final MockMvc mockMvc;
    private final JwtBlackListRepository jwtBlacklistRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private String token;

    public AuthenticationSteps(MockMvc mockMvc, JwtBlackListRepository jwtBlacklistRepository,
                               UserRepository userRepository, PasswordEncoder passwordEncoder) {
        log.debug("!!!!!!!!!!!!!!!!!! IN AUTH STEPS CONSTRUCTOR ===={}{}{}{}", mockMvc, jwtBlacklistRepository, userRepository, passwordEncoder);
        this.mockMvc = mockMvc;
        this.jwtBlacklistRepository = jwtBlacklistRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Before
    public void setUp() {
        jwtBlacklistRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Given("the system is set up with a user {string} and password {string}")
    public void givenUserSetup(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccountStatus(true);
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    @When("the user logs in with username {string} and password {string}")
    public void whenUserLogsIn(String username, String password) throws Exception {
        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password)))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();

        token = JsonPath.parse(loginResponse).read("$.results[0].token");
    }

    @Then("the login should be successful and a token should be returned")
    public void thenLoginShouldBeSuccessful() {
        assertTrue(token != null && !token.isEmpty());
    }

    @When("the user logs out with the token")
    public void whenUserLogsOut() throws Exception {
        mockMvc.perform(post("/auth/logout")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Then("the token should be blacklisted")
    public void thenTokenShouldBeBlacklisted() {
        assertTrue(jwtBlacklistRepository.existsById(token));
    }

    @After
    public void cleanUp(){
        userRepository.deleteAll();
        jwtBlacklistRepository.deleteAll();
    }
}
