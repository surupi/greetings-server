package org.example;

import org.example.util.ParseUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// Class implementing Runnable to handle client connections
public class GreetingHandler implements Runnable {
    private Socket clientSocket;
    private int timeout;

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
            out.println("200 server ready");
            String line;
            String name = "";
            String location = "";

            // Loop to continuously read client input
            while ((line = in.readLine()) != null) {
                // Parse the client input using ParseUtil
                GreetingCommand cmd = ParseUtil.parseInput(line);

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
                        if (name.isEmpty() || location.isEmpty()) {
                            out.println("400 Bad Request");
                        }
                        else {
                            out.printf("Hello %s of %s%n", name, location);
                        }
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
            throw new RuntimeException(e);
        }
    }

}
