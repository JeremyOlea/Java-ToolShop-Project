package Server;

import java.util.ArrayList;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.*;

/**
 * Class to manage the Tool Shop Backend.
 * 
 * @author M. Moshirpour, Oscar Wong, Jeremy Olea.
 * @version 2.0
 * @since April 3rd, 2019
 */
public class Shop implements Runnable {
	/**
	 * The Shop's Inventory.
	 */
	private Inventory theInventory;
	/**
	 * The Shop's list of Suppliers.
	 */
	private ArrayList<Supplier> supplierList;
	/**
	 * Printing back to client
	 */
	private PrintWriter socketOut;
	/**
	 * Reading input from client
	 */
	private BufferedReader socketIn;
	/**
	 * Socket for client
	 */
	private Socket clientSocket;

	/**
	 * Database Controller.
	 */
	private DbController db;

	/**
	 * Constructs a new Shop.
	 * 
	 * @param inventory Inventory for the Shop.
	 * @param suppliers Suppliers to the Shop.
	 */
	public Shop(Socket s, Inventory inventory, ArrayList<Supplier> suppliers) {
		clientSocket = s;
		theInventory = inventory;
		supplierList = suppliers;
		try {
			socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			socketOut = new PrintWriter(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Link the database controller to the Shop.
	 * 
	 * @param db Database controller to set.
	 */
	public void setDbController(DbController db) {
		this.db = db;
	}

	/**
	 * Run this thread.
	 */
	public void run() {
		this.communicate();
	}

	/**
	 * Communicate with the socket.
	 */
	public void communicate() {
		String input = "";
		while (true) {
			try {
				input = socketIn.readLine();
				System.out.println(input);
				refreshInventory();
				if (input.equals("GET/TOOLS")) {
					sendString(theInventory.toString());
					sendString("GET/TOOLS");
				} else if (input.startsWith("SEARCH/TOOL/NAME/")) {
					String toolName = input.replace("SEARCH/TOOL/NAME/", "");
					sendString(getItem(toolName));
				} else if (input.startsWith("SEARCH/TOOL/ID/")) {
					int toolId = Integer.parseInt(input.replace("SEARCH/TOOL/ID/", ""));
					sendString(getItem(toolId));
				} else if (input.startsWith("GET/TOOL/QUANTITY/")) {
					String toolName = input.replace("GET/TOOL/QUANTITY/", "");
					sendString(getItemQuantity(toolName));
				} else if (input.startsWith("DECREASE/TOOL/QUANTITY/")) {
					String toolName = input.replace("DECREASE/TOOL/QUANTITY/", "");
					sendString(decreaseItem(toolName));
				} else if (input.startsWith("BUY/TOOL/")) {
					String rest = input.replace("BUY/TOOL/", "");
					String[] inputs = rest.split("/");
					String toolName = inputs[0];
					int quantity = Integer.parseInt(inputs[1]);
					sendString(buyItem(toolName, quantity));
				}
			} catch (IOException e) {
				e.printStackTrace();
				close();
				return;
			}
		}
	}

	/**
	 * Send output to socket.
	 * 
	 * @param send String to send.
	 */
	public void sendString(String send) {
		socketOut.println(send);
		socketOut.flush();
	}

	public void refreshInventory() {
		// update the inventory from the database
		db.fetchSuppliers(supplierList);
		theInventory = new Inventory(db.fetchItems(supplierList));
	}

	/**
	 * CLoses the socket, printwrite and bufferedreader
	 */
	public void close() {
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the Shop's Inventory.
	 * 
	 * @return The Shop's Inventory.
	 */
	public Inventory getTheInventory() {
		return theInventory;
	}

	/**
	 * Set the Shop's inventory.
	 * 
	 * @param inventory The Shop's incoming Inventory.
	 */
	public void setTheInventory(Inventory inventory) {
		theInventory = inventory;
	}

	/**
	 * Return the Shop's list of Suppliers.
	 * 
	 * @return The Shop's list of Suppliers.
	 */
	public ArrayList<Supplier> getSupplierList() {
		return supplierList;
	}

	/**
	 * Set the Shop's list of Suppliers.
	 * 
	 * @param suppliers The Shop's incoming list of Suppliers.
	 */
	public void setSupplierList(ArrayList<Supplier> suppliers) {
		supplierList = suppliers;
	}

	/**
	 * List all items in the Shop.
	 */
	public void listAllItems() {
		System.out.println(theInventory);
	}

	/**
	 * Buy an item.
	 * @param toolName Name of item to buy.
	 * @param quantity quantity to buy.
	 * @return Info about whether or not the operation succeeded.
	 */
	public String buyItem(String toolName, int quantity) {
		if (theInventory.getItemQuantity(toolName) >= quantity) {
			for (int i = 0; i < quantity; i++) {
				theInventory.manageItem(toolName, db);
			}
			return "Purchased!";
		} else {
			return "Sorry, you can't buy this.";
		}
	}

	/**
	 * Decrease quantity of an item in the Shop.
	 * 
	 * @param name Name of item to decrease.
	 * @return String containing info about whether or not the transaction
	 *         succeeded.
	 */
	public String decreaseItem(String name) {
		if (theInventory.manageItem(name, db) == null)
			return "Couldn't not decrease item quantity!";
		else
			return "Item quantity was decreased!";
	}

	/**
	 * List every Supplier to the Shop.
	 */
	public void listAllSuppliers() {
		for (Supplier s : supplierList) {
			System.out.println(s);
		}

	}

	/**
	 * Return an Item as a String, based on its name.
	 * 
	 * @param name Name of Item to get.
	 * @return Item in question.
	 */
	public String getItem(String name) {
		Item theItem = theInventory.searchForItem(name);
		if (theItem == null)
			return "Item " + name + " could not be found!";
		else
			return theItem.toStringNoTabs();

	}

	/**
	 * Return an Item as a String, based on its ID.
	 * 
	 * @param id ID of Item to get.
	 * @return Item in question.
	 */
	public String getItem(int id) {
		Item theItem = theInventory.searchForItem(id);
		if (theItem == null)
			return "Item number " + id + " could not be found!";
		else
			return theItem.toStringNoTabs();
	}

	/**
	 * Output an Item as a String.
	 * 
	 * @param theItem Item to output.
	 * @return The Item as a String.
	 */
	private String outputItem(Item theItem) {
		return "The item information is as follows: " + theItem;
	}

	/**
	 * Return the quantity of a particular Item.
	 * 
	 * @param name Name of Item to return the quantity of.
	 * @return Quantity of Item with given name.
	 */
	public String getItemQuantity(String name) {
		int quantity = theInventory.getItemQuantity(name);
		if (quantity < 0)
			return "Item " + name + " could not be found!";
		else
			return "The quantity of Item " + name + " is: " + quantity;
	}

	/**
	 * Return the Order currently held by the Shop's Inventory.
	 * 
	 * @return The Order in the Inventory, as a String.
	 */
	public String printOrder() {
		return theInventory.printOrder();
	}
}
