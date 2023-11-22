package de.sql.chat.exceptions;

import de.sql.chat.localization.LocalizationBundle;
import de.sql.chat.localization.LocalizedResourceManager;

/**
 * Custom exception class for handling application-specific errors.
 *
 * This class provides the capability to handle application-specific errors using error codes and localized error messages.
 *
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class ChatAppException extends Exception {
  private final ErrorCode errorCode;

  /**
   * Creates a ChatAppException with the specified error code.
   *
   * @param errorCode The error code representing the specific error condition.
   */
  public ChatAppException(ErrorCode errorCode) {
    super(LocalizedResourceManager.getInstance().getMessage(LocalizationBundle.ERROR, errorCode.getKey()));
    this.errorCode = errorCode;
  }

  /**
   * Creates a ChatAppException with the specified error code and a custom error message.
   *
   * @param errorCode The error code representing the specific error condition.
   * @param customMessage A custom error message to be used if the localization for the error code is missing.
   */
  public ChatAppException(ErrorCode errorCode, String customMessage) {
    super(customMessage != null ? customMessage : LocalizedResourceManager.getInstance().getMessage(LocalizationBundle.ERROR, errorCode.getKey()));
    this.errorCode = errorCode;
  }

  /**
   * Gets the error code associated with this exception.
   *
   * @return The error code that represents the specific error condition.
   */
  public ErrorCode getErrorCode() {
    return errorCode;
  }
}