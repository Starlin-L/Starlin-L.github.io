package enhanced_mvc;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * The Model class.
 * This class is responsible for all data management.
 * * ENHANCEMENT (Milestone 4):
 * Integrated SQLite Database. Data is now persistent.
 * - Creates 'destinations.db' if it doesn't exist.
 * - Loads data via JDBC instead of hard-coded strings.
 */
public class DestinationModel {

    private DefaultListModel<TextAndIcon> listModel;
    private Map<String, TextAndIcon> destinationMap;
    
    // Connection string for SQLite
    private static final String DB_URL = "jdbc:sqlite:destinations.db";

    public DestinationModel() {
        listModel = new DefaultListModel<>();
        destinationMap = new HashMap<>();
        
        // 1. Initialize the database (create table/data if missing)
        initDatabase();
        
        // 2. Load the data from the database into the App
        loadDestinationsFromDB();
    }

    /**
     * Initializes the database.
     * Creates the table if it doesn't exist and populates it with initial data.
     */
    private void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                // Create table
                String createTableSQL = "CREATE TABLE IF NOT EXISTS destinations ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "name TEXT NOT NULL, "
                        + "image_path TEXT, "
                        + "key_name TEXT NOT NULL UNIQUE)";
                
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createTableSQL);
                }

                // Check if empty, if so, seed data
                if (isTableEmpty(conn)) {
                    seedData(conn);
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Initialization Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Checks if the destinations table is empty.
     */
    private boolean isTableEmpty(Connection conn) throws SQLException {
        String countSQL = "SELECT COUNT(*) FROM destinations";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }

    /**
     * Inserts the initial 5 destinations into the database.
     * This replaces the hard-coded loading logic.
     */
    private void seedData(Connection conn) throws SQLException {
        String insertSQL = "INSERT INTO destinations(name, image_path, key_name) VALUES(?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            // Helper to insert row
            insertRow(pstmt, "1. Punta Cana, Dominican Republic - A Caribbean Paradise", "/resources/Dominican Republic.jpg", "Punta Cana");
            insertRow(pstmt, "2. San Juan, Puerto Rico - The Island of Enchantment", "/resources/Puerto Rico.jpg", "San Juan");
            insertRow(pstmt, "3. Havana, Cuba - The Heart of Cuba", "/resources/Cuba.jpg", "Havana");
            insertRow(pstmt, "4. Cancun, Mexico - A Tropical Escape", "/resources/Mexico.jpg", "Cancun");
            insertRow(pstmt, "5. Montego Bay, Jamaica - The Home of Reggae", "/resources/Jamaica.jpg", "Montego Bay");
        }
        System.out.println("Database seeded with initial data.");
    }

    private void insertRow(PreparedStatement pstmt, String name, String path, String key) throws SQLException {
        pstmt.setString(1, name);
        pstmt.setString(2, path);
        pstmt.setString(3, key);
        pstmt.executeUpdate();
    }

    /**
     * Loads all destinations from the database into the ListModel and HashMap.
     */
    private void loadDestinationsFromDB() {
        String query = "SELECT name, image_path, key_name FROM destinations";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String path = rs.getString("image_path");
                String key = rs.getString("key_name");
                
                // Add to internal data structures
                addDestination(name, path, key);
            }
        } catch (SQLException e) {
            System.err.println("Error loading data from DB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Helper method to add a new destination to the UI list and HashMap.
     */
    private void addDestination(String text, String iconPath, String keyName) {
        Icon icon = null;
        URL imgURL = getClass().getResource(iconPath);
        
        if (imgURL != null) {
            icon = new ImageIcon(imgURL);
        } else {
            // Fail silently or print error, but don't crash
            System.err.println("Could not find image: " + iconPath);
        }

        TextAndIcon tai = new TextAndIcon(text, icon);
        listModel.addElement(tai);
        destinationMap.put(keyName, tai);
    }

    public DefaultListModel<TextAndIcon> getListModel() {
        return listModel;
    }
    
    public TextAndIcon getDestinationByName(String name) {
        return destinationMap.get(name);
    }
}