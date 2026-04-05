package com.example.model;

/**
 * Holds the message shown on the hello result page.
 */
public class MessageStore {
    private final String message;

    public MessageStore(String message) {
        this.message = message != null ? message : "";
    }

    public String getMessage() {
        return message;
    }
}
