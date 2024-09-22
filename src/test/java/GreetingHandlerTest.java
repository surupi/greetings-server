import org.example.GreetingHandler;
import org.example.util.ParseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GreetingHandlerTest {

    private Socket mockSocket;
    private GreetingHandler greetingHandler;
    private BufferedReader mockReader;
    private PrintWriter mockWriter;
    private InputStream mockInputStream;
    private OutputStream mockOutputStream;

    @BeforeEach
    void setUp() throws IOException {
        // Mock the Socket, InputStream, and OutputStream
        mockSocket = mock(Socket.class);
        mockInputStream = mock(InputStream.class);
        mockOutputStream = mock(OutputStream.class);
    }

    @Test
    void testGreetingHandler() throws IOException {
        // Prepare the mock input from the client
        String input = "NAME John Doe\nLOCATION Earth\nGREET\nQUIT\n";
        String expectedOutput = "200 server ready\n201 NAME ok\n201 LOCATION ok\nHello John Doe of Earth\n202 Bye\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        // Prepare the output stream to capture the handler's responses
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        GreetingHandler handler = new GreetingHandler(mockSocket, 45);
        handler.run();
        String actualOutput = outputStream.toString();

        assertEquals(expectedOutput, actualOutput, "execution successful");
    }

    @Test
    void testGreetingHandlerMissingName() throws IOException {
        String input = "LOCATION Earth\nGREET\nQUIT\n";
        String expectedOutput = "200 server ready\n201 LOCATION ok\n400 Bad Request\n202 Bye\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        GreetingHandler handler = new GreetingHandler(mockSocket, 45);
        handler.run();
        String actualOutput = outputStream.toString();

        assertEquals(expectedOutput, actualOutput, "missing NAME command");
    }

    @Test
    void testGreetingHandlerMissingLocation() throws IOException {
        String input = "NAME John Doe\nGREET\nQUIT\n";
        String expectedOutput = "200 server ready\n201 NAME ok\n400 Bad Request\n202 Bye\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        GreetingHandler handler = new GreetingHandler(mockSocket, 45);
        handler.run();
        String actualOutput = outputStream.toString();

        assertEquals(expectedOutput, actualOutput, "missing LOCATION command");
    }

    @Test
    void testGreetingHandlerMissingNameAndLocation() throws IOException {
        String input = "GREET\nQUIT\n";
        String expectedOutput = "200 server ready\n400 Bad Request\n202 Bye\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        GreetingHandler handler = new GreetingHandler(mockSocket, 45);
        handler.run();
        String actualOutput = outputStream.toString();

        assertEquals(expectedOutput, actualOutput, "missing NAME and LOCATION commands");
    }

    @Test
    void testGreetingHandlerIncorrectCommand() throws IOException {
        String input = "Ice Cream\nQUIT\n";
        String expectedOutput = "200 server ready\n400 Bad Request\n202 Bye\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        when(mockSocket.getInputStream()).thenReturn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(mockSocket.getOutputStream()).thenReturn(outputStream);

        GreetingHandler handler = new GreetingHandler(mockSocket, 45);
        handler.run();
        String actualOutput = outputStream.toString();

        assertEquals(expectedOutput, actualOutput, "missing NAME and LOCATION commands");
    }

}
