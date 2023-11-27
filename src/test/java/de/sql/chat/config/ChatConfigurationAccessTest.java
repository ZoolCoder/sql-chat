package de.sql.chat.config;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.sql.chat.exceptions.ChatAppException;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ChatConfigurationAccessTest {

  private static ChatConfigurationAccess chatConfigurationAccess;

  @BeforeAll
  public static void setup() {
    chatConfigurationAccess = ChatConfigurationAccess.getInstance();
  }

  @Test
  void testGetChatConfiguration() {
    // Use try-catch block to hide the exception
    try {
      chatConfigurationAccess.getChatConfiguration();
      fail("Expected ChatAppException but it was not thrown");
    } catch (ChatAppException ignored) {
      // Exception caught, do nothing
    }
  }

  @Test
  void testEnableResourcePath() {
    chatConfigurationAccess.enableResourcePath();
    String filePath = chatConfigurationAccess.getFilePath();
    String expectedPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "config" + File.separator + "configuration-chat.xml";

    assertEquals(expectedPath, filePath);
  }

  @Test
  void testGetFilePath() {
    chatConfigurationAccess.enableResourcePath();
    String filePath = chatConfigurationAccess.getFilePath();
    String expectedPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "config" + File.separator + "configuration-chat.xml";
    assertEquals(expectedPath, filePath);

    // Optionally, you can test the disableResourcePath method
    // chatConfigurationAccess.disableResourcePath();
    // filePath = chatConfigurationAccess.getFilePath();
    // assertEquals(System.getProperty("user.dir") + File.separator + "config" + File.separator + "configuration-chat.xml", filePath);
  }
}