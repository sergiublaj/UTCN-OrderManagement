package businesslogic.listeners;

import businesslogic.AbstractBLL;
import businesslogic.ClientBLL;
import businesslogic.OrderBLL;
import businesslogic.ProductBLL;
import presentation.panels.AbstractPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Listener for searching an object</p>
 * @param <T> type of object
 */
public class SearchObjectListener<T> implements ActionListener {
   private final AbstractPanel<T> objectPanel;
   private final AbstractBLL<T> objectBLL;
   private final String className;

   /**
    * Default constuctor
    * @param objectPanel GUI panel
    * @param objectBLL specific object queries
    * @param className type of object
    */
   public SearchObjectListener(AbstractPanel<T> objectPanel, AbstractBLL<T> objectBLL, String className) {
      this.objectPanel = objectPanel;
      this.objectBLL = objectBLL;
      this.className = className;
   }

   /**
    * <p>Adds meaning to button</p>
    * @param e current event
    */
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
