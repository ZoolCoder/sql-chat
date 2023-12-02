package de.sql.chat.init;

import de.sql.chat.config.ChatConfigurationAccess;
import de.sql.chat.exceptions.ChatAppException;
import java.util.Locale;
import de.sql.chat.localization.LocalizedResourceManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the application initializer.
 * 
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class AppInitializer {

  private static final Logger LOGGER = LogManager.getLogger(AppInitializer.class);
  
  /**
   * Private constructor to enforce the singleton pattern.
   */
  private AppInitializer() {
    // Private constructor to enforce singleton pattern
  }

  /**
    * A holder class for the singleton instance of AppInitializer.
    */
  private static class SingletonHolder {
    private static final AppInitializer INSTANCE = new AppInitializer();
  }

  /**
   * Returns the instance of the AppInitializer class.
   *
   * @return The instance of the AppInitializer class.
   */
  public static AppInitializer getInstance() {
    return AppInitializer.SingletonHolder.INSTANCE;
  }

  /**
   * Initializes the application by setting up the necessary components and resources.
   * This method should be called once at the start of the application.
   */
  public void initialize() throws ChatAppException {
    // Initialize log4j2 configuration first to allow logging of other initialization steps
    initLog4j2();
    LOGGER.info("Starting application initialization...");

    initLocalization();
    LOGGER.info("Application initialization completed.");
  }

  /**
   * Initializes the localization resources for the chat application.
   * @throws ChatAppException if an error occurs during initialization.
   */
  protected void initLocalization() throws ChatAppException {
    LOGGER.info("Initializing localization resources.");
    Locale locale = new Locale(ChatConfigurationAccess.getInstance().getChatConfiguration().getApplication().getLocal());
    LocalizedResourceManager.getInstance(locale);
    LOGGER.info("localization configuration initialized.");
  }

  /**
   * Initializes the log4j2 configuration by setting the log4j2 configuration file path and reconfiguring the logger context.
   */
  protected void initLog4j2() {
    Path configPath = Paths.get(System.getProperty("user.dir") + "/config/log4j2.xml");
    System.setProperty("log4j.configurationFile", configPath.toUri().toString());

    // Force log4j2 to reconfigure using the specified configuration file
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    context.reconfigure();
    LOGGER.info("log4j2 configuration initialized.");
  }
}