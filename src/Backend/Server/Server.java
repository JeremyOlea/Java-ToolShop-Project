package Backend.Server;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private PrintWriter socketOut;
    private Socket clientSocket;
    private BufferedReader socketIn;


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

    public void communicate() {
        ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
		readSuppliers(suppliers);
		Inventory theInventory = new Inventory(readItems(suppliers));
        Shop theShop = new Shop(theInventory, suppliers);
        String input = "";
        while(true) {
            try {
                input = socketIn.readLine();
                if(input.equals("GET/TOOLS")) {
                    Inventory temp = theShop.getTheInventory();
                    String output = "";
                    for(int i = 0; i < temp.getItemList().size(); i++) {
                        output += temp.getItemList().get(i).toString();
                    }
                    socketOut.println(output);
                }

            } catch(IOException e) {
                System.err.println(e.getMessage());
            }
            try {
                socketIn.close();
                socketOut.close();
                serverSocket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            
        }
    }

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
    
    public static void main(String[] args) {
        Server server = new Server(5050);
        server.communicate();
    }

}