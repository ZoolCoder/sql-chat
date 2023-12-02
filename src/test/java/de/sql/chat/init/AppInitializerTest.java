package de.sql.chat.init;

import static org.junit.jupiter.api.Assertions.fail;

import de.sql.chat.exceptions.ChatAppException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppInitializerTest {

  private static AppInitializer appInitializer;

  @BeforeAll
  public static void setup() {
    appInitializer = AppInitializer.getInstance();
  }

  @Test
  void testInitialize() {
    try {
      appInitializer.initialize();
      // No specific assertions for initialization, assuming no exceptions were thrown
    } catch (ChatAppException e) {
      fail("Exception thrown during initialization: " + e.getMessage());
    }
  }

  @Test
  void testInitLocalization() {
    try {
      appInitializer.initLocalization();
      // Add assertions or verifications here based on the expected behavior of localization
      // For example, check if localization resources are initialized successfully
    } catch (ChatAppException e) {
      fail("Exception thrown during localization initialization: " + e.getMessage());
    }
  }

  @Test
  void testInitLog4j2() {
    try {
      appInitializer.initLog4j2();
      // Add assertions or verifications here based on the expected behavior of log4j2 initialization
    } catch (Exception e) {
      fail("Exception thrown during log4j2 initialization: " + e.getMessage());
    }
  }
}
