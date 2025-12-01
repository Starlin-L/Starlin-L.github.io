package enhanced_mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;

/**
 * The View class.
 * This class is responsible for the user interface (GUI).
 * It creates all the Swing components (window, list, labels) but contains no data logic.
 * It exposes methods for the Controller to interact with.
 */
public class DestinationView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JList<TextAndIcon> list;
    private DefaultListModel<TextAndIcon> listModel;

    public DestinationView() {
        super("Top Five Destination List - MVC Enhanced");

        // Basic window setup
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 750);
        
        // The View creates its components, but the Model will provide the data
        listModel = new DefaultListModel<>(); // This will be set by the Controller
        list = new JList<>(listModel);
        
        // Setup the list visual properties
        setupListView();

        JScrollPane scrollPane = new JScrollPane(list);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add the footer label
        JLabel nameLabel = new JLabel("Programmed by Starlin Luciano (MVC Version)");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        getContentPane().add(nameLabel, BorderLayout.SOUTH);
    }

    /**
     * Sets up the visual styles for the JList.
     */
    private void setupListView() {
        // Create and set the custom renderer
        // This is the corrected line:
        ListCellRenderer<Object> renderer = new TextAndIconListCellRenderer(2);
        list.setCellRenderer(renderer);

        // Set custom color scheme
        list.setBackground(Color.black);
        list.setSelectionBackground(Color.GRAY);
        list.setSelectionForeground(Color.YELLOW);
    }

    /**
     * Public method for the Controller to set the list's data model.
     * @param model The list model from the Model class.
     */
    public void setListModel(DefaultListModel<TextAndIcon> model) {
        this.listModel = model;
        list.setModel(model);
    }

    public JList<TextAndIcon> getJList() {
        return list;
    }
}