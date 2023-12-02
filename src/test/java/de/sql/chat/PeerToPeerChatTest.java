package de.sql.chat;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import de.sql.chat.exceptions.ChatAppException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PeerToPeerChatTest {
  private static PeerToPeerChat spyPeerToPeerChat;

  @BeforeAll
  public static void setup() {
    spyPeerToPeerChat = spy(new PeerToPeerChat());
  }

  @Test
  void testMainMethodWithOtherInstanceArgs() throws ChatAppException {
    String[] args = {"--other_instance", "127.0.0.1:8080"};
    spyPeerToPeerChat.run(args);
    verify(spyPeerToPeerChat, times(1)).startClient(anyString(), anyInt());
  }

  @Test
  void testMainMethodWithoutOtherInstanceArgs() throws ChatAppException {
    doNothing().when(spyPeerToPeerChat).startServer();
    String[] args = {};
    spyPeerToPeerChat.run(args);
    verify(spyPeerToPeerChat, times(1)).startServer();
  }
}