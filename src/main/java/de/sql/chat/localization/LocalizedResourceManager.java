package de.sql.chat.localization;

import java.text.MessageFormat;
import java.util.*;

/**
 * The LocalizedResourceManager class is responsible for managing and retrieving localized messages from resource bundles.
 * It provides methods to get messages from a specific resource bundle, format messages with arguments, and manage the current locale.
 *
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class LocalizedResourceManager {
    private Map<LocalizationBundle, ResourceBundle> resourceBundles;
    private Locale currentLocale = Locale.getDefault();
    private static volatile LocalizedResourceManager instance;

     // Private constructor to prevent instantiation
     private LocalizedResourceManager(Locale locale) {
        this.currentLocale = locale;
        this.resourceBundles = new EnumMap<>(LocalizationBundle.class);
        for (LocalizationBundle bundle : LocalizationBundle.values()) {
            resourceBundles.put(bundle, bundle.getResourceBundle(locale));
        }
    }

    public static synchronized LocalizedResourceManager getInstance(Locale locale) {
        if (instance == null) {
            instance = new LocalizedResourceManager(locale);
        }
        return instance;
    }

    public static LocalizedResourceManager getInstance() {
        if (instance == null) {
            instance = new LocalizedResourceManager(Locale.getDefault());
        }
        return instance;
    }

    /**
     * Get a message from the specified resource bundle by key.
     *
     * @param bundle The LocalizationBundle to use.
     * @param key    The key of the message.
     * @return The localized message, or null if not found.
     */
    public String getMessage(LocalizationBundle bundle, String key) {
        ResourceBundle resourceBundle = resourceBundles.get(bundle);
        if (resourceBundle != null && key != null) {
            try {
                return resourceBundle.getString(key);
            } catch (Exception e) {
                // Handle the exception or log an error
            }
        }
        return null;
    }

    /**
     * Get a parameterized message from the specified resource bundle by key and format it with the provided arguments.
     *
     * @param bundle The LocalizationBundle to use.
     * @param key    The key of the message.
     * @param args   The arguments to format the message.
     * @return The formatted message, or null if not found.
     */
    public String getFormattedMessage(LocalizationBundle bundle, String key, Object... args) {
        String pattern = getMessage(bundle, key);
        if (pattern != null) {
            try {
                MessageFormat formatter = new MessageFormat("");
                formatter.applyPattern(pattern);
                formatter.setLocale(currentLocale);
                return formatter.format(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Set the locale for the resource bundle.
     *
     * @param locale The locale to set.
     */
    public void setLocale(Locale locale) {
        currentLocale = locale;
    }

    /**
     * Get the current default locale.
     *
     * @return The current default locale.
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }
}