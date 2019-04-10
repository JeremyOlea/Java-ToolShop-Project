package Controller;

import java.net.Socket;
import java.io.*;
import java.lang.StringBuilder;

/**
 * A Simple Client.
 * @author Oscar Wong, Jeremy Olea
 * @version 1.0
 * @since April 3rd, 2019
 */
public class Client {
  /**
   * Socket to connect to.
   */
  private Socket socket;
  /**
   * Input from socket.
   */
  private BufferedReader socketIn;
  /**
   * Output to socket.
   */
  private PrintWriter socketOut;

  /**
   * Constructs a new Client.
   * @param serverName Servername to connect to.
   * @param portNumber Port on server to connect to.
   */
  public Client(String serverName, int portNumber) {
    try {
      socket = new Socket(serverName, portNumber);
      socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      socketOut = new PrintWriter((socket.getOutputStream()), true);
    } catch (IOException e) {
      System.err.println(e.getStackTrace());
    }
  }

  /**
   * Display all tools in the shop.
   * @return A string containing all tools in the shop.
   * @throws IOException In case there's an input/output error with the socket.
   */
  public String displayAllTools() throws IOException {
    socketOut.println("GET/TOOLS");
    String response = socketIn.readLine();
    StringBuilder data = new StringBuilder();
    while (! response.equals("GET/TOOLS")) {
      data.append(response);
      data.append("\n");
      response = socketIn.readLine();
    }
    return data.toString();
  }

  /**
   * Closes all connections to the socket.
   */
  public void close() {
    try {
      socketIn.close();
      socketOut.close();
      socket.close();
    } catch (IOException e) {
      System.out.println("Closing error: " + e.getMessage());
    }
  }
}