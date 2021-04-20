import businesslogic.Controller;
import presentation.MainFrame;

/**
 * <p>Class that executes the application</p>
 */
public class MainClass {
   /**
    * <p>Runs the application</p>
    * @param args void
    */
   public static void main(String[] args) {
      MainFrame mainFrame = new MainFrame();
      Controller mainController = new Controller(mainFrame);
      mainFrame.setVisible(true);
   }
}
