package de.sql.chat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import de.sql.chat.exceptions.ChatAppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PeerToPeerChatTest {
  private PeerToPeerChat spyPeerToPeerChat;

  @BeforeEach
  public void setup() {
    spyPeerToPeerChat = spy(new PeerToPeerChat());
  }

  @Test
  void shouldStartServerWhenNoArgumentsProvided() throws ChatAppException {
    doNothing().when(spyPeerToPeerChat).startServer();
    String[] args = {};
    spyPeerToPeerChat.run(args);
    verify(spyPeerToPeerChat, times(1)).startServer();
  }
  @Test
  void shouldStartClientWhenOtherInstanceArgumentsAreProvided() throws ChatAppException {
    String[] args = {"--other_instance", "127.0.0.1:8080"};
    spyPeerToPeerChat.run(args);
    verify(spyPeerToPeerChat, times(1)).startClient(anyString(), anyInt());
  }

  @Test
  void shouldHandleNullArguments() throws ChatAppException {
    String[] args = null;
    doNothing().when(spyPeerToPeerChat).startServer();
    spyPeerToPeerChat.run(args);
    verify(spyPeerToPeerChat, times(1)).startServer();
  }

  @Test
  void shouldHandleEmptyArguments() throws ChatAppException {
    String[] args = {};
    doNothing().when(spyPeerToPeerChat).startServer();
    spyPeerToPeerChat.run(args);
    verify(spyPeerToPeerChat, times(1)).startServer();
  }

  @Test
  void shouldHandleStartServerFailure() throws ChatAppException {
    doThrow(ChatAppException.class).when(spyPeerToPeerChat).startServer();
    String[] args = {};
    assertThrows(ChatAppException.class, () -> spyPeerToPeerChat.run(args));
  }

  @Test
  void shouldHandleStartClientFailure() throws ChatAppException {
    doThrow(ChatAppException.class).when(spyPeerToPeerChat).startClient(anyString(), anyInt());
    String[] args = {"--other_instance", "127.0.0.1:8080"};
    assertThrows(ChatAppException.class, () -> spyPeerToPeerChat.run(args));
  }
}