/**
 * This class tests the vehicle registration flow from the servlet layer
 * to the business logic and database layers.
 * 
 * Author: Jinze Li
 */
import businesslayer.VehicleBusinessLogic;
import entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transferobjects.CredentialsDTO;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class VehicleServletIntegrationTest {
    private CredentialsDTO creds;
    
    @BeforeEach
    public void setup() {
        creds = new CredentialsDTO();
        creds.setUsername("cst8288");
        creds.setPassword("cst8288");
        
        // Ensure the test data is properly cleaned
        cleanSpecificTestVehicle(); 
        DatabaseTestUtils.cleanTestData(creds);
    }
    
    /**
     * Clean up the specific test vehicle directly using SQL
     * This ensures it's removed even if the business logic delete fails
     */
    private void cleanSpecificTestVehicle() {
        try {
            // Get a connection using DataSource
            dataaccesslayer.DataSource ds = dataaccesslayer.DataSource.getInstance(creds);
            Connection conn = ds.getConnection();
            
            // Delete the test vehicle directly with SQL
            String sql = "DELETE FROM vehicles WHERE number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "B999");
            stmt.executeUpdate();
            
            // Close resources
            stmt.close();
            // Don't close the connection as it's managed by DataSource
        } catch (SQLException e) {
            // Log the error but continue with the test
            System.err.println("Error cleaning test vehicle: " + e.getMessage());
        }
    }
    
    @Test
    public void testVehicleRegistrationFlow() {
        try {
            // Generate a unique vehicle number to prevent collisions
            String testNumber = "B999-" + UUID.randomUUID().toString().substring(0, 8);
            
            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
            
            // Register new vehicle
            vehicleLogic.addVehicle("Diesel Bus", testNumber, "Diesel", 0.5, 60, "Test Route");
            
            // Retrieve all vehicles and verify the test vehicle is present
            List<Vehicle> vehicles = vehicleLogic.getAllVehicles();
            Vehicle found = vehicles.stream()
                .filter(v -> testNumber.equals(v.getNumber()))
                .findFirst()
                .orElse(null);
            
            assertNotNull(found, "Vehicle should exist in the database");
            assertEquals("Diesel", found.getFuelType());
            assertEquals("Test Route", found.getRoute());
            
            // Clean up after test
            try {
                vehicleLogic.deleteVehicle(testNumber);
            } catch (Exception e) {
                System.err.println("Warning: Could not delete test vehicle: " + e.getMessage());
            }
            
        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }
}