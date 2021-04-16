package presentation;

import businesslogic.ClientBLL;
import businesslogic.OrderBLL;
import businesslogic.ProductBLL;
import presentation.listeners.*;
import presentation.panels.ClientPanel;
import presentation.panels.OrderPanel;
import presentation.panels.ProductPanel;

public class Controller {
   private final MainFrame mainFrame;

   public Controller(MainFrame mainFrame) {
      this.mainFrame = mainFrame;
      this.addEventListeners();
   }

   private void addEventListeners() {
      this.addClientEventListeners();
      this.addProductEventListeners();
      this.addOrderEventListeners();
   }

   private void addClientEventListeners() {
      TabbedPanel tabbedPanel = (TabbedPanel) mainFrame.getPanelContainer().getComponentAt(0);
      ClientPanel clientPanel = (ClientPanel)tabbedPanel.getActivityPanel();
      ClientBLL clientBLL = new ClientBLL();

      tabbedPanel.addCreateButtonListener(e -> {
         clientPanel.clearFields();
         clientPanel.removeAllEventListeners();
         clientPanel.showCreateObjectPanel();
         clientPanel.addPerformOperationBtnListener(new CreateObjectListener<>(clientPanel, clientBLL));
      });
      clientPanel.addPerformOperationBtnListener(new CreateObjectListener<>(clientPanel, clientBLL));

      tabbedPanel.addEditButtonListener(e -> {
         clientPanel.clearFields();
         clientPanel.removeAllEventListeners();
         clientPanel.showObjectByIdPanel();
         clientPanel.addNextStepBtnListener(new NextStepListener<>(clientPanel, clientBLL, "client", "edit"));
         clientPanel.addPerformOperationBtnListener(new EditObjectListener<>(clientPanel, clientBLL));
      });

      tabbedPanel.addRemoveButtonListener(e -> {
         clientPanel.clearFields();
         clientPanel.removeAllEventListeners();
         clientPanel.showObjectByIdPanel();
         clientPanel.addNextStepBtnListener(new NextStepListener<>(clientPanel, clientBLL, "client", "remove"));
         clientPanel.addPerformOperationBtnListener(new RemoveObjectListener<>(clientPanel, clientBLL, "client"));
      });

      tabbedPanel.addSearchButtonListener(e -> {
         clientPanel.clearFields();
         clientPanel.removeAllEventListeners();
         clientPanel.showObjectByIdPanel();
         clientPanel.addNextStepBtnListener(new NextStepListener<>(clientPanel, clientBLL, "client", "search"));
         clientPanel.addPerformOperationBtnListener(new SearchObjectListener<>(clientPanel, clientBLL, "client"));
      });

      tabbedPanel.addViewButtonListener(e -> {
         clientPanel.clearFields();
         clientPanel.removeAllEventListeners();
         clientPanel.showViewObjectsPanel();
      });
      tabbedPanel.addViewButtonListener(new ViewObjectsListener<>(clientPanel, clientBLL, "client"));
   }

   private void addProductEventListeners() {
      TabbedPanel tabbedPanel = (TabbedPanel) mainFrame.getPanelContainer().getComponentAt(1);
      ProductPanel productPanel = (ProductPanel)tabbedPanel.getActivityPanel();
      ProductBLL productBLL = new ProductBLL();

      tabbedPanel.addCreateButtonListener(e -> {
         productPanel.clearFields();
         productPanel.removeAllEventListeners();
         productPanel.showCreateObjectPanel();
         productPanel.addPerformOperationBtnListener(new CreateObjectListener<>(productPanel, productBLL));
      });
      productPanel.addPerformOperationBtnListener(new CreateObjectListener<>(productPanel, productBLL));

      tabbedPanel.addEditButtonListener(e -> {
         productPanel.clearFields();
         productPanel.removeAllEventListeners();
         productPanel.showObjectByIdPanel();
         productPanel.addNextStepBtnListener(new NextStepListener<>(productPanel, productBLL, "product", "edit"));
         productPanel.addPerformOperationBtnListener(new EditObjectListener<>(productPanel, productBLL));
      });

      tabbedPanel.addRemoveButtonListener(e -> {
         productPanel.clearFields();
         productPanel.removeAllEventListeners();
         productPanel.showObjectByIdPanel();
         productPanel.addNextStepBtnListener(new NextStepListener<>(productPanel, productBLL, "product", "remove"));
         productPanel.addPerformOperationBtnListener(new RemoveObjectListener<>(productPanel, productBLL, "product"));
      });

      tabbedPanel.addSearchButtonListener(e -> {
         productPanel.clearFields();
         productPanel.removeAllEventListeners();
         productPanel.showObjectByIdPanel();
         productPanel.addNextStepBtnListener(new NextStepListener<>(productPanel, productBLL, "product", "search"));
         productPanel.addPerformOperationBtnListener(new SearchObjectListener<>(productPanel, productBLL, "product"));
      });

      tabbedPanel.addViewButtonListener(e -> {
         productPanel.clearFields();
         productPanel.removeAllEventListeners();
         productPanel.showViewObjectsPanel();
      });
      tabbedPanel.addViewButtonListener(new ViewObjectsListener<>(productPanel, productBLL, "product"));
   }

   private void addOrderEventListeners() {
      TabbedPanel tabbedPanel = (TabbedPanel) mainFrame.getPanelContainer().getComponentAt(2);
      OrderPanel orderPanel = (OrderPanel)tabbedPanel.getActivityPanel();
      OrderBLL orderBLL = new OrderBLL();

      tabbedPanel.addCreateButtonListener(e -> {
         orderPanel.clearFields();
         orderPanel.removeAllEventListeners();
         orderPanel.showCreateObjectPanel();
         orderPanel.addPerformOperationBtnListener(new CreateObjectListener<>(orderPanel, orderBLL));
      });
      orderPanel.addPerformOperationBtnListener(new CreateObjectListener<>(orderPanel, orderBLL));

      tabbedPanel.addSearchButtonListener(e -> {
         orderPanel.clearFields();
         orderPanel.removeAllEventListeners();
         orderPanel.showObjectByIdPanel();
         orderPanel.addNextStepBtnListener(new NextStepListener<>(orderPanel, orderBLL, "order", "search"));
         orderPanel.addPerformOperationBtnListener(new SearchObjectListener<>(orderPanel, orderBLL, "order"));
      });

      tabbedPanel.addViewButtonListener(e -> {
         orderPanel.clearFields();
         orderPanel.removeAllEventListeners();
         orderPanel.showViewObjectsPanel();
      });
      tabbedPanel.addViewButtonListener(new ViewObjectsListener<>(orderPanel, orderBLL, "order"));
   }

}
