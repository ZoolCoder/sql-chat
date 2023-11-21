package de.sql.chat.session;

import java.util.Scanner;

public class ScannerUserInputSource implements UserInputSource {
  private final Scanner scanner;

  // Constructor taking a Scanner instance
  public ScannerUserInputSource(Scanner scanner) {
    this.scanner = scanner;
  }

  // Default constructor for creating ScannerUserInputSource without a Scanner instance
  public ScannerUserInputSource() {
    this(new Scanner(System.in));
  }

  @Override
  public String getUserInput() {
    return scanner.nextLine();
  }
}