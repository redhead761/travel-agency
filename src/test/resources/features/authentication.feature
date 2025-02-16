Feature: Authentication flow

  Scenario: User successfully logs in and then logs out
    Given the system is set up with a user "testUser" and password "Password1234"
    When the user logs in with username "testUser" and password "Password1234"
    Then the login should be successful and a token should be returned
    When the user logs out with the token
    Then the token should be blacklisted
