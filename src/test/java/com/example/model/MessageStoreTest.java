package com.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageStoreTest {

    @Test
    void nullMessageBecomesEmptyString() {
        MessageStore store = new MessageStore(null);
        assertEquals("", store.getMessage());
    }

    @Test
    void storesMessageVerbatim() {
        MessageStore store = new MessageStore("Hello Struts User");
        assertEquals("Hello Struts User", store.getMessage());
    }

    @Test
    void personalizedContentIsStored() {
        MessageStore store = new MessageStore("Hello Struts User, Ada!");
        assertTrue(store.getMessage().contains("Ada"));
    }
}
