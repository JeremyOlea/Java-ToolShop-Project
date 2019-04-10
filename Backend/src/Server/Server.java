package Server;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
     * A thread pool to hold multiple threads
     */
    private ExecutorService pool;

    /**
     * Database Controller to interact with MySQL db.
     */
    private DbController db;

    /**
     * Constructor for Server
     * @param portNumber port used for Socket
     */
    public Server(int portNumber) {
        db = new DbController();
        try {
            serverSocket = new ServerSocket(portNumber);
            pool = Executors.newCachedThreadPool();
        } catch(IOException e) {
            System.out.println("Error in constructor");
        }
    }

    /**
     * Waits for client input then acts accordingly to the input
     */
    public void communicate() {
        ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
		db.fetchSuppliers(suppliers);
        Inventory theInventory = new Inventory(db.fetchItems(suppliers));
        try {
            while(true) {
                Shop theShop = new Shop(serverSocket.accept(), theInventory, suppliers);
                theShop.setDbController(db);
                pool.execute(theShop);
            }
        } catch(IOException e) {
            e.printStackTrace();
            pool.shutdown();     
            db.close();       
        }
        
    }   
    
    /**
     * Creates server object and runs communicate class
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Server server = new Server(5000);
        server.communicate();
    }

}