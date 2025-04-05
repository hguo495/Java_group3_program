package viewlayer;

import businesslayer.ComponentBusinessLogic;
import entity.Component;
import transferobjects.CredentialsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet for handling component-related HTTP requests.
 * 
 * @author Claude AI Assistant
 */
@WebServlet(name = "ComponentServlet", urlPatterns = {"/components"})
public class ComponentServlet extends HttpServlet {

    /**
     * Handles GET requests for components data.
     * 
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
            
            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            
            switch (action) {
                case "edit":
                    showEditForm(request, response, componentLogic);
                    break;
                case "view":
                    viewComponent(request, response, componentLogic);
                    break;
                case "list":
                default:
                    listComponents(request, response, componentLogic);
                    break;
            }
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles POST requests for component operations.
     * 
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
            
            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            
            switch (action) {
                case "add":
                    addComponent(request, response, componentLogic);
                    break;
                case "update":
                    updateComponent(request, response, componentLogic);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/components");
                    break;
            }
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Lists all components.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @param componentLogic the business logic for components
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void listComponents(HttpServletRequest request, HttpServletResponse response,
            ComponentBusinessLogic componentLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract filter parameters
        String vehicleId = request.getParameter("vehicleId");
        String type = request.getParameter("type");

        // Get all components
        List<Component> components = componentLogic.getAllComponents();
        
        // Apply filters if provided
        if (vehicleId != null && !vehicleId.isEmpty()) {
            components = components.stream()
                    .filter(c -> c.getVehicleId().equals(vehicleId))
                    .toList();
        }
        
        if (type != null && !type.isEmpty()) {
            components = components.stream()
                    .filter(c -> c.getType().contains(type))
                    .toList();
        }
        
        // Count components at risk
        long criticalCount = components.stream()
                .filter(c -> c.getWearPercentage() > 75 || c.getHoursUsed() > 1000)
                .count();
        
        request.setAttribute("components", components);
        request.setAttribute("criticalCount", criticalCount);
        request.getRequestDispatcher("/WEB-INF/components/list.jsp").forward(request, response);
    }
    
    /**
     * Shows the edit form for a component.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @param componentLogic the business logic for components
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response,
            ComponentBusinessLogic componentLogic) 
            throws ServletException, IOException, SQLException {
        
        String componentIdStr = request.getParameter("id");
        if (componentIdStr == null || componentIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/components");
            return;
        }
        
        try {
            int componentId = Integer.parseInt(componentIdStr);
            Component component = componentLogic.getComponentById(componentId);
            
            if (component == null) {
                request.setAttribute("error", "Component not found");
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("component", component);
            request.getRequestDispatcher("/WEB-INF/components/edit.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/components");
        }
    }
    
    /**
     * Views a component's details.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @param componentLogic the business logic for components
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void viewComponent(HttpServletRequest request, HttpServletResponse response,
            ComponentBusinessLogic componentLogic) 
            throws ServletException, IOException, SQLException {
        
        String componentIdStr = request.getParameter("id");
        if (componentIdStr == null || componentIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/components");
            return;
        }
        
        try {
            int componentId = Integer.parseInt(componentIdStr);
            Component component = componentLogic.getComponentById(componentId);
            
            if (component == null) {
                request.setAttribute("error", "Component not found");
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("component", component);
            request.getRequestDispatcher("/WEB-INF/components/view.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/components");
        }
    }
    
    /**
     * Adds a new component.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @param componentLogic the business logic for components
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void addComponent(HttpServletRequest request, HttpServletResponse response,
            ComponentBusinessLogic componentLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract form parameters
        String vehicleId = request.getParameter("vehicleId");
        String type = request.getParameter("type");
        String hoursUsedStr = request.getParameter("hoursUsed");
        String wearPercentageStr = request.getParameter("wearPercentage");
        String diagnosticStatus = request.getParameter("diagnosticStatus");
        
        // Validate input
        if (vehicleId == null || vehicleId.isEmpty() || 
            type == null || type.isEmpty() || 
            hoursUsedStr == null || hoursUsedStr.isEmpty() || 
            wearPercentageStr == null || wearPercentageStr.isEmpty() || 
            diagnosticStatus == null || diagnosticStatus.isEmpty()) {
            
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/components/add.jsp").forward(request, response);
            return;
        }
        
        try {
            double hoursUsed = Double.parseDouble(hoursUsedStr);
            double wearPercentage = Double.parseDouble(wearPercentageStr);
            
            Component component = new Component();
            component.setVehicleId(vehicleId);
            component.setType(type);
            component.setHoursUsed(hoursUsed);
            component.setWearPercentage(wearPercentage);
            component.setDiagnosticStatus(diagnosticStatus);
            
            componentLogic.addComponent(component);
            response.sendRedirect(request.getContextPath() + "/components");
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            request.getRequestDispatcher("/WEB-INF/components/add.jsp").forward(request, response);
        }
    }
    
    /**
     * Updates a component.
     * 
     * @param request the HTTP request
     * @param response the HTTP response
     * @param componentLogic the business logic for components
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void updateComponent(HttpServletRequest request, HttpServletResponse response,
            ComponentBusinessLogic componentLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract form parameters
        String componentIdStr = request.getParameter("componentId");
        String hoursUsedStr = request.getParameter("hoursUsed");
        String wearPercentageStr = request.getParameter("wearPercentage");
        String diagnosticStatus = request.getParameter("diagnosticStatus");
        
        // Validate input
        if (componentIdStr == null || componentIdStr.isEmpty() || 
            hoursUsedStr == null || hoursUsedStr.isEmpty() || 
            wearPercentageStr == null || wearPercentageStr.isEmpty() || 
            diagnosticStatus == null || diagnosticStatus.isEmpty()) {
            
            request.setAttribute("error", "All fields are required");
            response.sendRedirect(request.getContextPath() + "/components?action=edit&id=" + componentIdStr);
            return;
        }
        
        try {
            int componentId = Integer.parseInt(componentIdStr);
            double hoursUsed = Double.parseDouble(hoursUsedStr);
            double wearPercentage = Double.parseDouble(wearPercentageStr);
            
            // Get existing component
            Component component = componentLogic.getComponentById(componentId);
            if (component == null) {
                request.setAttribute("error", "Component not found");
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                return;
            }
            
            // Update fields
            component.setHoursUsed(hoursUsed);
            component.setWearPercentage(wearPercentage);
            component.setDiagnosticStatus(diagnosticStatus);
            
            // Update component
            componentLogic.updateComponent(component);
            
            // Add success message to session
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Component updated successfully. System will check if maintenance is needed.");
            
            // Redirect to component list
            response.sendRedirect(request.getContextPath() + "/components");
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format");
            response.sendRedirect(request.getContextPath() + "/components?action=edit&id=" + componentIdStr);
        }
    }
}