package de.sql.chat.configuration;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * The ChatConfiguration class is a Singleton that encapsulates configuration details for the
 * chat application. It includes information about the application name and localization.
 *
 * @since 23-11-2023
 * @author Abdallah Emad
 */
@XmlRootElement(name = "ChatConfiguration")
public class ChatConfiguration {
  private ChatConfigurationApplication application;

  /**
   * Gets the application-specific configuration settings for the chat application.
   *
   * @return The ChatConfigurationApplication instance representing the application settings.
   */
  @XmlElement(name = "Application")
  public ChatConfigurationApplication getApplication() {
    return application;
  }

  /**
   * Sets the application-specific configuration settings for the chat application.
   *
   * @param application The ChatConfigurationApplication instance representing the application settings.
   */
  public void setApplication(ChatConfigurationApplication application) {
    this.application = application;
  }
}