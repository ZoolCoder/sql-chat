package de.sql.chat.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The LocalizationBundle enum represents the different localization bundles available in the application.
 * 
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public enum LocalizationBundle {
    MESSAGES("messages" ),
    ERROR("error" );

    private final String bundleName;

    /**
     * Constructs a LocalizationBundle with the specified bundle name.
     *
     * @param bundleName the name of the bundle
     */
    private LocalizationBundle(String bundleName) {
        this.bundleName = bundleName;
    }

    /**
     * Returns the name of the bundle.
     *
     * @return the name of the bundle
     */
    public String getBundleName() {
        return bundleName;
    }

    /**
     * Returns the ResourceBundle for the specified locale.
     *
     * @param locale the locale for which to retrieve the ResourceBundle
     * @return the ResourceBundle for the specified locale
     */
    public ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle(bundleName, locale);
    }

    /**
     * Returns the LocalizationBundle enum constant that corresponds to the specified bundle name.
     *
     * @param bundleName the name of the bundle
     * @return the LocalizationBundle enum constant that corresponds to the specified bundle name
     * @throws IllegalArgumentException if the specified bundle name is invalid
     */
    public static LocalizationBundle valueOfBundleName(String bundleName) {
        for (LocalizationBundle bundle : LocalizationBundle.values()) {
            if (bundle.getBundleName().equals(bundleName)) {
                return bundle;
            }
        }
        throw new IllegalArgumentException("Invalid bundle name: " + bundleName);
    }

    /**
     * Returns the string representation of the bundle name.
     *
     * @return the string representation of the bundle name
     */
    @Override
    public String toString() {
        return bundleName;
    }
}