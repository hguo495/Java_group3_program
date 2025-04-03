package viewlayer;

import businesslayer.VehicleBusinessLogic;
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

/**
 * Servlet for handling vehicle-related HTTP requests.
 * @author Hongchen Guo
 */
@WebServlet(name = "VehicleServlet", urlPatterns = {"/vehicles"})
public class VehicleServlet extends HttpServlet {
    
    /**
     * Handles GET requests for vehicle data.
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
            action = "list";  // Default action
        }
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");
            
            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
            
            switch (action) {
                case "list":
                    listVehicles(request, response, vehicleLogic);
                    break;
                case "view":
                    viewVehicle(request, response, vehicleLogic);
                    break;
                case "showAddForm":
                    showAddForm(request, response);
                    break;
                case "showEditForm":
                    showEditForm(request, response, vehicleLogic);
                    break;
                default:
                    listVehicles(request, response, vehicleLogic);
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles POST requests for adding/updating vehicles.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in and is a Manager
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String userType = (String) session.getAttribute("userType");
        if (!"Manager".equals(userType)) {
            request.setAttribute("error", "Only managers can add or update vehicles");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");
            
            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
            
            switch (action) {
                case "add":
                    addVehicle(request, response, vehicleLogic);
                    break;
                case "update":
                    updateVehicle(request, response, vehicleLogic);
                    break;
                case "delete":
                    deleteVehicle(request, response, vehicleLogic);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/vehicles");
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Lists all vehicles.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param vehicleLogic the business logic for vehicles
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void listVehicles(HttpServletRequest request, HttpServletResponse response,
            VehicleBusinessLogic vehicleLogic) 
            throws ServletException, IOException, SQLException {
        
        List<Vehicle> vehicles = vehicleLogic.getAllVehicles();
        request.setAttribute("vehicles", vehicles);
        request.getRequestDispatcher("/WEB-INF/vehicles/list.jsp").forward(request, response);
    }
    
    /**
     * Views a specific vehicle.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param vehicleLogic the business logic for vehicles
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void viewVehicle(HttpServletRequest request, HttpServletResponse response,
            VehicleBusinessLogic vehicleLogic) 
            throws ServletException, IOException, SQLException {
        
        String vehicleId = request.getParameter("id");
        if (vehicleId == null || vehicleId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/vehicles");
            return;
        }
        
        Vehicle vehicle = vehicleLogic.getVehicleById(vehicleId);
        if (vehicle == null) {
            request.setAttribute("error", "Vehicle not found");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
        
        request.setAttribute("vehicle", vehicle);
        request.getRequestDispatcher("/WEB-INF/vehicles/view.jsp").forward(request, response);
    }
    
    /**
     * Shows the form for adding a new vehicle.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/vehicles/add.jsp").forward(request, response);
    }
    
    /**
     * Shows the form for editing a vehicle.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param vehicleLogic the business logic for vehicles
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response,
            VehicleBusinessLogic vehicleLogic) 
            throws ServletException, IOException, SQLException {
        
        String vehicleId = request.getParameter("id");
        if (vehicleId == null || vehicleId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/vehicles");
            return;
        }
        
        Vehicle vehicle = vehicleLogic.getVehicleById(vehicleId);
        if (vehicle == null) {
            request.setAttribute("error", "Vehicle not found");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
        
        request.setAttribute("vehicle", vehicle);
        request.getRequestDispatcher("/WEB-INF/vehicles/edit.jsp").forward(request, response);
    }
    
    /**
     * Adds a new vehicle.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param vehicleLogic the business logic for vehicles
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void addVehicle(HttpServletRequest request, HttpServletResponse response,
            VehicleBusinessLogic vehicleLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract form parameters
        String type = request.getParameter("type");
        String number = request.getParameter("number");
        String fuelType = request.getParameter("fuelType");
        String consumptionRateStr = request.getParameter("consumptionRate");
        String maxPassengersStr = request.getParameter("maxPassengers");
        String route = request.getParameter("route");
        
        // Validate input
        if (type == null || type.isEmpty() || 
            number == null || number.isEmpty() || 
            fuelType == null || fuelType.isEmpty() ||
            consumptionRateStr == null || consumptionRateStr.isEmpty() ||
            maxPassengersStr == null || maxPassengersStr.isEmpty() ||
            route == null || route.isEmpty()) {
            
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/vehicles/add.jsp").forward(request, response);
            return;
        }
        
        try {
            double consumptionRate = Double.parseDouble(consumptionRateStr);
            int maxPassengers = Integer.parseInt(maxPassengersStr);
            
            // Add vehicle
            vehicleLogic.addVehicle(type, number, fuelType, consumptionRate, maxPassengers, route);
            
            response.sendRedirect(request.getContextPath() + "/vehicles");
            
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Invalid number format");
            request.getRequestDispatcher("/WEB-INF/vehicles/add.jsp").forward(request, response);
        }
    }
    
    /**
     * Updates an existing vehicle.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param vehicleLogic the business logic for vehicles
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void updateVehicle(HttpServletRequest request, HttpServletResponse response,
            VehicleBusinessLogic vehicleLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract form parameters
        String vehicleId = request.getParameter("vehicleId");
        String type = request.getParameter("type");
        String number = request.getParameter("number");
        String fuelType = request.getParameter("fuelType");
        String consumptionRateStr = request.getParameter("consumptionRate");
        String maxPassengersStr = request.getParameter("maxPassengers");
        String route = request.getParameter("route");
        String status = request.getParameter("status");
        
        // Validate input
        if (vehicleId == null || vehicleId.isEmpty() ||
            type == null || type.isEmpty() || 
            number == null || number.isEmpty() || 
            fuelType == null || fuelType.isEmpty() ||
            consumptionRateStr == null || consumptionRateStr.isEmpty() ||
            maxPassengersStr == null || maxPassengersStr.isEmpty() ||
            route == null || route.isEmpty() ||
            status == null || status.isEmpty()) {
            
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/vehicles/edit.jsp").forward(request, response);
            return;
        }
        
        try {
            double consumptionRate = Double.parseDouble(consumptionRateStr);
            int maxPassengers = Integer.parseInt(maxPassengersStr);
            
            // Create vehicle object
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(vehicleId);
            vehicle.setType(type);
            vehicle.setNumber(number);
            vehicle.setFuelType(fuelType);
            vehicle.setConsumptionRate(consumptionRate);
            vehicle.setMaxPassengers(maxPassengers);
            vehicle.setRoute(route);
            vehicle.setStatus(status);
            
            // Update vehicle
            vehicleLogic.updateVehicle(vehicle);
            
            response.sendRedirect(request.getContextPath() + "/vehicles?action=view&id=" + vehicleId);
            
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Invalid number format");
            request.getRequestDispatcher("/WEB-INF/vehicles/edit.jsp").forward(request, response);
        }
    }
    
    /**
     * Deletes a vehicle.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param vehicleLogic the business logic for vehicles
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response,
            VehicleBusinessLogic vehicleLogic) 
            throws ServletException, IOException, SQLException {
        
        String vehicleId = request.getParameter("id");
        if (vehicleId == null || vehicleId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/vehicles");
            return;
        }
        
        vehicleLogic.deleteVehicle(vehicleId);
        
        response.sendRedirect(request.getContextPath() + "/vehicles");
    }
}