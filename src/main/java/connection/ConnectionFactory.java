package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
   private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
   private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   private static final String URL = "jdbc:mysql://localhost:3306/warehouse";
   private static final String USER = "root";
   private static final String PASSWORD = "admin";
   private static final ConnectionFactory connectionInstance = new ConnectionFactory();

   private ConnectionFactory() {
      try {
         Class.forName(DRIVER);
      } catch (ClassNotFoundException classEx) {
         classEx.printStackTrace();
      }
   }

   private Connection createConnection() {
      Connection dbConnection = null;
      try {
         dbConnection = DriverManager.getConnection(URL, USER, PASSWORD);
      } catch (SQLException throwable) {
         LOGGER.log(Level.WARNING, "[DB] An error occured while trying to connect to the database!");
         throwable.printStackTrace();
      }
      return dbConnection;
   }

   public static Connection getConnection() {
      return connectionInstance.createConnection();
   }

   public static void closeAll(Connection dbConnection, Statement crtStatememnt, ResultSet resultSet) {
      try {
         if (dbConnection != null) {
            dbConnection.close();
         }
         if (crtStatememnt != null) {
            crtStatememnt.close();
         }
         if (resultSet != null) {
            resultSet.close();
         }
      } catch (SQLException throwable) {
         LOGGER.log(Level.WARNING, "An error occured while trying to close the Connection, Statement or ResultSet!");
         throwable.printStackTrace();
      }
   }
}
