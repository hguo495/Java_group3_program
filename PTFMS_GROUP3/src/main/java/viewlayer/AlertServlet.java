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
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");
            
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);
            
            // Get all alerts
            List<Alert> alerts = alertLogic.getAllAlerts();
            request.setAttribute("alerts", alerts);
            
            request.getRequestDispatcher("/WEB-INF/alerts/list.jsp").forward(request, response);
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles POST requests for adding alerts.
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
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");
            
            AlertBusinessLogic alertLogic = new AlertBusinessLogic(creds);
            
            if ("add".equals(action)) {
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
            }
            
            response.sendRedirect(request.getContextPath() + "/alerts");
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}