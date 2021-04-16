package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {
   public static final String APP_TITLE = "Warehouse manager";

   private static final int APP_WIDTH = 1000;
   private static final int APP_HEIGHT = 600;
   private static final int IMAGE_WIDTH = 150;
   private static final int IMAGE_HEIGHT = 450;

   private final JPanel contentPanel = new JPanel();
   private final JPanel imagePanel = new JPanel();
   private final JPanel mainPanel = new JPanel();
   private final JTabbedPane panelContainer = new JTabbedPane();

   public MainFrame() {
      this.setUpFrame();
      this.setUpContentPanel();
      this.addLeftSideInformation();
      this.addMainPanelComponents();
   }

   private void setUpFrame() {
      this.setMinimumSize(new Dimension(APP_WIDTH, APP_HEIGHT));
      this.setResizable(false);
      this.setTitle(APP_TITLE);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   private void setUpContentPanel() {
      contentPanel.setLayout(new BorderLayout());
      contentPanel.setBackground(Color.LIGHT_GRAY);
      this.setContentPane(contentPanel);
   }

   private void addLeftSideInformation() {
      this.setUpImagePanel();
      this.addWarehouseImage();
      this.addAuthorInfo();
   }

   private void setUpImagePanel() {
      imagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
      imagePanel.setLayout(new BorderLayout());
      contentPanel.add(imagePanel, BorderLayout.WEST);
   }

   private void addWarehouseImage() {
      JLabel mathImage = new JLabel();
      mathImage.setIcon(new ImageIcon("src/main/resources/images/warehouse.jpg"));
      imagePanel.add(mathImage, BorderLayout.CENTER);
   }

   private void addAuthorInfo() {
      JLabel shortInfo = new JLabel("<html>Ver: 1.8_15.18<br>" +
         "@ author Blaj Sergiu<br>" +
         "@ group 30225</html>");
      shortInfo.setFont(new Font("JetBrains Mono", Font.PLAIN, 15));
      shortInfo.setBorder(new EmptyBorder(5, 5, 5, 5));
      shortInfo.setForeground(Color.WHITE);
      shortInfo.setBackground(Color.BLACK);
      shortInfo.setOpaque(true);
      imagePanel.add(shortInfo, BorderLayout.SOUTH);
   }

   private void addMainPanelComponents() {
      this.setUpAppPanel();
      this.addAppTitle();
      this.addPanelTabs();
   }

   private void setUpAppPanel() {
      mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
      mainPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
      contentPanel.add(mainPanel, BorderLayout.CENTER);
   }

   private void addAppTitle() {
      JLabel appTitle = new JLabel(APP_TITLE.toUpperCase());
      appTitle.setFont(new Font("Impact", Font.BOLD, 40));
      appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
      mainPanel.add(appTitle);
   }

   private void addPanelTabs() {
      panelContainer.addTab("Client", new TabbedPanel("client"));
      panelContainer.addTab("Product", new TabbedPanel("product"));
      panelContainer.addTab("Order", new TabbedPanel("order"));
      mainPanel.add(panelContainer);
   }

   public static JLabel createLabel(String message) {
      JLabel newLabel = new JLabel(message);
      newLabel.setHorizontalAlignment(SwingConstants.LEFT);
      newLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
      return newLabel;
   }

   public static JTextField createInput() {
      JTextField newInput = new JTextField(50);
      newInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
      return newInput;
   }

   public static void showAlert(String message) {
      JLabel alertMessage = new JLabel(message);
      alertMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
      JOptionPane.showMessageDialog(null, alertMessage);
   }

   public static int showConfirm(String message) {
      JLabel confirmMessage = new JLabel(message);
      confirmMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
      return JOptionPane.showConfirmDialog(null, confirmMessage, "Are you sure?", JOptionPane.YES_NO_OPTION);
   }

   public JTabbedPane getPanelContainer() {
      return panelContainer;
   }
}