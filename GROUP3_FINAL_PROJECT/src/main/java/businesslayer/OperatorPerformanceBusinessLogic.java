package businesslayer;

import dataaccesslayer.OperatorPerformanceDAO;
import dataaccesslayer.OperatorPerformanceDAOImpl;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Business logic for processing and preparing operator performance metrics.
 * @author Claude AI
 */
public class OperatorPerformanceBusinessLogic {
    private OperatorPerformanceDAO performanceDAO;

    /**
     * Constructor that initializes the OperatorPerformanceDAO.
     * 
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database connection error occurs
     */
    public OperatorPerformanceBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.performanceDAO = new OperatorPerformanceDAOImpl(creds);
    }

    /**
     * Retrieves overall operator on-time performance for a given date range.
     * 
     * @param startDate the start date for performance calculation
     * @param endDate the end date for performance calculation
     * @return a list of operator on-time performance metrics
     * @throws SQLException if a database error occurs
     */
    public List<Map<String, Object>> getOperatorOnTimeRates(String startDate, String endDate) throws SQLException {
        // Use current date if no date provided
        if (startDate == null || endDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf.format(new Date());
            
            // Default to last 30 days if no start date
            long thirtyDaysAgo = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000);
            startDate = sdf.format(new Date(thirtyDaysAgo));
        }
        
        return performanceDAO.getOperatorOnTimeRates(startDate, endDate);
    }

    /**
     * Retrieves operator efficiency metrics for a given date range.
     * 
     * @param startDate the start date for performance calculation
     * @param endDate the end date for performance calculation
     * @return a list of operator efficiency metrics
     * @throws SQLException if a database error occurs
     */
    public List<Map<String, Object>> getOperatorEfficiencyMetrics(String startDate, String endDate) throws SQLException {
        // Use current date if no date provided
        if (startDate == null || endDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf.format(new Date());
            
            // Default to last 30 days if no start date
            long thirtyDaysAgo = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000);
            startDate = sdf.format(new Date(thirtyDaysAgo));
        }
        
        return performanceDAO.getOperatorEfficiencyMetrics(startDate, endDate);
    }

    /**
     * Retrieves detailed performance metrics for a specific operator.
     * 
     * @param operatorId the unique identifier of the operator
     * @param startDate the start date for performance calculation
     * @param endDate the end date for performance calculation
     * @return a map containing detailed performance metrics
     * @throws SQLException if a database error occurs
     */
    public Map<String, Object> getOperatorDetailedPerformance(int operatorId, String startDate, String endDate) throws SQLException {
        // Use current date if no date provided
        if (startDate == null || endDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf.format(new Date());
            
            // Default to last 30 days if no start date
            long thirtyDaysAgo = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000);
            startDate = sdf.format(new Date(thirtyDaysAgo));
        }
        
        return performanceDAO.getOperatorDetailedPerformance(operatorId, startDate, endDate);
    }
}