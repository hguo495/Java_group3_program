package dataaccesslayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * DAO interface for retrieving operator performance metrics.
 * @author Claude AI
 */
public interface OperatorPerformanceDAO {
    /**
     * Calculates on-time arrival rates for operators within a specified date range.
     * 
     * @param startDate the start date for performance calculation
     * @param endDate the end date for performance calculation
     * @return a list of maps containing operator performance metrics
     * @throws SQLException if a database error occurs
     */
    List<Map<String, Object>> getOperatorOnTimeRates(String startDate, String endDate) throws SQLException;
    
    /**
     * Calculates efficiency metrics for operators within a specified date range.
     * 
     * @param startDate the start date for performance calculation
     * @param endDate the end date for performance calculation
     * @return a list of maps containing operator efficiency metrics
     * @throws SQLException if a database error occurs
     */
    List<Map<String, Object>> getOperatorEfficiencyMetrics(String startDate, String endDate) throws SQLException;
    
    /**
     * Retrieves detailed performance breakdown for a specific operator.
     * 
     * @param operatorId the unique identifier of the operator
     * @param startDate the start date for performance calculation
     * @param endDate the end date for performance calculation
     * @return a map containing detailed performance metrics for the operator
     * @throws SQLException if a database error occurs
     */
    Map<String, Object> getOperatorDetailedPerformance(int operatorId, String startDate, String endDate) throws SQLException;
}