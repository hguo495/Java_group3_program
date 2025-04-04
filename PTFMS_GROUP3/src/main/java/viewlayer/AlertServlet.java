package viewlayer;

import businesslayer.AlertBusinessLogic;
import entity.Alert;
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

/**
 * Servlet for handling alert-related HTTP requests.
 * @author Hongchen Guo
 */
@WebServlet(name = "AlertServlet", urlPatterns = {"/alerts"})
public class AlertServlet extends HttpServlet {
    
    /**
     * Handles GET requests for alert data.
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
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "list"; // Default action
        }
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");
            
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);
            
            switch (action) {
                case "view":
                    viewAlert(request, response, alertLogic);
                    break;
                case "list":
                default:
                    listAlerts(request, response, alertLogic);
                    break;
            }
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles POST requests for adding or resolving alerts.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");
            
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);
            
            switch (action) {
                case "add":
                    addAlert(request, response, alertLogic);
                    break;
                case "resolve":
                    resolveAlert(request, response, alertLogic);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/alerts");
                    break;
            }
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Lists all alerts with pagination and filtering.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param alertLogic the business logic for alerts
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void listAlerts(HttpServletRequest request, HttpServletResponse response,
            AlertBusinessLogic alertLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract filter parameters
        String type = request.getParameter("type");
        String status = request.getParameter("status");
        String vehicleId = request.getParameter("vehicleId");

        // Fetch filtered alerts
        List<Alert> alerts = alertLogic.getFilteredAlerts(type, status, vehicleId);
        request.setAttribute("alerts", alerts);

        // Build query string for pagination links
        StringBuilder queryString = new StringBuilder();
        if (type != null && !type.isEmpty()) {
            queryString.append("type=").append(type).append("&");
        }
        if (status != null && !status.isEmpty()) {
            queryString.append("status=").append(status).append("&");
        }
        if (vehicleId != null && !vehicleId.isEmpty()) {
            queryString.append("vehicleId=").append(vehicleId).append("&");
        }

        // For pagination (simplified for now)
        request.setAttribute("currentPage", 1);
        request.setAttribute("totalPages", 1);
        request.setAttribute("queryString", queryString.toString());

        request.getRequestDispatcher("/WEB-INF/alerts/list.jsp").forward(request, response);
    }
    
    /**
     * Views a specific alert.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param alertLogic the business logic for alerts
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void viewAlert(HttpServletRequest request, HttpServletResponse response,
            AlertBusinessLogic alertLogic) 
            throws ServletException, IOException, SQLException {
        
        String alertId = request.getParameter("id");
        if (alertId == null || alertId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/alerts");
            return;
        }
        
        Alert alert = alertLogic.getAlertById(Integer.parseInt(alertId));
        if (alert == null) {
            request.setAttribute("error", "Alert not found");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
        
        request.setAttribute("alert", alert);
        request.getRequestDispatcher("/WEB-INF/alerts/view.jsp").forward(request, response);
    }
    
    /**
     * Adds a new alert.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param alertLogic the business logic for alerts
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void addAlert(HttpServletRequest request, HttpServletResponse response,
            AlertBusinessLogic alertLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract form parameters
        String type = request.getParameter("type");
        String vehicleId = request.getParameter("vehicleId");
        String message = request.getParameter("message");
        
        // Validate input
        if (type == null || type.isEmpty() || 
            vehicleId == null || vehicleId.isEmpty() || 
            message == null || message.isEmpty()) {
            
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/alerts/add.jsp").forward(request, response);
            return;
        }
        
        // Add alert
        alertLogic.addAlert(type, vehicleId, message);
        response.sendRedirect(request.getContextPath() + "/alerts");
    }
    
    /**
     * Resolves an alert by updating its status to "Resolved".
     * @param request the HTTP request
     * @param response the HTTP response
     * @param alertLogic the business logic for alerts
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void resolveAlert(HttpServletRequest request, HttpServletResponse response,
            AlertBusinessLogic alertLogic) 
            throws ServletException, IOException, SQLException {
        
        String alertId = request.getParameter("id");
        if (alertId == null || alertId.isEmpty()) {
            request.setAttribute("error", "Invalid alert ID");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
        
        try {
            alertLogic.resolveAlert(Integer.parseInt(alertId));
            response.sendRedirect(request.getContextPath() + "/alerts");
        } catch (SQLException ex) {
            request.setAttribute("error", "Failed to resolve alert: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}