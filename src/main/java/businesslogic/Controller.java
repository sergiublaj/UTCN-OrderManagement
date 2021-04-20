package businesslogic;

import businesslogic.listeners.*;
import presentation.MainFrame;
import presentation.TabbedPanel;
import presentation.panels.ClientPanel;
import presentation.panels.OrderPanel;
import presentation.panels.ProductPanel;

/**
 * <p>Class used for adding button event listeners</p>
 */
public class Controller {
   private final MainFrame mainFrame;

   /**
    * Default constructor
    *
    * @param mainFrame application frame
    */
   public Controller(MainFrame mainFrame) {
      this.mainFrame = mainFrame;
      this.addEventListeners();
   }

   /**
    * <p>Adds the event listeners for buttons</p>
    */
   private void addEventListeners() {
      this.addTabbedPaneChangeListener();
      this.addClientEventListeners();
      this.addProductEventListeners();
      this.addOrderEventListeners();
   }

   /**
    * <p>On changing the tab, it removes previous components</p>
    */
   private void addTabbedPaneChangeListener() {
      mainFrame.getPanelContainer().addChangeListener(e -> {
         TabbedPanel tabbedPanel = (TabbedPanel) mainFrame.getPanelContainer().getSelectedComponent();
         tabbedPanel.getActivityPanel().removeAll();
         tabbedPanel.getActivityPanel().repaint();
         tabbedPanel.getActivityPanel().revalidate();
      });
   }

   /**
    * <p>Adds the events listeners for the buttons in clients panel</p>
    */
   private void addClientEventListeners() {
      TabbedPanel tabbedPanel = (TabbedPanel) mainFrame.getPanelContainer().getComponentAt(0);
      ClientPanel clientPanel = (ClientPanel) tabbedPanel.getActivityPanel();
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

   /**
    * <p>Adds the events listeners for the buttons in products panel</p>
    */
   private void addProductEventListeners() {
      TabbedPanel tabbedPanel = (TabbedPanel) mainFrame.getPanelContainer().getComponentAt(1);
      ProductPanel productPanel = (ProductPanel) tabbedPanel.getActivityPanel();
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

   /**
    * <p>Adds the events listeners for the buttons in orders panel</p>
    */
   private void addOrderEventListeners() {
      TabbedPanel tabbedPanel = (TabbedPanel) mainFrame.getPanelContainer().getComponentAt(2);
      OrderPanel orderPanel = (OrderPanel) tabbedPanel.getActivityPanel();
      OrderBLL orderBLL = new OrderBLL();

      tabbedPanel.addCreateButtonListener(e -> {
         orderPanel.clearFields();
         orderPanel.removeAllEventListeners();
         orderPanel.showCreateObjectPanel();
         orderPanel.addPerformOperationBtnListener(new CreateObjectListener<>(orderPanel, orderBLL));
      });
      orderPanel.addPerformOperationBtnListener(new CreateObjectListener<>(orderPanel, orderBLL));

      tabbedPanel.addViewButtonListener(e -> {
         orderPanel.clearFields();
         orderPanel.removeAllEventListeners();
         orderPanel.showViewObjectsPanel();
      });
      tabbedPanel.addViewButtonListener(new ViewObjectsListener<>(orderPanel, orderBLL, "order"));
   }

}
