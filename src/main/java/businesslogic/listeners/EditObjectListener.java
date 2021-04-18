package businesslogic.listeners;

import businesslogic.AbstractBLL;
import businesslogic.ClientBLL;
import businesslogic.ProductBLL;
import model.Client;
import model.Product;
import presentation.MainFrame;
import presentation.panels.AbstractPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Listener for editing an object</p>
 * @param <T> type of object
 */
public class EditObjectListener<T> implements ActionListener {
   private final AbstractPanel<T> objectPanel;
   private final AbstractBLL<T> objectBLL;

   /**
    * Default constructor
    * @param objectPanel GUI panel
    * @param objectBLL specific object queries
    */
   public EditObjectListener(AbstractPanel<T> objectPanel, AbstractBLL<T> objectBLL) {
      this.objectPanel = objectPanel;
      this.objectBLL = objectBLL;
   }

   /**
    * <p>Adds meaning to button</p>
    * @param e current event
    */
   @Override
   public void actionPerformed(ActionEvent e) {
      boolean returnedValue = false;
      T newObject = objectPanel.getFields();
      if(objectBLL.getClass() == ClientBLL.class) {
         returnedValue = ((ClientBLL) objectBLL).editClient((Client)newObject);
      } else if(objectBLL.getClass() == ProductBLL.class) {
         returnedValue = ((ProductBLL) objectBLL).editProduct((Product)newObject);
      }

      if(returnedValue) {
         MainFrame.showAlert("Successfully edited the " + newObject.getClass().getSimpleName().toLowerCase() + "!");
      } else {
         MainFrame.showAlert("Failed to edit the " + newObject.getClass().getSimpleName().toLowerCase() + "!");
      }
      objectPanel.removeAll();
      objectPanel.revalidate();
   }
}
