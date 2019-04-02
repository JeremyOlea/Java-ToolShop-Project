package Frontend.Controller;

import java.net.Socket;
import java.io.*;

public class Client {
  private Socket socket;
  private BufferedReader socketIn;
  private PrintWriter socketOut;

  public Client(String serverName, int portNumber) {
    try {
      socket = new Socket(serverName, portNumber);
      socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      socketOut = new PrintWriter((socket.getOutputStream()), true);
    } catch (IOException e) {
      System.err.println(e.getStackTrace());
    }
  }

  public String displayAllTools() throws IOException {
    socketOut.println("GET/TOOLS");
    return socketIn.readLine();
  }

  //  public void communicate() {}

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