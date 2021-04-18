package businesslogic.listeners;

import businesslogic.AbstractBLL;
import businesslogic.ClientBLL;
import businesslogic.OrderBLL;
import businesslogic.ProductBLL;
import model.Client;
import model.Order;
import model.Product;
import presentation.MainFrame;
import presentation.panels.AbstractPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Listener for creating a new object</p>
 * @param <T> type of object
 */
public class CreateObjectListener<T> implements ActionListener {
   private final AbstractPanel<T> objectPanel;
   private final AbstractBLL<T> objectBLL;

   /**
    * Default constructor
    * @param objectPanel GUI panel
    * @param objectBLL specific object queries
    */
   public CreateObjectListener(AbstractPanel<T> objectPanel, AbstractBLL<T> objectBLL) {
      this.objectPanel = objectPanel;
      this.objectBLL = objectBLL;
   }

   /**
    * <p>Adds meaning to button</p>
    * @param e current event
    */
   @Override
   public void actionPerformed(ActionEvent e) {
      int id;
      T newObject = objectPanel.getFields();
      if(objectBLL.getClass() == ClientBLL.class) {
         id = ((ClientBLL) objectBLL).createClient((Client)newObject);
         ((Client)newObject).setId(id);
      } else if(objectBLL.getClass() == ProductBLL.class) {
         id = ((ProductBLL) objectBLL).createProduct((Product)newObject);
         ((Product)newObject).setId(id);
      } else {
         id = ((OrderBLL) objectBLL).createOrder((Order)newObject);
         ((Order)newObject).setId(id);
      }

      if (id == -1) {
         MainFrame.showAlert("Failed to add new " + newObject.getClass().getSimpleName().toLowerCase() + "!");
      } else {
         MainFrame.showAlert("Successfully added a new " + newObject.getClass().getSimpleName().toLowerCase() + "!");
      }

      objectPanel.removeAll();
      objectPanel.revalidate();
   }
}
