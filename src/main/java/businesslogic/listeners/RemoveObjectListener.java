package businesslogic.listeners;

import businesslogic.AbstractBLL;
import businesslogic.ClientBLL;
import businesslogic.ProductBLL;
import presentation.MainFrame;
import presentation.panels.AbstractPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>Listener for removing an object</p>
 * @param <T> type of object
 */
public class RemoveObjectListener<T> implements ActionListener {
   private final AbstractPanel<T> objectPanel;
   private final AbstractBLL<T> objectBLL;
   private final String className;

   /**
    * Default constructor
    * @param objectPanel GUI panel
    * @param objectBLL specific object queries
    * @param className class of object
    */
   public RemoveObjectListener(AbstractPanel<T> objectPanel, AbstractBLL<T> objectBLL, String className) {
      this.objectPanel = objectPanel;
      this.objectBLL = objectBLL;
      this.className = className;
   }

   /**
    * <p>Adds meaning to button</p>
    * @param e current event
    */
   @Override
   public void actionPerformed(ActionEvent e) {
      int id = objectPanel.getIdField();
      if (MainFrame.showConfirm("Are you sure?") == 0) {
         if (className.compareTo("client") == 0) {
            if (((ClientBLL) objectBLL).removeClient(id)) {
               MainFrame.showAlert("Successfully removed the client!");
            } else {
               MainFrame.showAlert("Failed to remove the client!");
            }
         } else if (className.compareTo("product") == 0) {
            if (((ProductBLL) objectBLL).removeProduct(id)) {
               MainFrame.showAlert("Successfully removed the product!");
            } else {
               MainFrame.showAlert("Failed to remove the product!");
            }
         }
         objectPanel.removeAll();
         objectPanel.revalidate();
      }
   }
}
