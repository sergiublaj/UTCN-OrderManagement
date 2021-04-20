package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * <p>Main application frame</p>
 */
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

   /**
    * <p>Default constructor</p>
    */
   public MainFrame() {
      this.setUpFrame();
      this.setUpContentPanel();
      this.addLeftSideInformation();
      this.addMainPanelComponents();
   }

   /**
    * <p>Sets up the frame</p>
    */
   private void setUpFrame() {
      this.setMinimumSize(new Dimension(APP_WIDTH, APP_HEIGHT));
      this.setResizable(false);
      this.setTitle(APP_TITLE);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
   }

   /**
    * <p>Sets up the frame panel</p>
    */
   private void setUpContentPanel() {
      contentPanel.setLayout(new BorderLayout());
      contentPanel.setBackground(Color.LIGHT_GRAY);
      this.setContentPane(contentPanel);
   }

   /**
    * <p>Adds information on the left side of GUI</p>
    */
   private void addLeftSideInformation() {
      this.setUpImagePanel();
      this.addWarehouseImage();
      this.addAuthorInfo();
   }

   /**
    * <p>Sets up the image panel</p>
    */
   private void setUpImagePanel() {
      imagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
      imagePanel.setLayout(new BorderLayout());
      contentPanel.add(imagePanel, BorderLayout.WEST);
   }

   /**
    * <p>Adds the warehouse image</p>
    */
   private void addWarehouseImage() {
      JLabel mathImage = new JLabel();
      mathImage.setIcon(new ImageIcon("src/main/resources/images/warehouse.jpg"));
      imagePanel.add(mathImage, BorderLayout.CENTER);
   }

   /**
    * <p>Adds information about the developer</p>
    */
   private void addAuthorInfo() {
      JLabel shortInfo = new JLabel("<html>Ver: 1.14_23.31<br>" +
         "@ author Blaj Sergiu<br>" +
         "@ group 30225</html>");
      shortInfo.setFont(new Font("JetBrains Mono", Font.PLAIN, 15));
      shortInfo.setBorder(new EmptyBorder(5, 5, 5, 5));
      shortInfo.setForeground(Color.WHITE);
      shortInfo.setBackground(Color.BLACK);
      shortInfo.setOpaque(true);
      imagePanel.add(shortInfo, BorderLayout.SOUTH);
   }

   /**
    * <p>Adds components to main panel</p>
    */
   private void addMainPanelComponents() {
      this.setUpAppPanel();
      this.addAppTitle();
      this.addPanelTabs();
   }

   /**
    * <p>Sets up main panel</p>
    */
   private void setUpAppPanel() {
      mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
      mainPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
      contentPanel.add(mainPanel, BorderLayout.CENTER);
   }

   /**
    * <p>Adds application title</p>
    */
   private void addAppTitle() {
      JLabel appTitle = new JLabel(APP_TITLE.toUpperCase());
      appTitle.setFont(new Font("Impact", Font.BOLD, 40));
      appTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
      mainPanel.add(appTitle);
   }

   /**
    * <p>Adds GUI tabbed panels</p>
    */
   private void addPanelTabs() {
      panelContainer.addTab("Client", new TabbedPanel("client"));
      panelContainer.addTab("Product", new TabbedPanel("product"));
      panelContainer.addTab("Order", new TabbedPanel("order"));
      mainPanel.add(panelContainer);
   }

   /**
    * <p>Creates a label with a given message</p>
    * @param message label message
    * @return label
    */
   public static JLabel createLabel(String message) {
      JLabel newLabel = new JLabel(message);
      newLabel.setHorizontalAlignment(SwingConstants.LEFT);
      newLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
      return newLabel;
   }

   /**
    * <p>Creates an input field</p>
    * @return input field
    */
   public static JTextField createInput() {
      JTextField newInput = new JTextField(50);
      newInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
      return newInput;
   }

   /**
    * <p>Creates a combobox of integers</p>
    * @return integer combobox
    */
   public static JComboBox<String> createComboBox() {
      JComboBox<String> newComboBox = new JComboBox<>();
      newComboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
      return newComboBox;
   }

   /**
    * <p>Sets up the panel title</p>
    * @param panelTitle label to make the changes
    */
   public static void initializeTitle(JLabel panelTitle) {
      panelTitle.setAlignmentX(CENTER_ALIGNMENT);
      panelTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
      panelTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
   }

   /**
    * <p>Sets up the buttons</p>
    * @param crtButton current button
    */
   public static void initializeButton(JButton crtButton) {
      crtButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
      crtButton.setAlignmentX(CENTER_ALIGNMENT);
      crtButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
   }

   /**
    * <p>Displays an alert with the given message</p>
    * @param message alert message
    */
   public static void showAlert(String message) {
      JLabel alertMessage = new JLabel(message);
      alertMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
      JOptionPane.showMessageDialog(null, alertMessage);
   }

   /**
    * <p>Displays a confirmation frame with a given message</p>
    * @param message confirmation message
    * @return chosen option
    */
   public static int showConfirm(String message) {
      JLabel confirmMessage = new JLabel(message);
      confirmMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
      return JOptionPane.showConfirmDialog(null, confirmMessage, "Are you sure?", JOptionPane.YES_NO_OPTION);
   }

   /**
    * <p>Returns the current panel of frame</p>
    * @return panel
    */
   public JTabbedPane getPanelContainer() {
      return panelContainer;
   }

   /**
    * <p>Adds listener to tabbed pane to remove all components</p>
    * @param crtListener current listener
    */
   public void addChangeTabListener(ChangeListener crtListener) {panelContainer.addChangeListener(crtListener);}
}