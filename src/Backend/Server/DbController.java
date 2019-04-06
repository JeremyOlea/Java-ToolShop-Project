package Backend.Server;
import java.sql.*;

public class DbController {
  Connection connection;
  Statement statement;
  public DbController() {
    super();
    //  TODO: get the server to create the database, build methods to execute certain statements/queries, and eventually CLOSE THE CONNECTION/STATEMENT!
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "admin", "mysqladmin");   
      createDatabase(); 
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  private void createDatabase() throws SQLException {
    String sql_stmt = "CREATE DATABASE IF NOT EXISTS `toolshop_db`;";
    statement = connection.createStatement();
    statement.executeUpdate(sql_stmt);
    System.out.println("toolshop_db has successfully been created");
  }
}