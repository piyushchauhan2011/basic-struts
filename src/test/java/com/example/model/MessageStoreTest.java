package com.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageStoreTest {

    @Test
    void defaultMessageWhenUserNameNull() {
        MessageStore store = new MessageStore(null);
        assertEquals("Hello Struts User", store.getMessage());
    }

    @Test
    void defaultMessageWhenUserNameBlank() {
        MessageStore store = new MessageStore("   ");
        assertEquals("Hello Struts User", store.getMessage());
    }

    @Test
    void personalizedMessageWhenUserNamePresent() {
        MessageStore store = new MessageStore("Ada");
        assertTrue(store.getMessage().contains("Ada"));
    }
}
