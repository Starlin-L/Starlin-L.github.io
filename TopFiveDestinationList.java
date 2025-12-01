
/*
Developer: Starlin Luciano
Date: 1/20/2025
Purpose: This program displays a GUI with the top five travel destinations
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class TopFiveDestinationList {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TopDestinationListFrame topDestinationListFrame = new TopDestinationListFrame();
                topDestinationListFrame.setTitle("Top 5 Destination List");
                topDestinationListFrame.setVisible(true);
            }
        });
    }
}

class TopDestinationListFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<TextAndIcon> listModel;

    public TopDestinationListFrame() {
        super("Top Five Destination List");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 750);

        listModel = new DefaultListModel<>();

        // Update the list with top five chosen destinations
        addDestinationNameAndPicture("1. Punta Cana, Dominican Republic - A Caribbean Paradise", new ImageIcon(getClass().getResource("/resources/Dominican Republic.jpg"))); // Image by Ryancoke2020
        addDestinationNameAndPicture("2. San Juan, Puerto Rico - The Island of Enchantment", new ImageIcon(getClass().getResource("/resources/Puerto Rico.jpg"))); // Image by Luigi Rosa
        addDestinationNameAndPicture("3. Havana, Cuba - The Heart of Cuba", new ImageIcon(getClass().getResource("/resources/Cuba.jpg"))); // Image by Pedro Szekely
        addDestinationNameAndPicture("4. Cancun, Mexico - A Tropical Escape", new ImageIcon(getClass().getResource("/resources/Mexico.jpg"))); // Image by Kirt Edblom
        addDestinationNameAndPicture("5. Montego Bay, Jamaica - The Home of Reggae", new ImageIcon(getClass().getResource("/resources/Jamaica.jpg"))); // Image by Just a Brazilian man

        // Create a JList and add it to a JScrollPane
        JList<TextAndIcon> list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        // Create a custom cell renderer for the JList
        TextAndIconListCellRenderer renderer = new TextAndIconListCellRenderer(2);

        // Set the cell renderer for the JList
        list.setCellRenderer(renderer);

        // Set a custom color scheme
        list.setBackground(Color.black);
        list.setSelectionBackground(Color.GRAY);
        list.setSelectionForeground(Color.YELLOW);

        // Add the JScrollPane to the content pane
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add a label with the programmer's name
        JLabel nameLabel = new JLabel("Programmed by Starlin Luciano");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setBorder(new EmptyBorder(10, 0, 10, 0));  // Add padding to the label
        getContentPane().add(nameLabel, BorderLayout.SOUTH);
    }

    // Method to add a destination with title, description, and icon to the list model
    private void addDestinationNameAndPicture(String text, Icon icon) {
        TextAndIcon tai = new TextAndIcon(text, icon);
        listModel.addElement(tai);
    }
}

class TextAndIcon {
    private String text;
    private Icon icon;

    public TextAndIcon(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}

class TextAndIconListCellRenderer extends JLabel implements ListCellRenderer<Object> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private Border insideBorder;

    public TextAndIconListCellRenderer() {
        this(0, 0, 0, 0);
    }

    public TextAndIconListCellRenderer(int padding) {
        this(padding, padding, padding, padding);
    }

    public TextAndIconListCellRenderer(int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
        insideBorder = BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding);
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
        // The object from the combo box model MUST be a TextAndIcon.
        TextAndIcon tai = (TextAndIcon) value;

        // Sets text and icon on 'this' JLabel.
        setText(tai.getText());
        setIcon(tai.getIcon());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        Border outsideBorder;

        if (hasFocus) {
            outsideBorder = UIManager.getBorder("List.focusCellHighlightBorder");
        } else {
            outsideBorder = NO_FOCUS_BORDER;
        }

        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        setComponentOrientation(list.getComponentOrientation());
        setEnabled(list.isEnabled());
        setFont(list.getFont());

        return this;
    }

    // The following methods are overridden to be empty for performance reasons.
    public void validate() {}
    public void invalidate() {}
    public void repaint() {}
    public void revalidate() {}
    public void repaint(long tm, int x, int y, int width, int height) {}
    public void repaint(Rectangle r) {}
}