package de.sql.chat.init;

import java.util.Locale;
import de.sql.chat.localization.LocalizedResourceManager;

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
     * Initialize the application components.
     */
    public void initialize() {
      LocalizedResourceManager.getInstance(new Locale("en"));
    }

}