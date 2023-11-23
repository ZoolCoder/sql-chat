package de.sql.chat.server;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;
import de.sql.chat.localization.LocalizationBundle;
import de.sql.chat.localization.LocalizedResourceManager;
import de.sql.chat.session.ChatSession;
import de.sql.chat.session.ChatSessionFactory;
import de.sql.chat.session.ChatSenderType;

import de.sql.chat.session.UserInputSource;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The ChatServer class represents a server that handles incoming client connections
 * and initiates chat sessions with the clients.
 *
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class ChatServer {

  private static final Logger LOGGER = LogManager.getLogger(ChatServer.class);
  private ServerSocket serverSocket;
  private Socket clientSocket;
  private String serverIP;
  private int serverPort;
  private ChatSession serverSession;

  /**
   * Starts the chat server by creating a server socket and waiting for
   * a single incoming client connection. Once a client connects, a chat session is initiated.
   *
   * @param userInputSource The source of user input for the chat session.
   * @throws ChatAppException If an error occurs during server setup.
   */
  public void start(UserInputSource userInputSource) throws ChatAppException {
    try {
      setupServer();
      acceptClientConnection();

      ChatSession serverSession = ChatSessionFactory.createChatSession(ChatSenderType.SERVER, clientSocket, userInputSource);
      serverSession.start();
    } catch (IOException e) {
      LOGGER.error("Error during server setup: {}", e.getMessage());
      throw new ChatAppException(ErrorCode.SERVER_ERROR, LocalizedResourceManager.getInstance().getMessage(LocalizationBundle.MESSAGES, "server.error") + ": " + e.getMessage());
    }
  }

  /**
   * Sets up the server socket.
   *
   * @throws IOException If an error occurs while setting up the server socket.
   */
  private void setupServer() throws IOException {
    serverSocket = new ServerSocket(0);
    this.serverIP = InetAddress.getLocalHost().getHostAddress();
    this.serverPort = serverSocket.getLocalPort();
    System.out.println(LocalizedResourceManager.getInstance().getFormattedMessage(LocalizationBundle.MESSAGES, "server.started", serverIP, serverPort));
    LOGGER.info("Server started at {}:{}", serverIP, serverPort);
  }

  /**
   * Accepts a single client connection.
   *
   * @throws IOException If an error occurs while accepting the client connection.
   */
  private void acceptClientConnection() throws IOException {
    clientSocket = serverSocket.accept();
    System.out.println(LocalizedResourceManager.getInstance().getMessage(LocalizationBundle.MESSAGES, "server.connected"));
    LOGGER.info("Client connected from {}:{}", clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
  }

  /**
   * Closes the server socket and client socket.
   */
  public void close() {
    try {
      if (clientSocket != null) {
        clientSocket.close();
      }
      if (serverSocket != null) {
        serverSocket.close();
        LOGGER.info("Server closed.");
      }
    } catch (IOException e) {
      LOGGER.error("Error during server socket closure: {}", e.getMessage());
    }
  }

  /**
   * Returns the IP address of the server.
   *
   * @return The IP address of the server.
   */
  public String getServerIP() {
    return serverIP;
  }

  /**
   * Returns the port number of the server.
   *
   * @return The port number of the server.
   */
  public int getServerPort() {
    return serverPort;
  }

  /**
   * Returns the chat session associated with the server.
   *
   * @return The chat session associated with the server.
   */
  public ChatSession getServerSession() {
    return serverSession;
  }
}