package org.example;

import lombok.AllArgsConstructor; // Lombok annotation for generating a constructor with parameters
import lombok.extern.slf4j.Slf4j; // Lombok annotation for logging

import java.io.IOException; // Exception handling for I/O operations
import java.net.ServerSocket; // Class for creating a server socket
import java.net.Socket; // Class for handling client sockets

@Slf4j // Enables logging in this class
@AllArgsConstructor // Generates a constructor that takes parameters for all fields
public class GreetingServer {
    private int port; // Port on which the server listens for incoming connections
    private int timeout; // Idle timeout value for client connections

    // Method to start the greeting server
    void start() {
        // Log the server's starting configuration
        log.info("Starting server on port {} with timeout {}", port, timeout);

        try (ServerSocket serverSocket = new ServerSocket(port)) { // Create a server socket
            // Loop indefinitely to accept client connections
            while (true) {
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();
                // Log the remote address of the connected client
                log.info("New connection from {}", clientSocket.getRemoteSocketAddress());

                // Create a new thread to handle the client's request
                Thread thread = new Thread(new GreetingHandler(clientSocket, timeout));
                thread.start(); // Start the new thread to handle the connection
            }
        } catch (IOException e) {
            // Handle exceptions related to I/O operations
            throw new RuntimeException(e);
        }
    }
}
