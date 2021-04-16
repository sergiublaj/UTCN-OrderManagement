package presentation.listeners;

import businesslogic.AbstractBLL;
import businesslogic.ClientBLL;
import businesslogic.OrderBLL;
import businesslogic.ProductBLL;
import presentation.panels.AbstractPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchObjectListener<T> implements ActionListener {
   private final AbstractPanel<T> objectPanel;
   private final AbstractBLL<T> objectBLL;
   private final String className;

   public SearchObjectListener(AbstractPanel<T> objectPanel, AbstractBLL<T> objectBLL, String className) {
      this.objectPanel = objectPanel;
      this.objectBLL = objectBLL;
      this.className = className;
   }

   @Override
   @SuppressWarnings("unchecked")
   public void actionPerformed(ActionEvent e) {
      T foundObject;
      int id = objectPanel.getIdField();
      if(className.compareTo("client") == 0) {
         foundObject = (T)((ClientBLL)objectBLL).searchClient(id);
      } else if(className.compareTo("product") == 0) {
         foundObject = (T)((ProductBLL)objectBLL).searchProduct(id);
      } else {
         foundObject = (T)((OrderBLL)objectBLL).searchOrder(id);
      }
      objectPanel.setFields(foundObject);
   }
}
