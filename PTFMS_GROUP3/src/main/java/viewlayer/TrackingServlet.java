package viewlayer;

import businesslayer.TrackingBusinessLogic;
import entity.GPSTracking;
import entity.OperatorLog;
import transferobjects.CredentialsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Servlet for handling tracking-related HTTP requests.
 * @author Hongchen Guo
 */
@WebServlet(name = "TrackingServlet", urlPatterns = {"/tracking"})
public class TrackingServlet extends HttpServlet {
    
    /**
     * Handles GET requests for tracking data.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "gps";  // Default action
        }
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("root");
            creds.setPassword("1234");
            
            TrackingBusinessLogic trackingLogic = new TrackingBusinessLogic(creds);
            
            switch (action) {
                case "gps":
                    listGPSTracking(request, response, trackingLogic);
                    break;
                case "operator":
                    listOperatorLogs(request, response, trackingLogic);
                    break;
                case "stationReport":
                    stationReport(request, response, trackingLogic);
                    break;
                case "allStationReports":
                    allStationReports(request, response, trackingLogic);
                    break;
                default:
                    listGPSTracking(request, response, trackingLogic);
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles POST requests for adding tracking data.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");
            
            TrackingBusinessLogic trackingLogic = new TrackingBusinessLogic(creds);
            
            switch (action) {
                case "addGPS":
                    addGPSTracking(request, response, trackingLogic);
                    break;
                case "addOperatorLog":
                    addOperatorLog(request, response, trackingLogic);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/tracking");
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Lists all GPS tracking records.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param trackingLogic the business logic for tracking
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void listGPSTracking(HttpServletRequest request, HttpServletResponse response,
            TrackingBusinessLogic trackingLogic) 
            throws ServletException, IOException, SQLException {
        
        List<GPSTracking> gpsList = trackingLogic.getAllGPSTracking();
        request.setAttribute("deviceList", gpsList);
        request.getRequestDispatcher("/WEB-INF/tracking/gps.jsp").forward(request, response);
    }
    
    /**
     * Lists all operator logs.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param trackingLogic the business logic for tracking
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void listOperatorLogs(HttpServletRequest request, HttpServletResponse response,
            TrackingBusinessLogic trackingLogic) 
            throws ServletException, IOException, SQLException {
        
        List<OperatorLog> logs = trackingLogic.getAllOperatorLogs();
        request.setAttribute("logs", logs);
        request.getRequestDispatcher("/WEB-INF/tracking/operator.jsp").forward(request, response);
    }
    
    /**
     * Shows station arrival/departure report for a vehicle.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param trackingLogic the business logic for tracking
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void stationReport(HttpServletRequest request, HttpServletResponse response,
            TrackingBusinessLogic trackingLogic) 
            throws ServletException, IOException, SQLException {
        
        String vehicleId = request.getParameter("vehicleId");
        if (vehicleId == null || vehicleId.isEmpty()) {
            request.setAttribute("error", "Vehicle ID is required");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
        
        List<Map<String, Object>> report = trackingLogic.getVehicleStationReport(vehicleId);
        request.setAttribute("report", report);
        request.setAttribute("vehicleId", vehicleId);
        request.getRequestDispatcher("/WEB-INF/tracking/stationReport.jsp").forward(request, response);
    }
    
    /**
     * Shows station arrival/departure reports for all vehicles.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param trackingLogic the business logic for tracking
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void allStationReports(HttpServletRequest request, HttpServletResponse response,
            TrackingBusinessLogic trackingLogic) 
            throws ServletException, IOException, SQLException {
        
        Map<String, List<Map<String, Object>>> allReports = trackingLogic.getAllVehicleStationReports();
        request.setAttribute("allReports", allReports);
        request.getRequestDispatcher("/WEB-INF/tracking/allStationReports.jsp").forward(request, response);
    }
    
    /**
     * Adds a new GPS tracking record.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param trackingLogic the business logic for tracking
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void addGPSTracking(HttpServletRequest request, HttpServletResponse response,
            TrackingBusinessLogic trackingLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract form parameters
        String vehicleId = request.getParameter("vehicleId");
        String latitudeStr = request.getParameter("latitude");
        String longitudeStr = request.getParameter("longitude");
        String stationId = request.getParameter("stationId"); // Optional
        
        // Validate input
        if (vehicleId == null || vehicleId.isEmpty() || 
            latitudeStr == null || latitudeStr.isEmpty() ||
            longitudeStr == null || longitudeStr.isEmpty()) {
            
            request.setAttribute("error", "Vehicle ID, latitude, and longitude are required");
            request.getRequestDispatcher("/WEB-INF/tracking/addGPS.jsp").forward(request, response);
            return;
        }
        
        try {
            double latitude = Double.parseDouble(latitudeStr);
            double longitude = Double.parseDouble(longitudeStr);
            
            // Create and add GPS tracking record
            GPSTracking gps = new GPSTracking();
            gps.setVehicleId(vehicleId);
            gps.setLatitude(latitude);
            gps.setLongitude(longitude);
            gps.setTimestamp(String.valueOf(System.currentTimeMillis()));
            gps.setStationId(stationId); // May be null or empty
            
            trackingLogic.addGPSTracking(gps);
            
            response.sendRedirect(request.getContextPath() + "/tracking?action=gps");
            
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Invalid latitude or longitude format");
            request.getRequestDispatcher("/WEB-INF/tracking/addGPS.jsp").forward(request, response);
        }
    }
    
    /**
     * Adds a new operator log.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param trackingLogic the business logic for tracking
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void addOperatorLog(HttpServletRequest request, HttpServletResponse response,
            TrackingBusinessLogic trackingLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract form parameters
        String vehicleId = request.getParameter("vehicleId");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String eventType = request.getParameter("eventType");
        String operatorIdStr = request.getParameter("operatorId");
        
        // Validate input
        if (vehicleId == null || vehicleId.isEmpty() || 
            startTime == null || startTime.isEmpty() ||
            eventType == null || eventType.isEmpty() ||
            operatorIdStr == null || operatorIdStr.isEmpty()) {
            
            request.setAttribute("error", "All required fields must be filled out");
            request.getRequestDispatcher("/WEB-INF/tracking/addOperatorLog.jsp").forward(request, response);
            return;
        }
        
        try {
            int operatorId = Integer.parseInt(operatorIdStr);
            
            // Create and add operator log
            OperatorLog log = new OperatorLog();
            log.setVehicleId(vehicleId);
            log.setStartTime(startTime);
            log.setEndTime(endTime); // May be null or empty for ongoing events
            log.setEventType(eventType);
            log.setOperatorId(operatorId);
            
            trackingLogic.addOperatorLog(log);
            
            response.sendRedirect(request.getContextPath() + "/tracking?action=operator");
            
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Invalid operator ID format");
            request.getRequestDispatcher("/WEB-INF/tracking/addOperatorLog.jsp").forward(request, response);
        }
    }
}