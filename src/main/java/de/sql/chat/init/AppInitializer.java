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
    private boolean useResourcePath = false;
    /**
     * Private constructor to enforce the singleton pattern.
     */
    private AppInitializer() {
      // Private constructor to enforce singleton pattern
    }

    private static class SingletonHolder {
      private static final AppInitializer INSTANCE = new AppInitializer();
    }

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

      if (this.useResourcePath) {
        ChatConfigurationAccess.getInstance().enableResourcePath();
      }

      initLocalization();
      LOGGER.info("Application initialization completed.");
    }

  protected void initLocalization() throws ChatAppException {
    LOGGER.info("Initializing localization resources.");
    Locale locale = new Locale(ChatConfigurationAccess.getInstance().getChatConfiguration().getApplication().getLocal());
    LocalizedResourceManager.getInstance(locale);
    LOGGER.info("localization configuration initialized.");
  }
  protected void initLog4j2() {
    Path configPath = Paths.get(System.getProperty("user.dir") + "/config/log4j2.xml");
    System.setProperty("log4j.configurationFile", configPath.toUri().toString());

    // Force log4j2 to reconfigure using the specified configuration file
    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    context.reconfigure();
    LOGGER.info("log4j2 configuration initialized.");
  }

  protected void enableResourcePath() {
    this.useResourcePath = true;
  }
}