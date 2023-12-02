package de.sql.chat.exceptions;

/**
 * Represents the error codes used in the chat application.
 * 
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public enum ErrorCode {
  SERVER_ERROR("error.server"),
  CLIENT_ERROR("error.client"),
  SESSION_ERROR("error.session"),
  CONFIGURATION_ERROR("error.configuration");

  private final String key;

  /**
   * Constructs an ErrorCode with the specified key.
   *
   * @param key the error code key
   */
  ErrorCode(String key) {
    this.key = key;
  }

  /**
   * Returns the key associated with this error code.
   *
   * @return the error code key
   */
  public String getKey() {
    return key;
  }
}