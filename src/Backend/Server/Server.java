package Backend.Server;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;
/**
 * A server application
 * @author Michael Jeremy Olea, Oscar Wong
 * @version 1.0
 * @since April 4th, 2018
 */
public class Server {
    /**
     * Socket for server
     */
    private ServerSocket serverSocket;
    /**
     * Printing back to client
     */
    private PrintWriter socketOut;
    /**
     * Socket for client
     */
    private Socket clientSocket;
    /**
     * Reading input from client
     */
    private BufferedReader socketIn;


    /**
     * Constructor for Server
     * @param portNumber port used for Socket
     */
    public Server(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
            clientSocket = serverSocket.accept();
            System.out.println("Server in now running...");
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch(IOException e) {
            System.out.println("error in constructor");
        }
    }

    /**
     * Waits for client input then acts accordingly to the input
     */
    public void communicate() {
        ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
		readSuppliers(suppliers);
		Inventory theInventory = new Inventory(readItems(suppliers));
        Shop theShop = new Shop(theInventory, suppliers);
        String input = "";
        while(true) {
            try {
                input = socketIn.readLine();
                System.out.println(input);
                if(input.equals("GET/TOOLS")) {
                    getTools(theShop);
                }
            } catch(IOException e) {
                System.err.println(e.getMessage());
            }           
        }
    }

    public void getTools(Shop theShop) {
        Inventory temp = theShop.getTheInventory();
        String output = "";
        for(int i = 0; i < temp.getItemList().size(); i++) {
            output += temp.getItemList().get(i).toString();
        }
        socketOut.println(output);
        socketOut.println("GET/TOOLS");
    }

    /**
     * CLoses the socket, printwrite and bufferedreader
     */
    public void close() {
        try {
            socketIn.close();
            socketOut.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdown();
        }
    }

    /**
     * Reads all suppliers from text file into an ArrayList
     * @param suppliers ArrayList of all suppliers
     */
    public void readSuppliers(ArrayList<Supplier> suppliers) {
		try {
            FileReader fr = new FileReader("suppliers.txt");
			BufferedReader br = new BufferedReader(fr);

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(";");
				suppliers.add(new Supplier(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3]));
            }
            br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    /**
     * Reads all the items from text file
     * @param suppliers ArrayList of suppliers
     * @return ArrayList of items read from file
     */
    private ArrayList<Item> readItems(ArrayList<Supplier> suppliers) {

		ArrayList<Item> items = new ArrayList<Item>();

		try {
			FileReader fr = new FileReader("items.txt");
            BufferedReader br = new BufferedReader(fr);

			String line = "";
			while ((line = br.readLine()) != null) {
				String[] temp = line.split(";");
				int supplierId = Integer.parseInt(temp[4]);

				Supplier theSupplier = findSupplier(supplierId, suppliers);
				if (theSupplier != null) {
					Item myItem = new Item(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]),
							Double.parseDouble(temp[3]), theSupplier);
					items.add(myItem);
					theSupplier.getItemList().add(myItem);
				}
            }
            br.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return items;
    }
    
    /**
     * Finds supplier given an ID
     * @param supplierId ID of supplier to be found
     * @param suppliers ArrayList of all suppliers
     * @return The supplier with the same ID
     */
    private Supplier findSupplier(int supplierId, ArrayList<Supplier> suppliers) {
		Supplier theSupplier = null;
		for (Supplier s : suppliers) {
			if (s.getSupId() == supplierId) {
				theSupplier = s;
				break;
			}

		}
		return theSupplier;
    }
    
    /**
     * Creates server object and runs communicate class
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server(5050);
        DbController db = new DbController();
        server.communicate();
    }

}