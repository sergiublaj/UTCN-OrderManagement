package businesslogic;

import businesslogic.validators.AmountValidator;
import businesslogic.validators.ExistingClientValidator;
import businesslogic.validators.ExistingProductValidator;
import businesslogic.validators.Validator;
import dataaccess.OrderDAO;
import dataaccess.ProductDAO;
import model.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderBLL extends AbstractBLL<Order> {
   private final List<Validator<Order>> validators = new ArrayList<>();
   private final OrderDAO orderDAO = new OrderDAO();
   private final ProductDAO productDAO = new ProductDAO();

   public OrderBLL() {
      validators.add(new ExistingClientValidator());
      validators.add(new ExistingProductValidator());
      validators.add(new AmountValidator());
   }

   public int createOrder(Order newOrder) {
      for (Validator<Order> crtValidator : validators) {
         crtValidator.validate(newOrder);
      }
      orderDAO.getOrderPrice(newOrder);
      productDAO.decrementStock(newOrder.getProduct(), newOrder.getAmount());
      int orderId = orderDAO.insert(newOrder);
      this.printBill(newOrder, orderId);
      return orderId;
   }

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

   public Order searchOrder(int orderId) {
      return orderDAO.findById(orderId);
   }

   public ArrayList<Order> viewOrders() {
      return orderDAO.findAll();
   }
}
