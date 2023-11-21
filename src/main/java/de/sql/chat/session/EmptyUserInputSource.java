package de.sql.chat.session;

public class EmptyUserInputSource implements UserInputSource {
  @Override
  public String getUserInput() {
    return "";
  }
}