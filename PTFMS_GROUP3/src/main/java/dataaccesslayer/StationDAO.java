package dataaccesslayer;

import entity.Station;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for station operations.
 * @author Jinze Li
 */
public interface StationDAO {
    List<Station> getAllStations() throws SQLException;
    Station getStationById(String stationId) throws SQLException;
    List<Station> getStationsByRoute(String routeId) throws SQLException;
    void addStation(Station station) throws SQLException;
}