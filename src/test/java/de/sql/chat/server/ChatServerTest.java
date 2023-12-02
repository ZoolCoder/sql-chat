package de.sql.chat.server;

import static org.junit.jupiter.api.Assertions.*;

import de.sql.chat.client.ChatClient;
import de.sql.chat.session.EmptyUserInputSource;
import de.sql.chat.util.TestUtils;
import org.junit.jupiter.api.*;

import de.sql.chat.exceptions.ChatAppException;

public class ChatServerTest {

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
}