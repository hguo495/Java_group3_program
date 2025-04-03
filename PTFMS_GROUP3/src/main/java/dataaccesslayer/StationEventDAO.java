package dataaccesslayer;

import entity.StationEvent;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for station event operations.
 * @author Jinze Li
 */
public interface StationEventDAO {
    List<StationEvent> getAllStationEvents() throws SQLException;
    List<StationEvent> getStationEventsByVehicle(String vehicleId) throws SQLException;
    List<StationEvent> getStationEventsByStation(String stationId) throws SQLException;
    void addStationEvent(StationEvent event) throws SQLException;
}