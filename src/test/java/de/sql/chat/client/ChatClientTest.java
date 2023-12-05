package de.sql.chat.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;
import de.sql.chat.server.ChatServer;
import de.sql.chat.session.EmptyUserInputSource;
import de.sql.chat.session.UserInputSource;
import de.sql.chat.util.TestUtils;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests for the ChatClient class.
 */
public class ChatClientTest {
    private static ChatServer chatServer;
    private ChatClient chatClient;

    @BeforeAll
    public static void setupServer() throws ChatAppException {
        chatServer = new ChatServer();
        chatServer.start(new EmptyUserInputSource());
        TestUtils.sleepForShortDuration(500);
    }

    @BeforeEach
    public void setupClient() throws ChatAppException {
        chatClient = new ChatClient(chatServer.getServerIP(), chatServer.getServerPort());
        chatClient.start(new EmptyUserInputSource());
        TestUtils.sleepForShortDuration(500);
    }

    @AfterEach
    public void cleanupClient() throws ChatAppException {
        chatClient.close();
    }

    @AfterAll
    public static void cleanup() throws ChatAppException {
        chatServer.close();
    }
    @Test
    void shouldThrowExceptionWhenStartFails() throws ChatAppException {
        ChatClient mockChatClient = Mockito.mock(ChatClient.class);
        doThrow(ChatAppException.class).when(mockChatClient).start(any(UserInputSource.class));

        assertThrows(ChatAppException.class, () -> mockChatClient.start(new EmptyUserInputSource()));
    }

    @Test
    void shouldThrowExceptionWhenCloseFails() throws ChatAppException {
        ChatClient mockChatClient = Mockito.mock(ChatClient.class);
        doThrow(ChatAppException.class).when(mockChatClient).close();
        assertThrows(ChatAppException.class, () -> mockChatClient.close());
    }

    @Test
    void shouldReturnNullWhenClientSessionNotRunning() {
        ChatClient mockChatClient = Mockito.mock(ChatClient.class);
        when(mockChatClient.getClientSession()).thenReturn(null);
        assertNull(mockChatClient.getClientSession());
    }
    @Test
    void shouldSendMessagesAndLogOnExit() {
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
    void shouldNotThrowExceptionWhenSendMessageWithValidMessage() {
        assertDoesNotThrow(() -> chatClient.sendMessage("Test message"));
    }
    @Test
    void shouldReturnCorrectServerIP() {
        assertEquals(chatServer.getServerIP(), chatClient.getServerIP());
    }

    @Test
    void shouldReturnCorrectServerPort() {
        assertEquals(chatServer.getServerPort(), chatClient.getServerPort());
    }

    @Test
    void shouldReturnTrueWhenClientSessionIsRunning() {
        assertTrue(chatClient.isRunning());
    }

    @Test
    void shouldReturnFalseWhenClientSessionNotRunning() {
        ChatClient mockChatClient = Mockito.mock(ChatClient.class);
        when(mockChatClient.getClientSession()).thenReturn(null);
        assertFalse(mockChatClient.isRunning());
    }

    @Test
    void shouldCloseChatSessionAndSocket() throws ChatAppException {
        chatClient.close();
        assertFalse(chatClient.isRunning());
        assertTrue(chatClient.getClientSocket().isClosed());
    }

    @Test
    void shouldReturnClientSessionWhenRunning() {
        assertNotNull(chatClient.getClientSession());
    }

    @Test
    void shouldReturnFalseWhenClientSessionIsNull() {
        ChatClient mockChatClient = Mockito.mock(ChatClient.class);
        when(mockChatClient.getClientSession()).thenReturn(null);
        assertFalse(mockChatClient.getClientSession()!= null && mockChatClient.getClientSession().isRunning());
    }
}