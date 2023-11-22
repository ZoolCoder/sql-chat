package de.sql.chat.session;

import java.util.Scanner;

/**
 * A class that implements the UserInputSource interface and provides user input using a Scanner instance.
 * 
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class ScannerUserInputSource implements UserInputSource {
  private final Scanner scanner;

  /**
   * Constructs a ScannerUserInputSource with the specified Scanner instance.
   * 
   * @param scanner the Scanner instance to be used for user input
   */
  public ScannerUserInputSource(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Constructs a ScannerUserInputSource with a default Scanner instance using System.in.
   */
  public ScannerUserInputSource() {
    this(new Scanner(System.in));
  }

  /**
   * Retrieves the user input as a String.
   * 
   * @return the user input as a String
   */
  @Override
  public String getUserInput() {
    return scanner.nextLine();
  }
}