package com.example.action;

import com.opensymphony.xwork2.Action;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageStoreActionTest {

    @Test
    void executeReturnsSuccessAndBuildsMessageStoreFromUserName() throws Exception {
        MessageStoreAction action = new MessageStoreAction();
        action.setUserName("UnitTest");

        String result = action.execute();

        assertEquals(Action.SUCCESS, result);
        assertNotNull(action.getMessageStore());
        assertTrue(action.getMessageStore().getMessage().contains("UnitTest"));
    }

    @Test
    void executeWithoutUserNameUsesDefaultGreeting() throws Exception {
        MessageStoreAction action = new MessageStoreAction();

        String result = action.execute();

        assertEquals(Action.SUCCESS, result);
        assertEquals("Hello Struts User", action.getMessageStore().getMessage());
    }
}
