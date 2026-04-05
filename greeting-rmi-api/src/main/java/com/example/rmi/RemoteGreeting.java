package com.example.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for the RMI greeting demo. Implemented by the standalone
 * {@code greeting-rmi-server} process and looked up by the web app when RMI mode is enabled.
 */
public interface RemoteGreeting extends Remote {

    String buildGreeting(String userName) throws RemoteException;
}
