package com.example.integration;

import com.example.action.MessageStoreAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import org.apache.struts2.StrutsJUnit4TestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests: loads {@code struts.xml} and exercises the Struts stack
 * (dispatcher, action proxy, interceptors). Runs under Maven Failsafe (*IT).
 */
public class HelloWorldActionIT extends StrutsJUnit4TestCase<MessageStoreAction> {

    @Override
    protected String getConfigPath() {
        return "struts.xml";
    }

    @Test
    public void helloActionMappingIsRegistered() {
        ActionMapping mapping = getActionMapping("/hello.action");
        assertNotNull(mapping);
        assertEquals("hello", mapping.getName());
    }

    @Test
    public void helloSubmitActionMappingIsRegistered() {
        ActionMapping mapping = getActionMapping("/helloSubmit.action");
        assertNotNull(mapping);
        assertEquals("helloSubmit", mapping.getName());
    }

    @Test
    public void executeHelloWithUserNamePopulatesMessageStore() throws Exception {
        request.setParameter("userName", "IntegrationUser");

        ActionProxy proxy = getActionProxy("/hello.action");
        String result = proxy.execute();

        assertEquals(Action.SUCCESS, result);
        MessageStoreAction action = (MessageStoreAction) proxy.getInvocation().getAction();
        assertNotNull(action.getMessageStore());
        assertTrue(action.getMessageStore().getMessage().contains("IntegrationUser"));
    }

    @Test
    public void invalidUserNameReturnsInput() throws Exception {
        request.setParameter("userName", "bad@name");

        ActionProxy proxy = getActionProxy("/hello.action");
        String result = proxy.execute();

        assertEquals(Action.INPUT, result);
    }
}
