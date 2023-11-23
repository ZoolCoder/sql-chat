package de.sql.chat.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.StringReader;
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
  private static final Logger LOGGER = LogManager.getLogger(ChatConfigurationAccess.class);
  private static volatile ChatConfigurationAccess instance;
  private ChatConfiguration chatConfiguration;

  /**
   * Private constructor to enforce the singleton pattern.
   */
  private ChatConfigurationAccess() {
    // Private constructor to enforce singleton pattern
  }

  /**
   * Retrieves the singleton instance of the ChatConfigurationAccess.
   *
   * @return The ChatConfigurationAccess instance.
   */
  public static ChatConfigurationAccess getInstance() {
    if (instance == null) {
      synchronized (ChatConfigurationAccess.class) {
        if (instance == null) {
          instance = new ChatConfigurationAccess();
        }
      }
    }
    return instance;
  }

  /**
   * Gets the chat configuration. If the configuration has not been loaded, it triggers the
   * loading process.
   *
   * @return The ChatConfiguration instance.
   */
  public ChatConfiguration getChatConfiguration() {
    if (chatConfiguration == null) {
      loadConfiguration();
    }
    return chatConfiguration;
  }

  /**
   * Loads the chat configuration from an XML file.
   */
  private void loadConfiguration() {
    try {
      String xmlBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/config/configuration-chat.xml")), "UTF-8");
      // Create JAXB context and unmarshaller
      JAXBContext jaxbContext = JAXBContext.newInstance(ChatConfiguration.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

      // Unmarshal XML into Java object
      try (StringReader reader = new StringReader(xmlBody)) {
        chatConfiguration = (ChatConfiguration) unmarshaller.unmarshal(reader);
        LOGGER.info("Unmarshalled ChatConfiguration: " + chatConfiguration);
      } catch (JAXBException e) {
        LOGGER.error("Error unmarshalling configuration", e);
      }
    } catch (JAXBException e) {
      LOGGER.error("Error creating JAXB context", e);
    } catch (IOException e) {
      LOGGER.error("Error reading configuration file", e);
    }
  }

}