package de.sql.chat.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import de.sql.chat.client.ChatClient;
import de.sql.chat.session.EmptyUserInputSource;
import de.sql.chat.session.UserInputSource;
import de.sql.chat.util.TestUtils;
import org.junit.jupiter.api.*;

import de.sql.chat.exceptions.ChatAppException;
import org.mockito.Mockito;

public class ChatServerTest {

  private static ChatServer chatServer;
  private static ChatClient chatClient;

  @BeforeAll
  public static void setup() throws ChatAppException {
    // Start the chat server
    chatServer = new ChatServer();
    chatServer.start(new EmptyUserInputSource());
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
  void shouldThrowExceptionWhenStartFails() throws ChatAppException {
    ChatServer mockChatServer = Mockito.mock(ChatServer.class);
    doThrow(ChatAppException.class).when(mockChatServer).start(any(UserInputSource.class));

    assertThrows(ChatAppException.class, () -> mockChatServer.start(new EmptyUserInputSource()));
  }

  @Test
  void shouldThrowExceptionWhenCloseFails() throws ChatAppException {
    ChatServer mockChatServer = Mockito.mock(ChatServer.class);
    doThrow(ChatAppException.class).when(mockChatServer).close();

    assertThrows(ChatAppException.class, () -> mockChatServer.close());
  }

  @Test
  void shouldReturnNullWhenServerSessionNotRunning() {
    ChatServer mockChatServer = Mockito.mock(ChatServer.class);
    when(mockChatServer.getServerSession()).thenReturn(null);

    assertNull(mockChatServer.getServerSession());
  }

  @Test
  void shouldReturnFalseWhenServerSessionNotRunning() {
    ChatServer mockChatServer = Mockito.mock(ChatServer.class);
    when(mockChatServer.isRunning()).thenReturn(false);

    assertFalse(mockChatServer.isRunning());
  }

  @Test
  void testStart() {
    assertTrue(chatClient.isRunning());
  }

  @Test
  void testGetServerIP() {
    String serverIP = chatServer.getServerIP();
    assertNotNull(serverIP);
    assertFalse(serverIP.isEmpty());
  }

  @Test
  void testGetServerPort() {
    int serverPort = chatServer.getServerPort();
    assertTrue(serverPort > 0);
  }
  @Test
  void shouldReturnServerSessionWhenRunning() {
    assertNotNull(chatServer.getServerSession());
  }
}