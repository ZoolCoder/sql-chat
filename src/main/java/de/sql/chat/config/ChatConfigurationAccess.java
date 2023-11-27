package de.sql.chat.config;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;
import de.sql.chat.util.XmlToObjectUtil;
import jakarta.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The ChatConfigurationAccess class is responsible for managing the application's configuration,
 * specifically for the chat functionality. It follows the singleton pattern to ensure a single
 * instance throughout the application.
 *
 * @since 23-11-2023
 * @author Abdallah Emad
 */
public class ChatConfigurationAccess {
  private static final Logger LOGGER = LogManager.getLogger(ChatConfigurationAccess.class);
  private ChatConfiguration chatConfiguration;

  /**
   * Private constructor to enforce the singleton pattern.
   */
  private ChatConfigurationAccess() {
    // Private constructor to enforce singleton pattern
  }

  private static class SingletonHolder {
    private static final ChatConfigurationAccess INSTANCE = new ChatConfigurationAccess();
  }

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
        String xmlBody = Files.readString(Paths.get(System.getProperty("user.dir") + "/config/configuration-chat.xml"));
        chatConfiguration = new XmlToObjectUtil<>(xmlBody, ChatConfiguration.class).getTargetObject();
      } catch (IOException | JAXBException e) {
        LOGGER.error("Error during Initializing configuration", e);
        throw new ChatAppException(ErrorCode.CONFIGURATION_ERROR, "configuration Error: " + e.getMessage());
      }
    }
    return chatConfiguration;
  }
}