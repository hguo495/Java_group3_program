package dataaccesslayer;

import entity.GPSTracking;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for GPS tracking operations.
 * @author Jinze Li
 */
public interface GPSTrackingDAO {
    List<GPSTracking> getAllGPSTracking() throws SQLException;
    List<GPSTracking> getRecentTrackingByVehicle(String vehicleId, int limit) throws SQLException;
    void addGPSTracking(GPSTracking gps) throws SQLException;
}