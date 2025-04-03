//updated on March 31
package viewlayer;

import businesslayer.StationTrackingBusinessLogic;
import entity.Station;
import transferobjects.CredentialsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet for handling station-related HTTP requests.
 * @author Hongchen Guo
 */
@WebServlet(name = "StationServlet", urlPatterns = {"/stations"})
public class StationServlet extends HttpServlet {
    
    /**
     * Handles GET requests for station data.
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
            action = "list";  // Default action
        }
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");
            
            StationTrackingBusinessLogic stationLogic = new StationTrackingBusinessLogic(creds);
            
            switch (action) {
                case "list":
                    listStations(request, response, stationLogic);
                    break;
                case "view":
                    viewStation(request, response, stationLogic);
                    break;
                case "byRoute":
                    listStationsByRoute(request, response, stationLogic);
                    break;
                case "addForm": // handles GET request to show form- on March 31
                    request.getRequestDispatcher("/WEB-INF/stations/add.jsp").forward(request, response);
                     break;
                default:
                    listStations(request, response, stationLogic);
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles POST requests for adding or updating stations.
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
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");
            
            StationTrackingBusinessLogic stationLogic = new StationTrackingBusinessLogic(creds);
            
            switch (action) {
                case "add":
                    addStation(request, response, stationLogic);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/stations");
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Lists all stations.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param stationLogic the business logic for stations
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void listStations(HttpServletRequest request, HttpServletResponse response,
            StationTrackingBusinessLogic stationLogic) 
            throws ServletException, IOException, SQLException {
        
        List<Station> stations = stationLogic.getAllStations();
        request.setAttribute("stations", stations);
        request.getRequestDispatcher("/WEB-INF/stations/list.jsp").forward(request, response);
    }
    
    /**
     * Views a specific station.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param stationLogic the business logic for stations
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void viewStation(HttpServletRequest request, HttpServletResponse response,
            StationTrackingBusinessLogic stationLogic) 
            throws ServletException, IOException, SQLException {
        
        String stationId = request.getParameter("id");
        if (stationId == null || stationId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/stations");
            return;
        }
        
        Station station = stationLogic.stationDAO.getStationById(stationId);
        if (station == null) {
            request.setAttribute("error", "Station not found");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
        
        request.setAttribute("station", station);
        
        // Also get recent events at this station
        List<entity.StationEvent> events = stationLogic.getStationEvents(stationId);
        request.setAttribute("events", events);
        
        request.getRequestDispatcher("/WEB-INF/stations/view.jsp").forward(request, response);
    }
    
    /**
     * Lists stations by route.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param stationLogic the business logic for stations
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void listStationsByRoute(HttpServletRequest request, HttpServletResponse response,
            StationTrackingBusinessLogic stationLogic) 
            throws ServletException, IOException, SQLException {
        
        String routeId = request.getParameter("routeId");
        if (routeId == null || routeId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/stations");
            return;
        }
        
        List<Station> stations = stationLogic.getStationsByRoute(routeId);
        request.setAttribute("stations", stations);
        request.setAttribute("routeId", routeId);
        request.getRequestDispatcher("/WEB-INF/stations/byRoute.jsp").forward(request, response);
    }
    
    /**
     * Adds a new station.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param stationLogic the business logic for stations
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void addStation(HttpServletRequest request, HttpServletResponse response,
            StationTrackingBusinessLogic stationLogic) 
            throws ServletException, IOException, SQLException {
        
        // Extract form parameters
        String stationId = request.getParameter("stationId");
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String routeId = request.getParameter("routeId");
        
        // Validate input
        if (stationId == null || stationId.isEmpty() || 
            name == null || name.isEmpty() ||
            routeId == null || routeId.isEmpty()) {
            
            request.setAttribute("error", "All required fields must be filled out");
            request.getRequestDispatcher("/WEB-INF/stations/add.jsp").forward(request, response);
            return;
        }
        
        // Create and add station
        Station station = new Station();
        station.setStationId(stationId);
        station.setName(name);
        station.setLocation(location);
        station.setRouteId(routeId);
        
        stationLogic.addStation(station);
        
        response.sendRedirect(request.getContextPath() + "/stations");
    }
}