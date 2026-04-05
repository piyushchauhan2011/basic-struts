package com.example.rmi.server;

import com.example.rmi.RemoteGreeting;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Starts an RMI registry on a configurable port and binds {@link RemoteGreeting}.
 * Run before the web app when using RMI mode (see README).
 */
public final class RmiGreetingServer {

    private RmiGreetingServer() {
    }

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getProperty("greeting.rmi.registry.port", "1099"));
        String bindName = System.getProperty("greeting.rmi.name", "GreetingService");

        RemoteGreeting impl = new RemoteGreetingImpl();
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(bindName, impl);

        System.out.println("RMI GreetingService bound: rmi://localhost:" + port + "/" + bindName);
        System.out.println("Press Ctrl+C to stop.");
        Thread.currentThread().join();
    }
}
