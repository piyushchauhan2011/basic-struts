package com.example.service;

import com.example.rmi.RemoteGreeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

/**
 * Builds user-facing greeting text. When {@code greeting.rmi.enabled} is true (see
 * {@code greeting-rmi.properties} or JVM system properties), delegates to the RMI server;
 * otherwise uses local logic (default for CI and typical Jetty runs).
 */
public class GreetingService {

    private static final Logger LOG = LogManager.getLogger(GreetingService.class);

    private final boolean rmiEnabled;
    private final String rmiHost;
    private final int rmiPort;
    private final String rmiName;

    private volatile RemoteGreeting cachedStub;

    public GreetingService() {
        Properties defaults = loadClasspathProperties("greeting-rmi.properties");
        this.rmiEnabled = Boolean.parseBoolean(
            firstNonBlank(System.getProperty("greeting.rmi.enabled"), defaults.getProperty("greeting.rmi.enabled", "false")));
        this.rmiHost = firstNonBlank(System.getProperty("greeting.rmi.host"), defaults.getProperty("greeting.rmi.host", "localhost"));
        this.rmiPort = Integer.parseInt(
            firstNonBlank(System.getProperty("greeting.rmi.port"), defaults.getProperty("greeting.rmi.port", "1099")));
        this.rmiName = firstNonBlank(System.getProperty("greeting.rmi.name"), defaults.getProperty("greeting.rmi.name", "GreetingService"));
    }

    private static String firstNonBlank(String a, String b) {
        if (a != null && !a.isBlank()) {
            return a;
        }
        return b != null ? b : "";
    }

    private static Properties loadClasspathProperties(String name) {
        Properties p = new Properties();
        try (InputStream in = GreetingService.class.getClassLoader().getResourceAsStream(name)) {
            if (in != null) {
                p.load(in);
            }
        } catch (IOException e) {
            LOG.warn("Could not load {}: {}", name, e.getMessage());
        }
        return p;
    }

    public String buildGreeting(String userName) {
        if (rmiEnabled) {
            try {
                return remoteBuildGreeting(userName);
            } catch (Exception e) {
                LOG.error("RMI greeting failed: {}", e.toString());
                return "Greeting unavailable (RMI error — is greeting-rmi-server running?).";
            }
        }
        return localBuildGreeting(userName);
    }

    private String localBuildGreeting(String userName) {
        if (userName == null || userName.isBlank()) {
            return "Hello Struts User";
        }
        return "Hello Struts User, " + userName.trim() + "!";
    }

    private String remoteBuildGreeting(String userName) throws RemoteException, NotBoundException {
        RemoteGreeting stub = cachedStub;
        if (stub == null) {
            synchronized (this) {
                if (cachedStub == null) {
                    Registry registry = LocateRegistry.getRegistry(rmiHost, rmiPort);
                    cachedStub = (RemoteGreeting) registry.lookup(rmiName);
                }
                stub = cachedStub;
            }
        }
        return stub.buildGreeting(userName);
    }
}
