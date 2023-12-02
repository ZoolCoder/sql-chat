package de.sql.chat.config;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;
import de.sql.chat.util.XmlToObjectUtil;
import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The ChatConfigurationAccess class is responsible for managing the application's configuration,
 * specifically for the chat functionality. It follows the singleton pattern to ensure a single
 * instance throughout the application.
 *
 * @since 23-11-2023
 * @author Abdallah Emad
 */
public class ChatConfigurationAccess {

  private ChatConfiguration chatConfiguration;
  protected static final String CONFIG_FILE_PATH = "config/configuration-chat.xml";

  /**
   * Private constructor to enforce the singleton pattern.
   */
  private ChatConfigurationAccess() {
    // Private constructor to enforce singleton pattern
  }

  /**
   * A holder class for the singleton instance of {@link ChatConfigurationAccess}.
   * This class ensures lazy initialization of the singleton instance.
   */
  private static class SingletonHolder {
    private static final ChatConfigurationAccess INSTANCE = new ChatConfigurationAccess();
  }

  /**
   * This class provides access to the chat configuration.
   */
  public static ChatConfigurationAccess getInstance() {
    return SingletonHolder.INSTANCE;
  }

  /**
   * Gets the chat configuration. If the configuration has not been loaded, it triggers the
   * loading process.
   *
   * @return The ChatConfiguration instance.
   */
  public ChatConfiguration getChatConfiguration() throws ChatAppException {
    if (chatConfiguration == null) {
      try {
        String xmlBody = Files.readString(Paths.get(getFilePath()));
        chatConfiguration = new XmlToObjectUtil<>(xmlBody, ChatConfiguration.class).getTargetObject();
      } catch (IOException | JAXBException e) {
        throw new ChatAppException(ErrorCode.CONFIGURATION_ERROR, "configuration Error: " + e.getMessage());
      }
    }
    return chatConfiguration;
  }

  /**
   * Retrieves the file path for the chat configuration.
   *
   * @return The file path as a {@link String}.
   * @throws ChatAppException
   */
  protected String getFilePath() throws ChatAppException {
    String filePath = System.getProperty("user.dir") + File.separator + CONFIG_FILE_PATH;

    return Files.exists(Paths.get(filePath)) ? filePath : getResourceFilePath();
  }

  /**
   * Retrieves the file path of the resource.
   *
   * @return The file path of the resource.
   * @throws ChatAppException
   * @throws RuntimeException If the resource is not found or if there is an error converting the URL to URI.
   */
  protected String getResourceFilePath() throws ChatAppException {
    URL resourceUrl = getClass().getClassLoader().getResource(CONFIG_FILE_PATH);
    if (resourceUrl != null) {
      try {
        return Paths.get(resourceUrl.toURI()).toString();
      } catch (URISyntaxException e) {
        throw new ChatAppException(ErrorCode.CONFIGURATION_ERROR, "Error converting URL to URI: " + e.getMessage());
      }
    } else {
      throw new ChatAppException(ErrorCode.CONFIGURATION_ERROR, "Resource not found: " + CONFIG_FILE_PATH);
    }
  }
}