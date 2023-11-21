package de.sql.chat.exceptions;

public enum ErrorCode {
  SERVER_ERROR("error.server"),
  CLIENT_ERROR("error.client"),
  SESSION_ERROR("error.session"),

  COMMUNICATION_ERROR("error.communication");
  private final String key;

  ErrorCode(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}