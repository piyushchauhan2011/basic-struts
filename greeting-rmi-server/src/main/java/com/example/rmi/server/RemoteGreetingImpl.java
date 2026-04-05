package com.example.rmi.server;

import com.example.rmi.RemoteGreeting;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Server-side implementation; mirrors {@link com.example.service.GreetingService} logic
 * so the RMI demo stays self-contained without depending on the web module.
 */
public class RemoteGreetingImpl extends UnicastRemoteObject implements RemoteGreeting {

    public RemoteGreetingImpl() throws RemoteException {
        super();
    }

    @Override
    public String buildGreeting(String userName) throws RemoteException {
        if (userName == null || userName.isBlank()) {
            return "Hello Struts User";
        }
        return "Hello Struts User, " + userName.trim() + "!";
    }
}
