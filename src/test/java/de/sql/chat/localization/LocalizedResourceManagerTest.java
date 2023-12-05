package de.sql.chat.localization;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LocalizedResourceManagerTest {

  @Test
  void shouldReturnNonNullInstance() {
    LocalizedResourceManager manager = LocalizedResourceManager.getInstance();
    assertNotNull(manager);
  }

  @Test
  void shouldReturnCorrectMessageForValidKey() {
    LocalizedResourceManager manager = LocalizedResourceManager.getInstance();
    String message = manager.getMessage(LocalizationBundle.MESSAGES, "server.stopped");
    assertEquals("Server stopped.", message);
  }

  @Test
  void shouldReturnNullForInvalidKey() {
    LocalizedResourceManager manager = LocalizedResourceManager.getInstance();
    String message = manager.getMessage(LocalizationBundle.MESSAGES, "invalidKey");
    assertNull(message);
  }

  @Test
  void shouldReturnFormattedMessageForValidKeyAndArguments() {
    LocalizedResourceManager manager = LocalizedResourceManager.getInstance();
    String message = manager.getFormattedMessage(LocalizationBundle.MESSAGES, "server.started", "localhost", "8080");
    assertEquals("Server started. Your IP: localhost, Port: 8080", message);
  }

  @Test
  void shouldReturnNullForInvalidKeyInFormattedMessage() {
    LocalizedResourceManager manager = LocalizedResourceManager.getInstance();
    String message = manager.getFormattedMessage(LocalizationBundle.MESSAGES, "invalidKey", "arg1", "arg2");
    assertNull(message);
  }

  @Test
  void shouldChangeLocale() {
    LocalizedResourceManager manager = LocalizedResourceManager.getInstance();
    manager.setLocale(Locale.GERMAN);
    assertEquals(Locale.GERMAN, manager.getCurrentLocale());
  }

  @Test
  void shouldReturnCurrentLocale() {
    LocalizedResourceManager manager = LocalizedResourceManager.getInstance();
    assertEquals(Locale.getDefault(), manager.getCurrentLocale());
  }
}