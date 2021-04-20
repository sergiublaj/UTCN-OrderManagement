package dataaccess;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Product specific queries
 */
public class ProductDAO extends AbstractDAO<Product> {
   /**
    * <p>Returns a product with the given name</p>
    * @param productName name of the product to look for
    * @return product with the given name
    */
   public Product findByName(String productName) {
      Connection dbConnection = null;
      PreparedStatement findStatement = null;
      ResultSet resultSet = null;
      Product toReturn = null;
      try {
         dbConnection = ConnectionFactory.getConnection();
         findStatement = dbConnection.prepareStatement(createFindByNameQuery());
         findStatement.setString(1, productName);
         resultSet = findStatement.executeQuery();
         toReturn = createObjects(resultSet).get(0);
      } catch (SQLException | IndexOutOfBoundsException e) {
         LOGGER.log(Level.WARNING, "Product DAO: findById " + e.getMessage());
      } finally {
         ConnectionFactory.closeAll(dbConnection, findStatement, resultSet);
      }
      return toReturn;
   }

   /**
    * <p>Builds the query for finding a product with a given name</p>
    * @return find query
    */
   private String createFindByNameQuery() {
      return "SELECT * FROM `Product` WHERE NAME = ?";
   }

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
