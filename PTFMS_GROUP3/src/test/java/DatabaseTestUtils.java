/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MM
 */
import transferobjects.CredentialsDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class to clean up test data from database before each test.
 */
public class DatabaseTestUtils {

    public static void cleanTestData(CredentialsDTO creds) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ptfms", creds.getUsername(), creds.getPassword());
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM alerts WHERE vehicle_id LIKE 'TEST_%' OR vehicle_id LIKE 'BUS998' OR vehicle_id LIKE 'BUS999'");
            stmt.executeUpdate("DELETE FROM components WHERE vehicle_id LIKE 'TEST_%' OR vehicle_id LIKE 'BUS998' OR vehicle_id LIKE 'BUS999'");
            stmt.executeUpdate("DELETE FROM vehicles WHERE vehicle_id LIKE 'TEST_%' OR vehicle_id LIKE 'BUS998' OR vehicle_id LIKE 'BUS999'");

        } catch (SQLException e) {
            System.err.println("❌ Failed to clean test data: " + e.getMessage());
        }
    }
}
