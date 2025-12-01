package enhanced_mvc;
import javax.swing.SwingUtilities;

/**
 * Main application class (the "M" in MVC).
 * This class is responsible for creating and launching the application.
 * It creates the Model, View, and Controller and links them together.
 */
public class TopFiveApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create the Model, View, and Controller
                DestinationModel model = new DestinationModel();
                DestinationView view = new DestinationView();
                
                // The Controller links the Model and View
                new DestinationController(model, view);
                
                // Make the application visible
                view.setVisible(true);
            }
        });
    }
}