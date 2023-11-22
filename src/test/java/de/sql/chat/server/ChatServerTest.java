package de.sql.chat.server;

import static org.junit.jupiter.api.Assertions.*;

import de.sql.chat.session.EmptyUserInputSource;
import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.*;

import de.sql.chat.exceptions.ChatAppException;

public class ChatServerTest {

  private static ChatServer chatServer;
  private static Socket clientSocket;

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
    clientSocket = new Socket("localhost", chatServer.getServerPort());
  }

  @AfterAll
  public static void cleanup() throws IOException {
   // chatServer.close();
    clientSocket.close();
  }

  @Test
  public void testStart() throws ChatAppException {
    assertTrue(clientSocket.isConnected());
  }

  @Test
  public void testGetServerIP() {
    String serverIP = chatServer.getServerIP();
    assertNotNull(serverIP);
    assertFalse(serverIP.isEmpty());
  }

  @Test
  public void testGetServerPort() {
    int serverPort = chatServer.getServerPort();
    assertTrue(serverPort > 0);
  }
}