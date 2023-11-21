package de.sql.chat.client;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.server.ChatServer;
import de.sql.chat.session.EmptyUserInputSource;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatClientTest {
    private static ChatServer chatServer;
    private static ChatClient chatClient;

    @BeforeAll
    public static void setup() throws IOException, ChatAppException {
        chatServer = new ChatServer();
        Thread serverThread = new Thread(() -> {
            try {
                chatServer.start(new EmptyUserInputSource());
            } catch (ChatAppException e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
        // Wait for the server to start before creating the client
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create the client socket
        chatClient = new ChatClient(chatServer.getServerIP(), chatServer.getServerPort());
        // / Start the chat session in a separate thread
        Thread chatSessionThread = new Thread(() -> {
            try {
                chatClient.start(new EmptyUserInputSource());
            } catch (ChatAppException e) {
                e.printStackTrace();
            }
        });
        chatSessionThread.start();
    }

    @AfterAll
    public static void cleanup() throws IOException, ChatAppException {
        chatServer.close();
        chatClient.close();
    }

    @Test
    public void testExit()  {
        // Send messages from the client
        chatClient.sendMessage("Hello");
        chatClient.sendMessage("How are you?");
        chatClient.sendMessage("Exit");
        // Get the user messages from the chat session
        List<String> userMessages = chatClient.getClientSession().getUserMessages();
        // Verify the user messages
        assertEquals(2, userMessages.size());
        assertEquals("Client: Hello", userMessages.get(0));
        assertEquals("Client: How are you?", userMessages.get(1));
    }


    @Test
    void testSendMessage() {
        assertDoesNotThrow(() -> chatClient.sendMessage("Test message"));
    }

}