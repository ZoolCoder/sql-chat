package de.sql.chat.init;

import de.sql.chat.config.ChatConfigurationAccess;
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
    private static AppInitializer instance;

    private AppInitializer() {
        // Private constructor to prevent direct instantiation
    }

    /**
     * Retrieve the singleton instance of the application initializer.
     *
     * @return The singleton instance of AppInitializer
     */
    public static AppInitializer getInstance() {
      if (instance == null) {
        instance = new AppInitializer();
      }
      return instance;
    }

    /**
     * Initializes the application by setting up the necessary components and resources.
     * This method should be called once at the start of the application.
     */
    public void initialize() {
      // Initialize log4j2 configuration first to allow logging of other initialization steps
      initLog4j2();
      LOGGER.info("Starting application initialization...");
      initLocalization();
      LOGGER.info("Application initialization completed.");
    }

  private void initLocalization() {
    LOGGER.info("Initializing localization resources.");
    LocalizedResourceManager.getInstance(new Locale(
        ChatConfigurationAccess.getInstance().getChatConfiguration().getApplication().getLocal()));
    LOGGER.info("localization configuration initialized.");
  }
  private void initLog4j2() {
    Path configPath = Paths.get(System.getProperty("user.dir") + "/config/log4j2.xml");
    System.setProperty("log4j.configurationFile", configPath.toUri().toString());

    // Force log4j2 to reconfigure using the specified configuration file
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    context.reconfigure();
    LOGGER.info("log4j2 configuration initialized.");
  }

}