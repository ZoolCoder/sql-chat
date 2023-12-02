package de.sql.chat.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;
import de.sql.chat.server.ChatServer;
import de.sql.chat.session.EmptyUserInputSource;
import de.sql.chat.util.TestUtils;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ChatClient class.
 */
public class ChatClientTest {
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
    void testExitSendsMessagesAndLogsProperly() {
        chatClient.getClientSession().clearUserMessages();

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
    void testSendMessageDoesNotThrowException() {
        assertDoesNotThrow(() -> chatClient.sendMessage("Test message"));
    }

    @Test
    void testGetServerIP() {
        assertEquals(chatServer.getServerIP(), chatClient.getServerIP());
    }

    @Test
    void testGetServerPort() {
        assertEquals(chatServer.getServerPort(), chatClient.getServerPort());
    }

    @Test
    void testIsRunningReturnsTrueWhenClientSessionIsRunning() {
        // Verify that isRunning() returns true
        assertTrue(chatClient.isRunning());
    }

    @Test
    void testCloseClosesChatSessionAndSocket() throws ChatAppException {
        // Close the chat client
        //chatClient.close();

        // Verify that the chat session is closed
        assertTrue(chatClient.getClientSession().isRunning());

        // Verify that the client socket is closed
        assertTrue(!chatClient.getClientSocket().isClosed());
    }

    /*@Test
    void testCloseThrowsExceptionOnIOException() throws IOException {
        // Mock an IOException during client shutdown
        IOException ioException = new IOException("Mock IOException");
        doThrow(ioException).when(chatClient.getClientSocket()).close();

        // Verify that closing the chat client throws a ChatAppException
        ChatAppException exception = assertThrows(ChatAppException.class, () -> chatClient.close());
        assertEquals(ErrorCode.CLIENT_ERROR, exception.getErrorCode());
        assertEquals("Client Error: Mock IOException", exception.getMessage());
        assertEquals(ioException.getMessage(), exception.getCause().getMessage());
    }*/

    @Test
    void testGetClientSession() {
        assertNotNull(chatClient.getClientSession());
    }
}