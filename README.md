# Greeting Server Application

This is a simple Java-based server application that handles greeting commands from clients. The server listens for connections on a specified port and responds to commands such as setting a name, location, greeting the user, and quitting the session.

## Features

- Accepts client connections on a configurable port.
- Allows users to set their name and location.
- Responds with a personalized greeting.
- Provides timeout control for client connections.
- Supports basic command-line arguments using JCommander.
- Uses Lombok for cleaner code with annotations like `@Builder`, `@Getter`, and `@Setter`.
- Handles client requests concurrently using multi-threading.
- Logs server actions using SLF4J.

## Requirements

- Java 8 or higher
- Gradle (for building the project)
- Dependencies:
  - JCommander
  - SLF4J (with Simple backend)
  - Lombok

## Getting Started

### Prerequisites

Make sure you have Java and Gradle installed on your machine.

### Build

Clone the repository and build the project:

```bash
git clone https://github.com/your-username/greeting-server.git
cd greeting-server
./gradlew build
