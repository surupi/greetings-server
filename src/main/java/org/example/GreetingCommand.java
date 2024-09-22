package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder

public class GreetingCommand {
    private String name; // The name of the command (e.g., "NAME", "LOCATION", "GREET")

    // List of arguments associated with the command (e.g., values for "NAME" or "LOCATION")
    private List<String> arguments = new ArrayList<>();
}
