package de.sql.chat.configuration;

import jakarta.xml.bind.annotation.XmlElement;

/**
 * The ChatConfigurationApplication class represents the application-specific configuration
 * settings related to the chat application. It is used as part of the overall ChatConfiguration.
 *
 * @since 23-11-2023
 * @author Abdallah Emad
 */
public class ChatConfigurationApplication {
  private String name;
  private String local;

  /**
   * Gets the name of the chat application.
   *
   * @return The name of the chat application.
   */
  @XmlElement(name = "Name")
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the chat application.
   *
   * @param name The name of the chat application.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the local configuration setting for the chat application.
   *
   * @return The local configuration setting for the chat application.
   */
  @XmlElement(name = "Local")
  public String getLocal() {
    return local;
  }

  /**
   * Sets the local configuration setting for the chat application.
   *
   * @param local The local configuration setting for the chat application.
   */
  public void setLocal(String local) {
    this.local = local;
  }
}