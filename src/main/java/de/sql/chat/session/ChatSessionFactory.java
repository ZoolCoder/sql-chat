package de.sql.chat.session;

import de.sql.chat.exceptions.ChatAppException;

import java.io.IOException;
import java.net.Socket;

/**
 * Factory class to create instances of ChatSession.
 * 
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class ChatSessionFactory {

  private ChatSessionFactory() {
    // Private constructor to prevent instantiation from outside the class.
  }

  /**
  * Create a ChatSession with the specified sender type and socket.
  *
  * @param sender The sender type (e.g., ChatSenderType.CLIENT or ChatSenderType.SERVER).
  * @param socket The socket for communication.
  * @param userInputSource The source of user input.
  * @return A new ChatSession instance.
  * @throws IOException If an I/O error occurs while creating the ChatSession.
  * @throws ChatAppException If an exception specific to the ChatApp occurs while creating the ChatSession.
  */
  public static ChatSession createChatSession(ChatSenderType sender, Socket socket, UserInputSource userInputSource) throws ChatAppException {

    return new ChatSession(sender, socket, userInputSource);
  }
}