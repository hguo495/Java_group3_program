package dataaccesslayer;

import entity.Vehicle;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for vehicle operations.
 * @author Jinze Li
 */
public interface VehicleDAO {
    List<Vehicle> getAllVehicles() throws SQLException;
    Vehicle getVehicleById(String vehicleId) throws SQLException;
    void addVehicle(Vehicle vehicle) throws SQLException;
    void updateVehicle(Vehicle vehicle) throws SQLException;
    void deleteVehicle(String vehicleId) throws SQLException;
}