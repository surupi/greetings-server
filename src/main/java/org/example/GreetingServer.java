package org.example;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
@AllArgsConstructor
public class GreetingServer {
    private int port;
    private int timeout;

    // Method to start the greeting server
    void start() {
        // Log the server's starting configuration
        log.info("Starting server on port {} with timeout {}", port, timeout);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
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
