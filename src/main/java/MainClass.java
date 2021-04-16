import presentation.Controller;
import presentation.MainFrame;

public class MainClass {
   public static void main(String[] args) {
      MainFrame mainFrame = new MainFrame();
      Controller mainController = new Controller(mainFrame);
      mainFrame.setVisible(true);
   }
}
