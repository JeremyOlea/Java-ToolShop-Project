package Controller;

import java.io.IOException;

/**
 * A class to handle GUI actions and trigger client actions.
 * @author Oscar Wong, Jeremy Olea
 * @version 1.0
 * @since April 4th, 2019
 */
public class Listener {
  /**
   * Client object to work with.
   */
  private Client client;

  /**
   * Constructs a new listener.
   * @param client Attaches the given client to this listener.
   */
  public Listener(Client client) {
    this.client = client;
  }

  /**
   * Performs a specific client action, and returns a String containing the results to the GUI.
   * @param action Action String to denote what to do.
   * @return The result of Server output, if applicable.
   */
  public String actionPerformed(String action) {
    if (action.equals("GET/TOOLS")) {
      try {
        return client.displayAllTools();
      } catch (IOException e) {
        return "There was an error fetching tools.";
      }
    } else if (action.startsWith("SEARCH/TOOL/ID/") || action.startsWith("SEARCH/TOOL/NAME/")) {
      try {
        return client.simpleOperation(action);
      } catch (IOException e) {
        return "There was an error searching for the tool";
      }
    } else if (action.startsWith("GET/TOOL/QUANTITY/")) {
      try {
        return client.simpleOperation(action);
      } catch (IOException e) {
        return "There was an error searching for the tool";
      }
    } else if (action.startsWith("DECREASE/TOOL/QUANTITY/")) {
      try {
        return client.simpleOperation(action);
      } catch (IOException e) {
        return "There was an error searching for the tool";
      }
    } else if (action.startsWith("BUY/TOOL/")) {
      try {
        return client.simpleOperation(action);
      } catch (IOException e) {
        return "There was an error searching for the tool";
      }
    }
    
    return "NULL";
  }
}