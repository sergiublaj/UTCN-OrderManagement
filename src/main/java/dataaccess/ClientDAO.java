package dataaccess;

import connection.ConnectionFactory;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Specific clients queries
 */
public class ClientDAO extends AbstractDAO<Client> {
   /**
    * <p>Returns a client with the given name</p>
    * @param clientName name of the client to look for
    * @return client with the given name
    */
   public Client findByName(String clientName) {
      Connection dbConnection = null;
      PreparedStatement findStatement = null;
      ResultSet resultSet = null;
      Client toReturn = null;
      try {
         dbConnection = ConnectionFactory.getConnection();
         findStatement = dbConnection.prepareStatement(createFindByNameQuery());
         findStatement.setString(1, clientName);
         resultSet = findStatement.executeQuery();
         toReturn = createObjects(resultSet).get(0);
      } catch (SQLException | IndexOutOfBoundsException e) {
         LOGGER.log(Level.WARNING, "Client DAO: findById " + e.getMessage());
      } finally {
         ConnectionFactory.closeAll(dbConnection, findStatement, resultSet);
      }
      return toReturn;
   }

   /**
    * <p>Build the query for finding a client with a given name</p>
    * @return find query
    */
   private String createFindByNameQuery() {
      return "SELECT * FROM `Client` WHERE NAME = ?";
   }
}
