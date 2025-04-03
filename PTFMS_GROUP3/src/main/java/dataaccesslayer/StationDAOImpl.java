package dataaccesslayer;

import entity.Station;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for station operations.
 * @author Jinze Li
 */
public class StationDAOImpl implements StationDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public StationDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<Station> getAllStations() throws SQLException {
        List<Station> stations = new ArrayList<>();
        String query = "SELECT * FROM stations";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Station station = new Station();
                station.setStationId(rs.getString("station_id"));
                station.setName(rs.getString("name"));
                station.setLocation(rs.getString("location"));
                station.setRouteId(rs.getString("route_id"));
                stations.add(station);
            }
        }
        return stations;
    }

    @Override
    public Station getStationById(String stationId) throws SQLException {
        String query = "SELECT * FROM stations WHERE station_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, stationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Station station = new Station();
                    station.setStationId(rs.getString("station_id"));
                    station.setName(rs.getString("name"));
                    station.setLocation(rs.getString("location"));
                    station.setRouteId(rs.getString("route_id"));
                    return station;
                }
            }
        }
        return null;
    }

    @Override
    public List<Station> getStationsByRoute(String routeId) throws SQLException {
        List<Station> stations = new ArrayList<>();
        String query = "SELECT * FROM stations WHERE route_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, routeId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Station station = new Station();
                    station.setStationId(rs.getString("station_id"));
                    station.setName(rs.getString("name"));
                    station.setLocation(rs.getString("location"));
                    station.setRouteId(rs.getString("route_id"));
                    stations.add(station);
                }
            }
        }
        return stations;
    }

    @Override
    public void addStation(Station station) throws SQLException {
        String query = "INSERT INTO stations (station_id, name, location, route_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, station.getStationId());
            stmt.setString(2, station.getName());
            stmt.setString(3, station.getLocation());
            stmt.setString(4, station.getRouteId());
            stmt.executeUpdate();
        }
    }
}