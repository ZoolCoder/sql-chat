package de.sql.chat.session;

import java.io.IOException;

/**
 * The UserInputSource interface represents a source of user input.
 * Implementations of this interface provide a way to retrieve user input.
 * 
 * @author Abdallah Emad
 * @since 8-11-2023
 */
public interface UserInputSource {

  /**
   * Retrieves user input.
   *
   * @return the user input as a String.
   * @throws IOException if an I/O error occurs while retrieving the user input.
   */
  String getUserInput() throws IOException;
  
}