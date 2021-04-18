package dataaccess;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Product specific queries
 */
public class ProductDAO extends AbstractDAO<Product> {
   private static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());

   /**
    * <p>Method decrements the stock of a product by an amount in an order</p>
    * @param productId id of product
    * @param usedAmount amount used in an order
    */
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

   /**
    * <p>Builds the query for updating the stock of a product</p>
    * @return stock update query
    */
   private String createUpdateStockQuery() {
      return "UPDATE `Product` SET stock = stock - ? WHERE ID = ?";
   }

   /**
    * <p>Method queries the price for tbe current order</p>
    * @param productId current product
    * @return product price
    */
   public int getPrice(int productId) {
      Connection dbConnection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      int totalPrice;
      try {
         dbConnection = ConnectionFactory.getConnection();
         preparedStatement = dbConnection.prepareStatement(createPriceQuery());
         preparedStatement.setInt(1, productId);
         System.out.println(preparedStatement);
         resultSet = preparedStatement.executeQuery();
         resultSet.next();
         totalPrice = resultSet.getInt(1);
      } catch (SQLException sqlEx) {
         LOGGER.log(Level.WARNING, "ProductDAO: getPrice " + sqlEx.getMessage());
         totalPrice = -1;
      } finally {
         ConnectionFactory.closeAll(dbConnection, preparedStatement, resultSet);
      }
      return totalPrice;
   }

   /**
    * <p>Builds the query for selecting the price of a product</p>
    * @return price query
    */
   private String createPriceQuery() {
      return "SELECT price FROM `Product` WHERE id = ?";
   }
}
