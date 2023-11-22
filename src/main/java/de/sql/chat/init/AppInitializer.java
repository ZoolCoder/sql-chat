package de.sql.chat.init;

import java.util.Locale;
import de.sql.chat.localization.LocalizedResourceManager;

/**
 * Represents the application initializer.
 * 
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class AppInitializer {
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
      LocalizedResourceManager.getInstance(new Locale("en"));
    }

}