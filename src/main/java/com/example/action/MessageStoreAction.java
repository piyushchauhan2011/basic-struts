package com.example.action;

import com.example.model.MessageStore;
import com.opensymphony.xwork2.ActionSupport;

public class MessageStoreAction extends ActionSupport {
    private MessageStore messageStore;
    private String userName;

    public String execute() {
        messageStore = new MessageStore(userName);
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
