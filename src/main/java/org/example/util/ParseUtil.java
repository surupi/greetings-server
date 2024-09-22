package org.example.util;

import org.example.GreetingCommand; // Importing the GreetingCommand class

import java.util.Arrays; // Utility class for working with arrays
import java.util.List; // List interface to store components of the parsed input

public class ParseUtil {

    // Method to parse input from a string and return a GreetingCommand object
    public static GreetingCommand parseInput(String line) {
        // Split the input line by spaces and convert the result into a list of strings
        List<String> components = Arrays.stream(line.split(" ")).toList();

        // Build and return a GreetingCommand object
        return GreetingCommand.builder()
                // First component is assumed to be the name of the command
                .name(components.get(0)) // Fixed method from `getFirst()` to `get(0)`
                // Remaining components are passed as arguments to the command
                .arguments(components.subList(1, components.size()))
                .build();
    }
}
