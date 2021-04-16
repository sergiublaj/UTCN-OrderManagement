package presentation.listeners;

import businesslogic.AbstractBLL;
import businesslogic.ClientBLL;
import businesslogic.ProductBLL;
import presentation.MainFrame;
import presentation.panels.AbstractPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveObjectListener<T> implements ActionListener {
   private final AbstractPanel<T> objectPanel;
   private final AbstractBLL<T> objectBLL;
   private final String className;

   public RemoveObjectListener(AbstractPanel<T> objectPanel, AbstractBLL<T> objectBLL, String className) {
      this.objectPanel = objectPanel;
      this.objectBLL = objectBLL;
      this.className = className;
   }

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
