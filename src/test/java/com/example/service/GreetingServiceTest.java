package com.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GreetingServiceTest {

    private final GreetingService greetingService = new GreetingService();

    @Test
    void nullUserNameYieldsDefaultGreeting() {
        assertEquals("Hello Struts User", greetingService.buildGreeting(null));
    }

    @Test
    void blankUserNameYieldsDefaultGreeting() {
        assertEquals("Hello Struts User", greetingService.buildGreeting("   "));
    }

    @Test
    void trimsUserName() {
        assertTrue(greetingService.buildGreeting("  Ada  ").contains("Ada"));
    }

    @Test
    void longButValidUserNameIncludesValue() {
        String name = "A".repeat(50);
        assertTrue(greetingService.buildGreeting(name).contains(name));
    }
}
