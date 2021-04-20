package presentation.panels;

import businesslogic.ClientBLL;
import businesslogic.ProductBLL;
import model.Client;
import model.Order;
import model.Product;
import presentation.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

/**
 * <p>GUI panel for order</p>
 */
public class OrderPanel extends AbstractPanel<Order> {
   private final ArrayList<JLabel> fieldsLabel = new ArrayList<>();
   private final ArrayList<Object> fieldsInput = new ArrayList<>();

   /**
    * <p>Default constructor</p>
    */
   public OrderPanel() {
      fieldsInput.add(MainFrame.createComboBox());
      fieldsInput.add(MainFrame.createComboBox());
      fieldsInput.add(MainFrame.createInput());

      for (Field value : Order.class.getDeclaredFields()) {
         fieldsLabel.add(MainFrame.createLabel(value.getName().toUpperCase() + ": "));
      }
   }

   /**
    * <p>GUI panel for showing order's info</p>
    * @param hasId boolean value representing the needing of showing the id of object or not
    */
   @SuppressWarnings("unchecked")
   public void showObjectInfo(boolean hasId) {
      this.addClientsToList();
      this.addProductsToList();

      int fieldsNb = hasId ? fieldsLabel.size() : fieldsLabel.size() - 3;
      midPanel.setLayout(new GridLayout(fieldsNb, 2, -150, 10));
      midPanel.setMaximumSize(new Dimension(650, fieldsNb * 55));
      midPanel.setBorder(new EmptyBorder(20, 100, 20, 100));
      this.add(midPanel);

      for (int i = 0; i < fieldsInput.size(); i++) {
         midPanel.add(fieldsLabel.get(i + 1));
         if (fieldsInput.get(i).getClass().getSimpleName().compareTo("JTextField") == 0) {
            midPanel.add((JTextField) fieldsInput.get(i));
         } else {
            midPanel.add((JComboBox<String>) fieldsInput.get(i));
         }
      }
   }

   /**
    * <p>Adds clients into a dropbox list</p>
    */
   @SuppressWarnings("unchecked")
   public void addClientsToList() {
      ((JComboBox<String>) fieldsInput.get(0)).removeAllItems();
      ArrayList<Client> clientList = new ClientBLL().viewClients();
      for (Client crtClient : clientList) {
         ((JComboBox<String>) fieldsInput.get(0)).addItem(crtClient.getName());
      }
   }

   /**
    * <p>Adds products' names into a dropbox list</p>
    */
   @SuppressWarnings("unchecked")
   public void addProductsToList() {
      ((JComboBox<String>) fieldsInput.get(1)).removeAllItems();
      ArrayList<Product> productList = new ProductBLL().viewProducts();
      for (Product crtProduct : productList) {
         ((JComboBox<String>) fieldsInput.get(1)).addItem(crtProduct.getName());
      }
   }

   /**
    * <p>Makes an order objects from the input fields</p>
    * @return a newly created order
    */
   @SuppressWarnings("unchecked")
   public Order getFields() {
      Order object = null;
      if(((JTextField)fieldsInput.get(2)).getText().trim().isBlank()) {
         MainFrame.showAlert("Fill in all the fields!");
      } else {
         object = new Order();
         object.setClient(new ClientBLL().searchClient(Objects.requireNonNull(((JComboBox<String>) fieldsInput.get(0)).getSelectedItem()).toString()).getId());
         object.setProduct(new ProductBLL().searchProduct(Objects.requireNonNull(((JComboBox<String>) fieldsInput.get(1)).getSelectedItem()).toString()).getId());
         object.setAmount(Integer.parseInt(((JTextField)fieldsInput.get(2)).getText()));
         object.setDate(new Date(System.currentTimeMillis()));
      }
      return object;
   }
}
