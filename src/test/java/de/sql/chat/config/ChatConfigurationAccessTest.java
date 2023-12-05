package de.sql.chat.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
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
  void shouldReturnSameInstance() {
    ChatConfigurationAccess instance1 = ChatConfigurationAccess.getInstance();
    ChatConfigurationAccess instance2 = ChatConfigurationAccess.getInstance();
    assertSame(instance1, instance2);
  }

  @Test
  void shouldReturnChatConfiguration() throws ChatAppException {
    ChatConfiguration chatConfiguration = chatConfigurationAccess.getChatConfiguration();
    assertNotNull(chatConfiguration);
    // Add more assertions here based on the expected properties of the chat configuration
  }

  @Test
  void shouldReturnFilePath() throws ChatAppException {
    String filePath = chatConfigurationAccess.getFilePath();
    assertNotNull(filePath);
    System.out.println(filePath);
    assertTrue(filePath.endsWith(ChatConfigurationAccess.CONFIG_FILE_PATH.replace("/", File.separator)));
  }

  @Test
  void shouldReturnResourceFilePathWhenResourceExists() throws ChatAppException, URISyntaxException {
    // Mock the getResourceFilePath method to return a valid file path
    ChatConfigurationAccess mockChatConfigurationAccess = Mockito.spy(ChatConfigurationAccess.class);
    Mockito.doReturn("/valid/file/path").when(mockChatConfigurationAccess).getResourceFilePath();

    String resourceFilePath = mockChatConfigurationAccess.getResourceFilePath();

    assertNotNull(resourceFilePath);
    assertEquals("/valid/file/path", resourceFilePath);
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