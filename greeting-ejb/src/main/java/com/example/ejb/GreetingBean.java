package com.example.ejb;

import javax.ejb.Stateless;

/**
 * Minimal stateless session bean for learning EJB packaging and deployment on a full
 * Java EE / Jakarta EE server (Jetty and plain Tomcat do not host EJBs).
 */
@Stateless
public class GreetingBean {

    public String buildGreeting(String userName) {
        if (userName == null || userName.isBlank()) {
            return "Hello Struts User";
        }
        return "Hello Struts User, " + userName.trim() + "!";
    }
}
