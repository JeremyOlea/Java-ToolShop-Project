package Server;

import java.sql.*;
import java.util.*;
import java.io.*;

/**
 * Class to control the database from the server.
 * @author Oscar Wong, Jeremy Olea
 * @version 1.0
 * @since April 12th, 2019
 */
public class DbController {
  /**
   * Connection to the database.
   */
  Connection connection;

  /**
   * Constructs a new database controller.
   */
  public DbController() {
    super();
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "admin", "mysqladmin");
      createDatabase();      
    } catch (SQLException e) {
      e.printStackTrace();
    }    
  }

  /**
   * Creates a new MySQL database.
   */
  private void createDatabase() {
    String sql_stmt = "CREATE DATABASE `toolshop_db`;";
    try {
      PreparedStatement pstmt = connection.prepareStatement(sql_stmt);
      pstmt.executeUpdate();
      System.out.println("toolshop_db has successfully been created");
      connection.setCatalog("toolshop_db"); // select toolshop_db as the database to use
      populateSuppliers();
      populateItems();
    } catch (SQLException e) {
      if (e.getMessage().contains("Can't create database 'toolshop_db'; database exists")) {
        System.out.println("Database already exists. Using existing database.");
        try {
          connection.setCatalog("toolshop_db"); // select toolshop_db as the database to use
        } catch (SQLException err) {
          err.printStackTrace();
        }
      } else {
        e.printStackTrace();
        System.exit(1);
      }
    }
  }

  /**
   * Populate the suppliers from the text file on the database.
   */
  public void populateSuppliers() {
    try {
      String sql_stmt = "CREATE TABLE suppliers" + "(id INTEGER not NULL, " + "name VARCHAR(255), "
          + "address VARCHAR(255), " + "contactName VARCHAR(255), " + "PRIMARY KEY(id))";
      PreparedStatement pstmt = connection.prepareStatement(sql_stmt);
      pstmt.executeUpdate();

      FileReader fr = new FileReader("suppliers.txt");
      BufferedReader br = new BufferedReader(fr);

      String line = "";
      while ((line = br.readLine()) != null) {
        String[] temp = line.split(";");
        sql_stmt = "INSERT INTO suppliers(id, name, address, contactName) VALUES(?,?,?,?)";
        pstmt = connection.prepareStatement(sql_stmt, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, Integer.parseInt(temp[0]));
        pstmt.setString(2, temp[1]);
        pstmt.setString(3, temp[2]);
        pstmt.setString(4, temp[3]);
        pstmt.executeUpdate();
      }
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Populate the items in the database from the text file.
   */
  public void populateItems() {
    try {
      String sql_stmt = "CREATE TABLE items" + "(id INTEGER not NULL, " + "name VARCHAR(255), "
          + "quantity INTEGER, " + "price DOUBLE, " + "supplierId INTEGER, " + "PRIMARY KEY(id))";
      PreparedStatement pstmt = connection.prepareStatement(sql_stmt);
      pstmt.executeUpdate();

      FileReader fr = new FileReader("items.txt");
      BufferedReader br = new BufferedReader(fr);

      String line = "";
      while ((line = br.readLine()) != null) {
        String[] temp = line.split(";");
        sql_stmt = "INSERT INTO items(id, name, quantity, price, supplierId) VALUES(?,?,?,?,?)";
        pstmt = connection.prepareStatement(sql_stmt, Statement.RETURN_GENERATED_KEYS);
        pstmt.setInt(1, Integer.parseInt(temp[0]));
        pstmt.setString(2, temp[1]);
        pstmt.setInt(3, Integer.parseInt(temp[2]));
        pstmt.setDouble(4, Double.parseDouble(temp[3]));
        pstmt.setInt(5, Integer.parseInt(temp[4]));
        pstmt.executeUpdate();
      }
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Decrease an item's quantity.
   * @param item Item to decrease quantity of.
   */
  public void decreaseQuantity(Item item) {
    String sql_stmt = "UPDATE ITEMS SET quantity = ? WHERE id = ?";
    try {
      PreparedStatement pstmt = connection.prepareStatement(sql_stmt);
      pstmt.setInt(1, item.getItemQuantity());
      pstmt.setInt(2, item.getItemId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Populate the given ArrayList with Suppliers from the db.
   * @param suppliers ArrayList to populate.
   */
  public void fetchSuppliers(List<Supplier> suppliers) {
    try {
      String sql_stmt = "SELECT * FROM SUPPLIERS";
      PreparedStatement pstmt = connection.prepareStatement(sql_stmt);
      ResultSet rs = pstmt.executeQuery(sql_stmt);

      while (rs.next()) {
        Supplier supp = new Supplier(rs.getInt(1), rs.getString("name"), rs.getString("address"),
            rs.getString("contactName"));
        suppliers.add(supp);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Return an ArrayList of Items, linked to a list of Suppliers.
   * @param suppliers ArrayList of Suppliers to link to.
   * @return ArrayList of items from the database.
   */
  public ArrayList<Item> fetchItems(ArrayList<Supplier> suppliers) {
    ArrayList<Item> items = new ArrayList<Item>();
    try {
      String sql_stmt = "SELECT * FROM ITEMS";
      PreparedStatement pstmt = connection.prepareStatement(sql_stmt);
      ResultSet rs = pstmt.executeQuery(sql_stmt);

      while (rs.next()) {
        Supplier supp = findSupplier(rs.getInt(5), suppliers); // search for supplier with given id
        items.add(new Item(rs.getInt(1), rs.getString("name"), rs.getInt(3), rs.getDouble(4), supp));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return items;
  }

  /**
   * Finds supplier given an ID
   * 
   * @param supplierId ID of supplier to be found
   * @param suppliers  ArrayList of all suppliers
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
   * Closes the connection to the database.
   */
  public void close() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}