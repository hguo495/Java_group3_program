package viewlayer;

import businesslayer.AlertBusinessLogic;
import businesslayer.OperatorPerformanceBusinessLogic;
import businesslayer.ReportBusinessLogic;
import businesslayer.TrackingBusinessLogic;
import businesslayer.VehicleBusinessLogic;
import entity.Alert;
import entity.Report;
import entity.Vehicle;
import transferobjects.CredentialsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Servlet for the main dashboard.
 * @author Hongchen Guo
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {
    
    /**
     * Handles GET requests for the dashboard.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    // Check if user is logged in
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    String userType = (String) session.getAttribute("userType");
    
    try {
        CredentialsDTO creds = new CredentialsDTO();
        creds.setUsername("cst8288");
        creds.setPassword("cst8288");
        
        // Get data for the dashboard
        VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
        AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);
        TrackingBusinessLogic trackingLogic = new TrackingBusinessLogic(creds);
        ReportBusinessLogic reportLogic = new ReportBusinessLogic(creds);
        
        // Get list of vehicles (both Manager and Operator can see)
        List<Vehicle> vehicles = vehicleLogic.getAllVehicles();
        request.setAttribute("vehicles", vehicles);
        
        // Get GPS tracking data (both Manager and Operator can see)
        Map<String, List<Map<String, Object>>> stationReports = 
                trackingLogic.getAllVehicleStationReports();
        request.setAttribute("stationReports", stationReports);
        
        // Manager-specific data
        if ("Manager".equals(userType)) {
            // Get pending alerts (only for Manager)
            List<Alert> alerts = alertLogic.getAllAlerts();
            request.setAttribute("alerts", alerts);
            
            // Get reports (only for Manager)
            List<Report> reports = reportLogic.getAllReports();
            request.setAttribute("reports", reports);
            
            // Add operator performance metrics (only for Manager)
            OperatorPerformanceBusinessLogic performanceLogic = new OperatorPerformanceBusinessLogic(creds);
            List<Map<String, Object>> onTimeRates = performanceLogic.getOperatorOnTimeRates(null, null);
            request.setAttribute("onTimeRates", onTimeRates);
        }
        
        // Forward to the appropriate dashboard
        String jspPage = "Manager".equals(userType) ? 
                "/WEB-INF/dashboard/manager.jsp" : "/WEB-INF/dashboard/operator.jsp";
        request.getRequestDispatcher(jspPage).forward(request, response);
        
    } catch (SQLException ex) {
        request.setAttribute("error", "Database error: " + ex.getMessage());
        request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
    }
}
}