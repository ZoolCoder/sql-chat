package de.sql.chat.session;

import de.sql.chat.client.ChatClient;
import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.server.ChatServer;
import de.sql.chat.util.TestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatSessionTest {
    private static ChatServer chatServer;
    private static ChatClient chatClient;

    @BeforeAll
    public static void setup() throws ChatAppException {
        // Start the chat server
        chatServer = new ChatServer();
        chatServer.start(new EmptyUserInputSource());
        System.out.println("Server started successfully!");
        TestUtils.sleepForShortDuration(500);

        // Start the chat client
        chatClient = new ChatClient(chatServer.getServerIP(), chatServer.getServerPort());
        chatClient.start(new EmptyUserInputSource());
        TestUtils.sleepForShortDuration(500);
    }

    @AfterAll
    public static void cleanup() throws ChatAppException {
        chatServer.close();
        chatClient.close();
    }

    @Test
    void testChatSession() throws InterruptedException {

        // / Start the chat session in a separate thread
        Thread chatSessionThread = new Thread(() -> {
          try {
              chatClient.start(new EmptyUserInputSource());
          } catch (ChatAppException e) {
              e.printStackTrace();
          }
        });
        chatSessionThread.start();

        // Add a delay here
        TestUtils.sleepForShortDuration(500);

        // Send messages from the client
        chatClient.getClientSession().sendMessage("Hello");
        chatClient.getClientSession().sendMessage("How are you?");
        chatClient.getClientSession().sendMessage("Exit");

        // Wait for the chat session thread to finish
        chatSessionThread.join();

        // Get the user messages from the chat session
        List<String> userMessages = chatClient.getClientSession().getUserMessages();
        // Verify the user messages
        assertEquals(2, userMessages.size());
        assertEquals("Client: Hello", userMessages.get(0));
        assertEquals("Client: How are you?", userMessages.get(1));
    }

    @Test
    void testClearUserMessages() throws InterruptedException {
        // / Start the chat session in a separate thread
        Thread chatSessionThread = new Thread(() -> {
            try {
                chatClient.start(new EmptyUserInputSource());
            } catch (ChatAppException e) {
                e.printStackTrace();
            }
        });
        chatSessionThread.start();

        // Add a delay here
        TestUtils.sleepForShortDuration(500);
        // Add some messages to the userMessages list
        chatClient.getClientSession().sendMessage("Hello");
        chatClient.getClientSession().sendMessage("How are you?");

        chatSessionThread.join();
        // Clear the userMessages list
        chatClient.getClientSession().clearUserMessages();

        // Get the user messages from the chat session
        List<String> userMessages = chatClient.getClientSession().getUserMessages();

        // Verify that the userMessages list is empty
        assertTrue(userMessages.isEmpty());
    }
}