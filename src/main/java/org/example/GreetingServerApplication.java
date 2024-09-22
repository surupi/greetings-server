package org.example;

import com.beust.jcommander.JCommander; // Library for command-line argument parsing
import com.beust.jcommander.Parameter; // Annotation for defining command-line parameters
import lombok.extern.slf4j.Slf4j; // Lombok annotation for logging

@Slf4j // Enables logging in this class
public class GreetingServerApplication {

    // Command-line parameter for the port to listen on, with a default value of 6666
    @Parameter(names = {"--port", "-p"}, description = "the port to listen on")
    private int port = 6666;

    // Command-line parameter for the idle timeout value for each connection, default is 45 seconds
    @Parameter(names = {"--timeout", "-t"}, description = "idle timeout value for each connection.")
    private int timeout = 45;

    // Main method, entry point of the application
    public static void main(String[] args) {
        // Create an instance of the application
        GreetingServerApplication app = new GreetingServerApplication();

        // Create a JCommander instance for parsing command-line arguments
        JCommander commander = JCommander.newBuilder()
                .addObject(app) // Add the application instance to the commander
                .build();

        // Parse the command-line arguments
        commander.parse(args);

        // Run the application logic
        app.run();
    }

    // Method to start the greeting server
    private void run() {
        // Uncomment the following line to log server start information
        // log.info("Starting server on port {} with timeout {}", port, timeout);

        // Create a new GreetingServer instance with the specified port and timeout
        GreetingServer greetingServer = new GreetingServer(port, timeout);

        // Start the greeting server
        greetingServer.start();
    }
}
