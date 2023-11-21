package de.sql.chat.session;

import de.sql.chat.exceptions.ChatAppException;

import java.io.IOException;
import java.net.Socket;

/**
 * Factory class to create instances of ChatSession.
 */
public class ChatSessionFactory {
  /**
   * Create a ChatSession with the specified sender type and socket.
   *
   * @param sender The sender type (e.g., ChatSenderType.CLIENT or ChatSenderType.SERVER).
   * @param socket The socket for communication.
   * @return A new ChatSession instance.
   */
  public static ChatSession createChatSession(ChatSenderType sender, Socket socket, UserInputSource userInputSource)
      throws IOException, ChatAppException {

    return new ChatSession(sender, socket, userInputSource);
  }
}