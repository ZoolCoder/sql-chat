package de.sql.chat.client;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;
import de.sql.chat.session.ChatSenderType;
import de.sql.chat.session.ChatSession;
import de.sql.chat.session.ChatSessionFactory;
import de.sql.chat.session.UserInputSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * The ChatClient class represents a client that connects to a chat server
 * and initiates a chat session.
 *
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class ChatClient {

  private static final Logger LOGGER = LogManager.getLogger(ChatClient.class);
  private String serverIP;
  private int serverPort;
  private ChatSession clientSession;
  private Socket clientSocket;

  /**
   * Creates a new ChatClient instance with the specified server IP and port.
   *
   * @param serverIP   The IP address of the chat server.
   * @param serverPort The port on which the server is listening.
   */
  public ChatClient(String serverIP, int serverPort) {
    this.serverIP = serverIP;
    this.serverPort = serverPort;
  }

  /**
   * Starts the chat client by establishing a connection to the server and
   * initiating a chat session.
   *
   * @param userInputSource The source of user input for the chat session.
   * @throws ChatAppException If an error occurs during client setup.
   */
  public void start(UserInputSource userInputSource) throws ChatAppException {
    try {
      LOGGER.info("Connecting to server {}:{}", serverIP, serverPort);
      clientSocket = new Socket(serverIP, serverPort);
      LOGGER.info("Connected to server. You can start typing messages.");

      this.clientSession = ChatSessionFactory.createChatSession(ChatSenderType.CLIENT, clientSocket, userInputSource);
      System.out.println("Connected to server. You can start typing messages.");
      this.clientSession.start();
    } catch (IOException e) {
      LOGGER.error("Error during client setup: {}", e.getMessage());
      throw new ChatAppException(ErrorCode.CLIENT_ERROR, "Client Error: " + e.getMessage());
    }
  }

  /**
   * Closes the chat client by closing the chat session and disconnecting from the server.
   *
   * @throws ChatAppException If an error occurs during client shutdown.
   */
  public void close() throws ChatAppException {
    try {
      LOGGER.info("Closing chat client...");

      // Close the chat session
      if (clientSession != null) {
        clientSession.close();
      }

      // Close the client socket
      if (clientSocket != null && !clientSocket.isClosed()) {
        clientSocket.close();
      }

      LOGGER.info("Chat client closed.");
    } catch (IOException e) {
      LOGGER.error("Error during client shutdown: {}", e.getMessage());
      throw new ChatAppException(ErrorCode.CLIENT_ERROR, "Client Error: " + e.getMessage());
    }
  }

  /**
   * Returns the IP address of the chat server.
   *
   * @return The IP address of the chat server.
   */
  public String getServerIP() {
    return serverIP;
  }

  /**
   * Returns the port on which the chat server is listening.
   *
   * @return The port on which the chat server is listening.
   */
  public int getServerPort() {
    return serverPort;
  }

  /**
   * Returns the chat session associated with the client.
   *
   * @return The chat session associated with the client.
   */
  public ChatSession getClientSession() {
    return this.clientSession;
  }

  /**
   * Returns the client socket used for communication with the server.
   *
   * @return The client socket used for communication with the server.
   */
  public Socket getClientSocket() {
    return clientSocket;
  }

  /**
   * Sends a message to the chat server through the client session.
   *
   * @param message The message to be sent.
   */
  public void sendMessage(String message) {
    LOGGER.debug("Sending message: {}", message);
    this.clientSession.sendMessage(message);
  }
}