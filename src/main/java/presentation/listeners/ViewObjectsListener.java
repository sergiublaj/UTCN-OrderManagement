package presentation.listeners;

import businesslogic.AbstractBLL;
import businesslogic.ClientBLL;
import businesslogic.OrderBLL;
import businesslogic.ProductBLL;
import presentation.panels.AbstractPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewObjectsListener<T> implements ActionListener {
   private final AbstractPanel<T> objectPanel;
   private final AbstractBLL<T> objectBLL;
   private final String className;

   public ViewObjectsListener(AbstractPanel<T> objectPanel, AbstractBLL<T> objectBLL, String className) {
      this.objectPanel = objectPanel;
      this.objectBLL = objectBLL;
      this.className = className;
   }

   @Override
   @SuppressWarnings("unchecked")
   public void actionPerformed(ActionEvent e) {
      ArrayList<T> objectList;
      if (className.compareTo("client") == 0) {
         objectList = (ArrayList<T>)((ClientBLL)objectBLL).viewClients();
      } else if (className.compareTo("product") == 0) {
         objectList = (ArrayList<T>)((ProductBLL)objectBLL).viewProducts();
      } else {
         objectList =(ArrayList<T>)((OrderBLL)objectBLL).viewOrders();
      }
      objectPanel.showObjectsInTable(objectList);
   }
}
