package com.example.model;

public class MessageStore {
    private String message;

    public MessageStore() {
        this(null);
    }

    public MessageStore(String userName) {
        if (userName != null && !userName.isBlank()) {
            message = "Hello Struts User, " + userName + "!";
        } else {
            message = "Hello Struts User";
        }
    }

    public String getMessage() {
        return message;
    }
}
