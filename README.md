# SQL Chat Application

The SQL Chat Application is a peer-to-peer chat program that enables direct communication between two instances using plain TCP socket connections. This Java-based application offers a user-friendly command-line interface (CLI) for seamless chat interactions.

## Features

- **Peer-to-Peer Communication**: Establish direct connections between two instances to enable real-time chat communication.

- **Client-Server Model**: One instance functions as a server, while the other instance connects as a client.

- **User-Friendly CLI**: A simple and intuitive command-line interface for sending and receiving messages.

- **Graceful Termination**: Easily exit the application at any time by entering "EXIT."

## Usage

### Server Instance

Start the server instance by running the following command:
   start_server.bat
   
The server will display its IP and port, awaiting incoming connections from the client instance.

### Client Instance
To initiate the client instance and connect to the server, use the following command:

start_client.bat

You will be prompted to enter the IP address of the server and the port. Once connected, you can exchange messages with the server.

## Termination
Terminate the application gracefully at any time by entering "EXIT" in the CLI.

## Building and Running
To build the SQL Chat Application, you can leverage Maven. Execute the following command to compile the Java source code, package it into JAR files, and create the necessary batch scripts for running the server and client:

mvn clean package

## Dependencies
The application relies on Java and the following standard libraries:

- java.net for socket communication.
- java.io for input and output streams.
- java.util for handling user input.
