package de.sql.chat.localization;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationBundleTest {

  @Test
  void shouldReturnCorrectBundleName() {
    assertEquals("messages", LocalizationBundle.MESSAGES.getBundleName());
    assertEquals("error", LocalizationBundle.ERROR.getBundleName());
  }

  @Test
  void shouldReturnNonNullResourceBundle() {
    assertNotNull(LocalizationBundle.MESSAGES.getResourceBundle(Locale.getDefault()));
    assertNotNull(LocalizationBundle.ERROR.getResourceBundle(Locale.getDefault()));
  }

  @Test
  void shouldReturnCorrectLocalizationBundleForValidBundleName() {
    assertEquals(LocalizationBundle.MESSAGES, LocalizationBundle.valueOfBundleName("messages"));
    assertEquals(LocalizationBundle.ERROR, LocalizationBundle.valueOfBundleName("error"));
  }

  @Test
  void shouldThrowExceptionForInvalidBundleName() {
    assertThrows(IllegalArgumentException.class, () -> LocalizationBundle.valueOfBundleName("invalid"));
  }

  @Test
  void shouldReturnCorrectStringRepresentation() {
    assertEquals("messages", LocalizationBundle.MESSAGES.toString());
    assertEquals("error", LocalizationBundle.ERROR.toString());
  }
}