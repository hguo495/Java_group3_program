package dataaccesslayer;

import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of OperatorPerformanceDAO for retrieving operator performance metrics.
 * @author Claude AI
 */
public class OperatorPerformanceDAOImpl implements OperatorPerformanceDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * 
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database connection error occurs
     */
    public OperatorPerformanceDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<Map<String, Object>> getOperatorOnTimeRates(String startDate, String endDate) throws SQLException {
        List<Map<String, Object>> operatorOnTimeRates = new ArrayList<>();
        
        String query = "SELECT " +
            "operator_id, " +
            "COUNT(*) AS total_trips, " +
            "SUM(CASE WHEN is_on_time = true THEN 1 ELSE 0 END) AS on_time_trips, " +
            "(SUM(CASE WHEN is_on_time = true THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS on_time_percentage " +
            "FROM operator_trips " +
            "WHERE trip_date BETWEEN ? AND ? " +
            "GROUP BY operator_id";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> operatorPerformance = new HashMap<>();
                    operatorPerformance.put("operatorId", rs.getInt("operator_id"));
                    operatorPerformance.put("totalTrips", rs.getInt("total_trips"));
                    operatorPerformance.put("onTimeTrips", rs.getInt("on_time_trips"));
                    operatorPerformance.put("onTimePercentage", rs.getDouble("on_time_percentage"));
                    
                    operatorOnTimeRates.add(operatorPerformance);
                }
            }
        }
        
        return operatorOnTimeRates;
    }

    @Override
    public List<Map<String, Object>> getOperatorEfficiencyMetrics(String startDate, String endDate) throws SQLException {
        List<Map<String, Object>> operatorEfficiencyMetrics = new ArrayList<>();
        
        String query = "SELECT " +
            "operator_id, " +
            "COUNT(*) AS total_trips, " +
            "AVG(trip_duration) AS avg_trip_duration, " +
            "AVG(idle_time) AS avg_idle_time, " +
            "SUM(distance_traveled) AS total_distance " +
            "FROM operator_trips " +
            "WHERE trip_date BETWEEN ? AND ? " +
            "GROUP BY operator_id";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> operatorEfficiency = new HashMap<>();
                    operatorEfficiency.put("operatorId", rs.getInt("operator_id"));
                    operatorEfficiency.put("totalTrips", rs.getInt("total_trips"));
                    operatorEfficiency.put("avgTripDuration", rs.getDouble("avg_trip_duration"));
                    operatorEfficiency.put("avgIdleTime", rs.getDouble("avg_idle_time"));
                    operatorEfficiency.put("totalDistance", rs.getDouble("total_distance"));
                    
                    operatorEfficiencyMetrics.add(operatorEfficiency);
                }
            }
        }
        
        return operatorEfficiencyMetrics;
    }

    @Override
    public Map<String, Object> getOperatorDetailedPerformance(int operatorId, String startDate, String endDate) throws SQLException {
        Map<String, Object> detailedPerformance = new HashMap<>();
        
        String onTimeQuery = "SELECT " +
            "COUNT(*) AS total_trips, " +
            "SUM(CASE WHEN is_on_time = true THEN 1 ELSE 0 END) AS on_time_trips, " +
            "(SUM(CASE WHEN is_on_time = true THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) AS on_time_percentage " +
            "FROM operator_trips " +
            "WHERE operator_id = ? AND trip_date BETWEEN ? AND ?";
        
        String efficiencyQuery = "SELECT " +
            "AVG(trip_duration) AS avg_trip_duration, " +
            "AVG(idle_time) AS avg_idle_time, " +
            "SUM(distance_traveled) AS total_distance " +
            "FROM operator_trips " +
            "WHERE operator_id = ? AND trip_date BETWEEN ? AND ?";
        
        // Get on-time performance
        try (PreparedStatement stmt = connection.prepareStatement(onTimeQuery)) {
            stmt.setInt(1, operatorId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    detailedPerformance.put("totalTrips", rs.getInt("total_trips"));
                    detailedPerformance.put("onTimeTrips", rs.getInt("on_time_trips"));
                    detailedPerformance.put("onTimePercentage", rs.getDouble("on_time_percentage"));
                }
            }
        }
        
        // Get efficiency metrics
        try (PreparedStatement stmt = connection.prepareStatement(efficiencyQuery)) {
            stmt.setInt(1, operatorId);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    detailedPerformance.put("avgTripDuration", rs.getDouble("avg_trip_duration"));
                    detailedPerformance.put("avgIdleTime", rs.getDouble("avg_idle_time"));
                    detailedPerformance.put("totalDistance", rs.getDouble("total_distance"));
                }
            }
        }
        
        return detailedPerformance;
    }
}