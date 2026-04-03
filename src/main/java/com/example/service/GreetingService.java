package com.example.service;

/**
 * Builds user-facing greeting text. Kept separate from the action for clarity and testing.
 */
public class GreetingService {

    public String buildGreeting(String userName) {
        if (userName == null || userName.isBlank()) {
            return "Hello Struts User";
        }
        return "Hello Struts User, " + userName.trim() + "!";
    }
}
