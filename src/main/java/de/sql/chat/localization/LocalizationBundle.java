package de.sql.chat.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The LocalizationBundle enum represents different bundles of localized messages and error messages.
 * Each bundle has a unique name and corresponds to a resource bundle file.
 */
public enum LocalizationBundle {
    MESSAGES("messages" ),
    ERROR("error" );

    private final String bundleName;

    private LocalizationBundle(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getBundleName() {
        return bundleName;
    }

    public ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle(bundleName, locale);
    }

    public static LocalizationBundle valueOfBundleName(String bundleName) {
        for (LocalizationBundle bundle : LocalizationBundle.values()) {
            if (bundle.getBundleName().equals(bundleName)) {
                return bundle;
            }
        }
        throw new IllegalArgumentException("Invalid bundle name: " + bundleName);
    }

    @Override
    public String toString() {
        return bundleName;
    }
}