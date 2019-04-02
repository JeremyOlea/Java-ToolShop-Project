package Frontend.Controller;

import java.io.IOException;

public class Listener {
  private Client client;

  public Listener(Client client) {
    this.client = client;
  }

  /**
   * Performs a specific client action, and returns a String containing the results to the GUI.
   */
  public String actionPerformed(String action) {
    if (action.equals("GET/TOOLS")) {
      try {
        return client.displayAllTools();
      } catch (IOException e) {
        return "There was an error fetching tools.";
      }
    }
    return "";
  }
}