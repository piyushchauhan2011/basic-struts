package com.example.action;

import com.example.model.MessageStore;
import com.example.service.GreetingService;
import com.opensymphony.xwork2.ActionSupport;

public class MessageStoreAction extends ActionSupport {
    private final GreetingService greetingService = new GreetingService();

    private MessageStore messageStore;
    private String userName;

    public String execute() {
        messageStore = new MessageStore(greetingService.buildGreeting(userName));
        return ActionSupport.SUCCESS;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public MessageStore getMessageStore() {
        return messageStore;
    }
}
