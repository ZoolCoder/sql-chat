package de.sql.chat.session;

import de.sql.chat.exceptions.ChatAppException;
import de.sql.chat.exceptions.ErrorCode;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chat session between a client and a server.
 * Implements the AutoCloseable interface to allow for automatic resource management.
 * 
 * @since 8-11-2023
 * @author Abdallah Emad
 */
public class ChatSession implements AutoCloseable {
    private final Socket socket;
    private final ChatSenderType sender;
    private final PrintWriter out;
    private final BufferedReader in;
    private final UserInputSource userInputSource;
    private volatile boolean exitRequested = false;
    private List<String> userMessages = new ArrayList<>();

    /**
     * Constructs a ChatSession object.
     *
     * @param sender           the type of chat sender (client or server)
     * @param socket           the socket associated with the chat session
     * @param userInputSource the source of user input
     * @throws ChatAppException if there is an error creating the session
     */
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

    /**
     * Starts the chat session.
     *
     * @throws ChatAppException if there is an error during the chat session
     */
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

    /**
     * Reads user input and sends messages based on the input.
     */
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

    /**
     * Prints the received messages from the input stream.
     * Continuously reads messages from the input stream until an exit command is received or the input stream is closed.
     * If a non-empty message is received and it is not an exit command, the message is printed.
     * If an IOException occurs while reading from the input stream, the exception is printed.
     */
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

    /**
     * Sends a message to the chat session.
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        if (isExitCommand(message)) {
            exitRequested = true;
        } else {
            out.println(sender + ": " + message);
            out.flush();
            userMessages.add(sender + ": " + message);
        }
    }

    /**
     * Prints a message to the console.
     *
     * @param message the message to be printed
     */
    private void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Checks if the given message is an exit command.
     * An exit command is considered when the message is trimmed and case-insensitively equals "EXIT".
     *
     * @param message the message to check
     * @return true if the message is an exit command, false otherwise
     */
    private boolean isExitCommand(String message) {
        return message.trim().equalsIgnoreCase("EXIT");
    }

    /**
     * Closes the chat session and releases any associated resources.
     *
     * @throws IOException if an I/O error occurs while closing the session
     */
    @Override
    public void close() throws IOException {
        socket.close();
    }

    /**
     * Returns the list of user messages sent during the chat session.
     *
     * @return the list of user messages
     */
    public List<String> getUserMessages() {
        return userMessages;
    }
}