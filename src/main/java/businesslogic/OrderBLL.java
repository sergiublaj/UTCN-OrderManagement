package businesslogic;

import businesslogic.validators.AmountValidator;
import businesslogic.validators.ExistingClientValidator;
import businesslogic.validators.ExistingProductValidator;
import businesslogic.validators.Validator;
import dataaccess.OrderDAO;
import model.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Various products business logic</p>
 */
public class OrderBLL extends AbstractBLL<Order> {
   private final List<Validator<Order>> validators = new ArrayList<>();
   private final OrderDAO orderDAO = new OrderDAO();
   private final ProductBLL productBLL = new ProductBLL();

   /**
    * <p>Default constructor adding the validators</p>
    */
   public OrderBLL() {
      validators.add(new ExistingClientValidator());
      validators.add(new ExistingProductValidator());
      validators.add(new AmountValidator());
   }

   /**
    * <p>>Tries to validate an order and, if the order is valid, makes call to insert it into database</p>
    * @param newOrder order to be added to database
    * @return inserted order id
    */
   public int createOrder(Order newOrder) {
      for (Validator<Order> crtValidator : validators) {
         crtValidator.validate(newOrder);
      }
      newOrder.setPrice(newOrder.getAmount() * productBLL.getPrice(newOrder.getProduct()));
      productBLL.decrementStock(newOrder.getProduct(), newOrder.getAmount());
      int orderId = orderDAO.insert(newOrder);
      this.printBill(newOrder, orderId);
      return orderId;
   }

   /**
    * <p>Prints the bill of a placed order</p>
    * @param crtOrder current order
    * @param orderId id of the order
    */
   private void printBill(Order crtOrder, int orderId) {
      ArrayList<String> fields = orderDAO.getFields(crtOrder);
      ArrayList<Object> values = orderDAO.getValues(crtOrder);

      StringBuilder orderBill = new StringBuilder("------ Order ");
      orderBill.append(orderId);
      orderBill.append(" ------\n");
      for (int i = 1; i < fields.size(); i++) {
         orderBill.append(fields.get(i));
         orderBill.append(": ");
         orderBill.append(values.get(i));
         orderBill.append("\n");
      }
      orderBill.append("----- THANK YOU! -----\n\n");

      try {
         FileWriter fileWriter = new FileWriter("bill.txt", true);
         fileWriter.write(orderBill.toString());
         fileWriter.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * <p>Makes call to search an order in database</p>
    * @param orderId id of the order to be searched
    * @return the found order or null
    */
   public Order searchOrder(int orderId) {
      return orderDAO.findById(orderId);
   }

   /**
    * <p>Makes call to select all the orders from database</p>
    * @return a list of all the orders existing in database
    */
   public ArrayList<Order> viewOrders() {
      return orderDAO.findAll();
   }
}
