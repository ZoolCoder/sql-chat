package de.sql.chat.session;

/**
 * A UserInputSource implementation that represents an empty user input.
 * This class is used for testing purposes.
 * 
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class EmptyUserInputSource implements UserInputSource {

  /**
   * Retrieves the user input.
   * @return An empty string representing the user input.
   */
  @Override
  public String getUserInput() {
    return "";
  }
  
}