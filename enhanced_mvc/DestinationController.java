package enhanced_mvc;

/**
 * The Controller class.
 * This class acts as the intermediary between the Model and the View.
 * It contains the application logic.
 * It listens for user actions from the View and updates the Model.
 * It takes data from the Model and tells the View how to display it.
 */
public class DestinationController {

    private DestinationModel model;
    private DestinationView view;

    public DestinationController(DestinationModel model, DestinationView view) {
        this.model = model;
        this.view = view;

        // --- This is the core logic ---
        // 1. Get the data from the Model.
        // 2. Give that data to the View to display.
        this.view.setListModel(this.model.getListModel());
    }
}