package dataaccess;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO extends AbstractDAO<Product> {
   private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

   public void decrementStock(int productId, int usedAmount)  {
      Connection dbConnection = null;
      PreparedStatement preparedStatement = null;
      try {
         dbConnection = ConnectionFactory.getConnection();
         preparedStatement = dbConnection.prepareStatement(createUpdateStockQuery());
         preparedStatement.setInt(1, usedAmount);
         preparedStatement.setInt(2, productId);
         preparedStatement.executeUpdate();
      } catch (SQLException sqlEx) {
         LOGGER.log(Level.WARNING, "ProductDAO: decrementStock " + sqlEx.getMessage());
      } finally {
         ConnectionFactory.closeAll(dbConnection, preparedStatement, null);
      }
   }

   private String createUpdateStockQuery() {
      return "UPDATE `Product` SET stock = stock - ? WHERE ID = ?";
   }
}
