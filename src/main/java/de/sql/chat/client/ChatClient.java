package de.sql.chat.client;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;
import de.sql.chat.session.ChatSenderType;
import de.sql.chat.session.ChatSession;
import de.sql.chat.session.ChatSessionFactory;
import de.sql.chat.session.UserInputSource;
import java.io.*;
import java.net.*;

/**
 * The ChatClient class represents a client that connects to a chat server
 * and initiates a chat session.
 *
 * @since 8-11-2023
 */
public class ChatClient {
  private String serverIP;
  private int serverPort;
  private ChatSession clientSession;
  private Socket clientSocket;
  
  /**
   * Creates a new ChatClient instance with the specified server IP and port.
   *
   * @param serverIP The IP address of the chat server.
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
   * @throws ChatAppException If an error occurs during client setup.
   */
  public void start(UserInputSource userInputSource) throws ChatAppException {
    try {
      clientSocket = new Socket(serverIP, serverPort);
      this.clientSession = ChatSessionFactory.createChatSession(ChatSenderType.CLIENT, clientSocket, userInputSource);
      System.out.println("Connected to server. You can start typing messages.");
      this.clientSession.start();
    } catch (IOException e) {
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
      // Close the chat session
      clientSession.close();

      // Close the client socket
      clientSocket.close();

      System.out.println("Disconnected from server.");
    } catch (IOException e) {
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

  public void sendMessage(String hello) {
    this.clientSession.sendMessage(hello);
  }
}