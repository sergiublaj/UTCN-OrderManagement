package presentation.listeners;

import businesslogic.AbstractBLL;
import businesslogic.ClientBLL;
import businesslogic.OrderBLL;
import businesslogic.ProductBLL;
import presentation.MainFrame;
import presentation.panels.AbstractPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextStepListener<T> implements ActionListener {
   private final AbstractPanel<T> objectPanel;
   private final AbstractBLL<T> objectBLL;
   private final String currentPanel;
   private final String nextPanel;

   public NextStepListener(AbstractPanel<T> objectPanel, AbstractBLL<T> objectBLL, String currentPanel, String nextPanel) {
      this.objectPanel = objectPanel;
      this.objectBLL = objectBLL;
      this.currentPanel = currentPanel;
      this.nextPanel = nextPanel;
   }

   @Override
   @SuppressWarnings("unchecked")
   public void actionPerformed(ActionEvent e) {
      if (objectPanel.getIdField() == -1) {
         MainFrame.showAlert("Field is empty!");
         return;
      }

      T foundObject;
      if (currentPanel.compareTo("client") == 0) {
         foundObject = (T) ((ClientBLL) objectBLL).searchClient(objectPanel.getIdField());
      } else if (currentPanel.compareTo("product") == 0) {
         foundObject = (T) ((ProductBLL) objectBLL).searchProduct(objectPanel.getIdField());
      } else {
         foundObject = (T) ((OrderBLL) objectBLL).searchOrder(objectPanel.getIdField());
      }

      if (foundObject == null) {
         MainFrame.showAlert(nextPanel + " not found!");
         objectPanel.removeAll();
         objectPanel.revalidate();
         return;
      }

      if (nextPanel.compareTo("edit") == 0) {
         objectPanel.showEditObjectPanel(foundObject);
      } else if (nextPanel.compareTo("remove") == 0) {
         objectPanel.showRemoveObjectPanel(foundObject);
      } else {
         objectPanel.showSearchObjectPanel(foundObject);
      }
   }
}
