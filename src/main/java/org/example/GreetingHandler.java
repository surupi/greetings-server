package org.example;

import org.example.util.ParseUtil; // Importing utility class to parse input

import java.io.BufferedReader; // Class for reading text from an input stream
import java.io.IOException; // Exception handling for I/O operations
import java.io.InputStreamReader; // Converts InputStream to Reader
import java.io.PrintWriter; // Class for writing text to an output stream
import java.net.Socket; // Class for handling client sockets

// Class implementing Runnable to handle client connections
public class GreetingHandler implements Runnable {
    private Socket clientSocket; // Socket to handle client connection
    private int timeout; // Timeout value for client connection in seconds

    // Constructor to initialize the client socket and timeout
    public GreetingHandler(Socket clientSocket, int timeout) {
        this.clientSocket = clientSocket;
        this.timeout = timeout;
    }

    // The method that runs when the thread is started
    @Override
    public void run() {
        try (
                // Input reader for reading client input from the socket
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // Output writer for sending responses back to the client
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            // Set the socket timeout (in milliseconds)
            clientSocket.setSoTimeout(timeout * 1000);

            // Send a response indicating the server is ready
            out.println("200 server ready");

            // Variables to store the client-provided name and location
            String line;
            String name = "";
            String location = "";

            // Loop to continuously read client input
            while ((line = in.readLine()) != null) {
                // Parse the client input using ParseUtil
                GreetingCommand cmd = ParseUtil.parseInput(line);

                // Handle the command based on the parsed input
                switch (cmd.getName().toUpperCase()) {

                    case "NAME":
                        name = String.join(" ", cmd.getArguments());
                        out.println("201 NAME ok");
                        break;

                    case "LOCATION":
                        location = String.join(" ", cmd.getArguments());
                        out.println("201 LOCATION ok");
                        break;

                    case "GREET":
                        out.printf("Hello %s of %s%n", name, location);
                        break;

                    case "QUIT":
                        out.println("202 Bye");
                        return;

                    default:
                        out.println("400 Bad Request");
                        break;
                }
            }

        } catch (IOException e) {
            // Handle any I/O exceptions by wrapping them in a runtime exception
            throw new RuntimeException(e);
        }
    }

}
