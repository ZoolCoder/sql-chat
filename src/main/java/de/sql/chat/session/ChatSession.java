package de.sql.chat.session;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class ChatSession implements AutoCloseable {
    private final Socket socket;
    private final ChatSenderType sender;
    private final PrintWriter out;
    private final BufferedReader in;
    private final UserInputSource userInputSource;
    private volatile boolean exitRequested = false;
    private List<String> userMessages = new ArrayList<>();
    public ChatSession(ChatSenderType sender, Socket socket, UserInputSource userInputSource) throws ChatAppException {
        this.socket = socket;
        this.sender = sender;
        this.userInputSource = userInputSource;

        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new ChatAppException(
                ErrorCode.SESSION_ERROR, "Error creating session: " + e.getMessage());
        }
    }

    public void start() throws ChatAppException {
        Thread userInputThread = new Thread(this::readUserInput);
        userInputThread.start();

        try {
            while (!exitRequested) {
                printReceivedMessages();

                // Sleep for a short duration to avoid busy-waiting
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            userInputThread.interrupt();
        }
    }

    private void readUserInput() {
        try {
            while (!exitRequested) {
                String userInput = userInputSource.getUserInput();

                if (!userInput.isEmpty()) {
                    sendMessage(userInput);
                }

                // Sleep for a short duration to avoid busy-waiting
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printReceivedMessages() {
        try {
            while (!exitRequested && in.ready()) {
                String message = in.readLine();
                if (!message.isEmpty()) {
                    if (isExitCommand(message)) {
                        exitRequested = true;
                    } else {
                        printMessage(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (isExitCommand(message)) {
            exitRequested = true;
        } else {
            out.println(sender + ": " + message);
            out.flush();
            userMessages.add(sender + ": " + message);
        }
    }

    private void printMessage(String message) {
        System.out.println(message);
    }

    private boolean isExitCommand(String message) {
        return message.trim().equalsIgnoreCase("EXIT");
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    public List<String> getUserMessages() {
        return userMessages;
    }
}