package de.sql.chat.session;

import de.sql.chat.client.ChatClient;
import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.server.ChatServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatSessionTest {
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
        // Wait for the server to start before creating the client socket
        try {
            Thread.sleep(1000); // Adjust the delay if needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create the client socket
        chatClient = new ChatClient(chatServer.getServerIP(), chatServer.getServerPort());
    }

    @AfterAll
    public static void cleanup() throws IOException, ChatAppException {
        chatServer.close();
        chatClient.close();
    }

    @Test
    public void testChatSession() throws IOException, InterruptedException, ChatAppException {
      
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
        Thread.sleep(500);

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
}
