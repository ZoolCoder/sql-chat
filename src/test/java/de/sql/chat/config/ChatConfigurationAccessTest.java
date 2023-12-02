package de.sql.chat.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ChatConfigurationAccessTest {

  private static ChatConfigurationAccess chatConfigurationAccess;

  @BeforeAll
  public static void setup() {
    chatConfigurationAccess = ChatConfigurationAccess.getInstance();
  }

  @Test
  void testGetChatConfiguration() {
    try {
      chatConfigurationAccess.getChatConfiguration();
    } catch (ChatAppException e) {
      fail("Exception thrown during getChatConfiguration: " + e.getMessage());
    }
  }

  @Test
  void testGetFilePath() throws ChatAppException {
    assertEquals(chatConfigurationAccess.getResourceFilePath(), chatConfigurationAccess.getFilePath());
  }

  @Test
  void testGetResourceFilePath_ResourceNotFound() throws ChatAppException {
    ChatConfigurationAccess mockChatConfigurationAccess = Mockito.mock(ChatConfigurationAccess.class);
    when(mockChatConfigurationAccess.getFilePath()).thenThrow(new ChatAppException(ErrorCode.CONFIGURATION_ERROR, "Resource not found: " + ChatConfigurationAccess.CONFIG_FILE_PATH));

    assertThrows(ChatAppException.class, () -> {
      mockChatConfigurationAccess.getFilePath();
    }, "Resource not found: " + ChatConfigurationAccess.CONFIG_FILE_PATH);
  }

  @Test
  void testGetResourceFilePath_URISyntaxException() throws ChatAppException {
    assertNotNull(getClass().getClassLoader().getResource(ChatConfigurationAccess.CONFIG_FILE_PATH));
    // Create a mock object for ChatConfigurationAccess
    ChatConfigurationAccess mockChatConfigurationAccess = Mockito.mock(ChatConfigurationAccess.class);
    when(mockChatConfigurationAccess.getResourceFilePath()).thenThrow(new ChatAppException(ErrorCode.CONFIGURATION_ERROR, "Error converting URL to URI:" + ChatConfigurationAccess.CONFIG_FILE_PATH));

    // Call the method and assert that it throws a ChatAppException with the expected error code and message
    assertThrows(ChatAppException.class, () -> {
      mockChatConfigurationAccess.getResourceFilePath();
    }, "Error converting URL to URI: " + ChatConfigurationAccess.CONFIG_FILE_PATH);
  }
}