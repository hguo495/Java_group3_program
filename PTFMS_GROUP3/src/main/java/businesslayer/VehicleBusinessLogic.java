package businesslayer;

import dataaccesslayer.VehicleDAO;
import dataaccesslayer.VehicleDAOImpl;
import entity.Vehicle;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Business logic for vehicle operations using Builder and Simple Factory patterns.
 * @author Hongchen Guo
 */
public class VehicleBusinessLogic {
    private VehicleDAO vehicleDAO;
    private VehicleFactory vehicleFactory;

    /**
     * Constructor that initializes the DAO and factory.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public VehicleBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.vehicleDAO = new VehicleDAOImpl(creds);
        this.vehicleFactory = new VehicleFactory();
    }

    /**
     * Gets all vehicles.
     * @return a list of all vehicles
     * @throws SQLException if a database error occurs
     */
    public List<Vehicle> getAllVehicles() throws SQLException {
        return vehicleDAO.getAllVehicles();
    }

    /**
     * Gets a vehicle by ID.
     * @param vehicleId the ID of the vehicle
     * @return the vehicle, or null if not found
     * @throws SQLException if a database error occurs
     */
    public Vehicle getVehicleById(String vehicleId) throws SQLException {
        return vehicleDAO.getVehicleById(vehicleId);
    }

    /**
     * Adds a new vehicle using Builder and Factory.
     * @param type vehicle type
     * @param number vehicle number
     * @param fuelType fuel/energy type
     * @param consumptionRate consumption rate
     * @param maxPassengers maximum passengers
     * @param route assigned route
     * @throws SQLException if a database error occurs
     */
    public void addVehicle(String type, String number, String fuelType, double consumptionRate,
                           int maxPassengers, String route) throws SQLException {
        VehicleBuilder builder = vehicleFactory.createVehicle(type);
        Vehicle vehicle = builder.setVehicleId(number)
                                .setNumber(number)
                                .setFuelType(fuelType)
                                .setConsumptionRate(consumptionRate)
                                .setMaxPassengers(maxPassengers)
                                .setRoute(route)
                                .setStatus("Active")
                                .build();
        vehicleDAO.addVehicle(vehicle);
    }

    /**
     * Updates an existing vehicle.
     * @param vehicle the vehicle to update
     * @throws SQLException if a database error occurs
     */
    public void updateVehicle(Vehicle vehicle) throws SQLException {
        vehicleDAO.updateVehicle(vehicle);
    }

    /**
     * Deletes a vehicle by ID.
     * @param vehicleId the ID of the vehicle to delete
     * @throws SQLException if a database error occurs
     */
    public void deleteVehicle(String vehicleId) throws SQLException {
        vehicleDAO.deleteVehicle(vehicleId);
    }
}