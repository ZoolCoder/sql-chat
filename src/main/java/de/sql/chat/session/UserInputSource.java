package de.sql.chat.session;

import java.io.IOException;
public interface UserInputSource {
  String getUserInput() throws IOException;
}