package de.sql.chat.session;

/**
 * Enum representing the sender type (e.g., "Client" or "Server") for chat messages.
 *
 * @since 8-11-2023
 */
public enum ChatSenderType {
  CLIENT("Client"),
  SERVER("Server");

  private final String displayName;

  /**
   * Constructs a ChatSenderType with the specified display name.
   *
   * @param displayName the display name of the sender type
   */
  ChatSenderType(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Returns the display name of the sender type.
   *
   * @return the display name
   */
  @Override
  public String toString() {
    return displayName;
  }
}