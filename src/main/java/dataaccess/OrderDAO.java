package dataaccess;

import connection.ConnectionFactory;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO extends AbstractDAO<Order> {
   private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

   public void getOrderPrice(Order crtOrder) {
      Connection dbConnection = null;
      PreparedStatement preparedStatement = null;
      ResultSet resultSet = null;
      int totalPrice;
      try {
         dbConnection = ConnectionFactory.getConnection();
         preparedStatement = dbConnection.prepareStatement(createPriceQuery());
         preparedStatement.setInt(1, crtOrder.getProduct());
         resultSet = preparedStatement.executeQuery();
         resultSet.next();
         totalPrice = crtOrder.getAmount() * resultSet.getInt(1);
      } catch (SQLException sqlEx) {
         LOGGER.log(Level.WARNING, "OrderDAO: orderPrice " + sqlEx.getMessage());
         totalPrice = -1;
      } finally {
         ConnectionFactory.closeAll(dbConnection, preparedStatement, resultSet);
      }
      crtOrder.setPrice(totalPrice);
   }

   private String createPriceQuery() {
      return "SELECT price FROM `Product` WHERE id = ?";
   }
}
