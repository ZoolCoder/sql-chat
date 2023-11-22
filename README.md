# SQL Chat Application

The SQL Chat Application is a robust, Java-based, peer-to-peer chat program that facilitates direct, real-time communication between two instances using TCP socket connections. With its user-friendly command-line interface (CLI), users can enjoy seamless chat interactions.

## Key Features

- **Peer-to-Peer Communication**: Enables direct, real-time chat communication between two instances. This is achieved by using Java's `Socket` class for communication between the client and server.

- **Client-Server Model**: Adopts a client-server model where one instance functions as a server, while the other connects as a client. The server uses a `ServerSocket` to listen for incoming connections and creates a new `Socket` for each client that connects.

- **User-Friendly CLI**: Provides a simple, intuitive command-line interface for effortless sending and receiving of messages. The CLI is implemented using Java's `Scanner` class for user input and `System.out.println` for output.

- **Graceful Termination**: Allows users to exit the application at any time by simply entering "EXIT." This is handled by checking the user's input for the "EXIT" command and closing the `Socket` and `ServerSocket` when it's received.

## Main Class Responsibilities

- **ChatServer**: This class is responsible for listening for incoming connections and creating a new `ChatSession` for each client that connects. It uses a `ServerSocket` to listen for connections.

- **ChatClient**: This class is responsible for connecting to the `ChatServer` and sending and receiving messages. It uses a `Socket` to communicate with the server.

- **ChatSession**: This class represents a chat session between the `ChatServer` and a `ChatClient`. It is responsible for sending and receiving messages between the client and server.

## How to Use

### Server Instance

1. Start the server instance by running the following command:
   `start_server.bat`
   
2. The server will display its IP and port, indicating that it's ready for incoming connections from the client instance.

### Client Instance

1. Initiate the client instance and connect to the server using the following command:
   `start_client.bat`

2. You will be prompted to enter the IP address and port of the server. Once connected, you can start exchanging messages with the server.

## Termination

To terminate the application gracefully at any time, simply enter "EXIT" in the CLI.

## Building and Running

The SQL Chat Application leverages Maven for building. Execute the following command to compile the Java source code, package it into JAR files, and generate the necessary batch scripts for running the server and client:

`mvn clean package`

## Dependencies

The application is built with Java and utilizes the following standard libraries:

- `java.net` for socket communication. This includes classes like `Socket` and `ServerSocket` for TCP communication.
- `java.io` for handling input and output streams. This includes classes like `InputStream` and `OutputStream` for reading from and writing to the socket, and `Scanner` and `PrintStream` for user input and output.
- `java.util` for managing user input. This includes the `Scanner` class for reading user input.