package de.sql.chat;

import de.sql.chat.client.ChatClient;
import de.sql.chat.server.ChatServer;
import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.init.AppInitializer;
import de.sql.chat.session.ScannerUserInputSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The main class for the Peer-to-Peer Chat application.
 * It handles command-line arguments and initiates the chat server or client accordingly.
 *
 * @author Abdallah Emad
 * @since 8-11-2023
 */
public class PeerToPeerChat {
  private static final Logger LOGGER = LogManager.getLogger(PeerToPeerChat.class);
  public static void main(String[] args) {
    
    // Initializes the application
    AppInitializer appInitializer = AppInitializer.getInstance();
    appInitializer.initialize();
    
    String otherInstanceIP = null;
    int otherInstancePort = 0;

    if (args.length == 2 && args[0].equals("--other_instance")) {
      String[] parts = args[1].split(":");
      if (parts.length == 2) {
        otherInstanceIP = parts[0];
        otherInstancePort = Integer.parseInt(parts[1]);
      }
    }

    try {
      if (otherInstanceIP != null && otherInstancePort != 0) {
        startClient(otherInstanceIP, otherInstancePort);
      } else {
        startServer();
      }
    } catch (ChatAppException e) {
      LOGGER.error("Chat Application Error: {}", e.getMessage());
      System.err.println("Chat Application Error: " + e.getMessage());
    }
  }

  /**
   * Starts the chat client with the given IP and port.
   *
   * @param serverIP The IP address of the chat server.
   * @param serverPort The port on which the server is listening.
   * @throws ChatAppException If an error occurs during client setup.
   */
  private static void startClient(String serverIP, int serverPort) throws ChatAppException {
    LOGGER.info("Starting chat client with server IP: {} and port: {}", serverIP, serverPort);
    new ChatClient(serverIP, serverPort).start(new ScannerUserInputSource());
  }

  /**
   * Starts the chat server and waits for incoming connections.
   *
   * @throws ChatAppException If an error occurs during server setup.
   */
  private static void startServer() throws ChatAppException {
    LOGGER.info("Starting chat server");
    new ChatServer().start(new ScannerUserInputSource());
  }
}