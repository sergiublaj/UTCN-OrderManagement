package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Class ensures application connection to the database</p>
 */
public class ConnectionFactory {
   private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
   private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
   private static final String URL = "jdbc:mysql://localhost:3306/warehouse";
   private static final String USER = "root";
   private static final String PASSWORD = "admin";
   private static final ConnectionFactory connectionInstance = new ConnectionFactory();

   /**
    * <p>Default constructor prepares the connection driver</p>
    */
   private ConnectionFactory() {
      try {
         Class.forName(DRIVER);
      } catch (ClassNotFoundException classEx) {
         classEx.printStackTrace();
      }
   }

   /**
    * <p>Method create the connections to the database</p>
    * @return database connection
    */
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

   /**
    * <p>Returns the singletone instance of databse connection object</p>
    * @return connection to the database
    */
   public static Connection getConnection() {
      return connectionInstance.createConnection();
   }

   /**
    * <p>Closes the database related objects</p>
    * @param dbConnection database connection
    * @param crtStatememnt connection statement
    * @param resultSet connection result set
    */
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
