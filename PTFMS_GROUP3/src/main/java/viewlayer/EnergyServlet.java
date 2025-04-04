package viewlayer;

import businesslayer.EnergyBusinessLogic;
import businesslayer.VehicleBusinessLogic;
import entity.EnergyUsage;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servlet for handling energy usage-related HTTP requests.
 * @author Hongchen Guo
 * @modifiedby Mei
 */
@WebServlet(name = "EnergyServlet", urlPatterns = {"/energy", "/energy/list", "/energy/add", "/energy/edit", "/energy/delete"})
public class EnergyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ensure user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();
        String action = request.getParameter("action");
        if (action == null) {
            if (path.equals("/energy/delete")) {
                action = "delete";
            } else if (path.equals("/energy/edit")) {
                action = "showEditForm";
            } else {
                action = "list";
            }
        }

        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");

            EnergyBusinessLogic energyLogic = new EnergyBusinessLogic(creds);
            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
            List<Vehicle> vehicles = null; // Declare only once

            switch (action) {
                case "showAddForm":
                    vehicles = vehicleLogic.getAllVehicles();
                    request.setAttribute("vehicles", vehicles);
                    request.getRequestDispatcher("/WEB-INF/energy/add.jsp").forward(request, response);
                    break;

                case "showEditForm":
                    String idStr = request.getParameter("id");
                    if (idStr != null && !idStr.isEmpty()) {
                        int id = Integer.parseInt(idStr);
                        EnergyUsage record = energyLogic.getEnergyUsageById(id);
                        if (record != null) {
                            request.setAttribute("record", record);

                            vehicles = vehicleLogic.getAllVehicles();
                            request.setAttribute("vehicles", vehicles);
                            request.getRequestDispatcher("/WEB-INF/energy/edit.jsp").forward(request, response);
                        } else {
                            request.setAttribute("error", "Record not found");
                            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("error", "Invalid ID for editing");
                        request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                    }
                    break;

                case "delete":
                    idStr = request.getParameter("id");
                    if (idStr != null && !idStr.isEmpty()) {
                        int id = Integer.parseInt(idStr);
                        energyLogic.deleteEnergyUsage(id);
                        response.sendRedirect(request.getContextPath() + "/energy");
                    } else {
                        request.setAttribute("error", "Invalid ID for deletion");
                        request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                    }
                    break;

                case "list":
                default:
                    String fuelEnergyType = request.getParameter("fuelEnergyType");
                    String startDate = request.getParameter("startDate");
                    String endDate = request.getParameter("endDate");
                    List<EnergyUsage> usages = energyLogic.getFilteredEnergyUsage(fuelEnergyType, startDate, endDate);
                    request.setAttribute("usages", usages);
                    request.getRequestDispatcher("/WEB-INF/energy/list.jsp").forward(request, response);
                    break;
            }

        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ensure user is logged in
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

            EnergyBusinessLogic energyLogic = new EnergyBusinessLogic(creds);

            if ("add".equals(action)) {
                addEnergyUsage(request, response, energyLogic);
                return;
            } else if ("update".equals(action)) {
                updateEnergyUsage(request, response, energyLogic);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/energy");

        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

    private void addEnergyUsage(HttpServletRequest request, HttpServletResponse response,
                                EnergyBusinessLogic energyLogic)
            throws ServletException, IOException, SQLException {

        String vehicleId = request.getParameter("vehicleId");
        String fuelEnergyType = request.getParameter("fuelEnergyType");
        String amountUsedStr = request.getParameter("amountUsed");
        String distanceTraveledStr = request.getParameter("distanceTraveled");

        if (vehicleId == null || fuelEnergyType == null || amountUsedStr == null || distanceTraveledStr == null ||
            vehicleId.isEmpty() || fuelEnergyType.isEmpty() || amountUsedStr.isEmpty() || distanceTraveledStr.isEmpty()) {

            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/energy/add.jsp").forward(request, response);
            return;
        }

        try {
            double amountUsed = Double.parseDouble(amountUsedStr);
            double distanceTraveled = Double.parseDouble(distanceTraveledStr);

            EnergyUsage usage = new EnergyUsage();
            usage.setVehicleId(vehicleId);
            usage.setFuelEnergyType(fuelEnergyType);
            usage.setAmountUsed(amountUsed);
            usage.setDistanceTraveled(distanceTraveled);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            usage.setTimestamp(formattedDateTime);

            energyLogic.addEnergyUsage(usage);
            response.sendRedirect(request.getContextPath() + "/energy");

        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Invalid number format");
            request.getRequestDispatcher("/WEB-INF/energy/add.jsp").forward(request, response);
        }
    }

    private void updateEnergyUsage(HttpServletRequest request, HttpServletResponse response,
                                   EnergyBusinessLogic energyLogic)
            throws ServletException, IOException, SQLException {

        String usageIdStr = request.getParameter("usageId");
        String vehicleId = request.getParameter("vehicleId");
        String fuelEnergyType = request.getParameter("fuelEnergyType");
        String amountUsedStr = request.getParameter("amountUsed");
        String distanceTraveledStr = request.getParameter("distanceTraveled");
        String timestamp = request.getParameter("timestamp");

        if (usageIdStr == null || vehicleId == null || fuelEnergyType == null || amountUsedStr == null || 
            distanceTraveledStr == null || timestamp == null || vehicleId.isEmpty() || 
            fuelEnergyType.isEmpty() || amountUsedStr.isEmpty() || distanceTraveledStr.isEmpty() || 
            timestamp.isEmpty()) {

            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/energy/edit.jsp").forward(request, response);
            return;
        }

        try {
            int usageId = Integer.parseInt(usageIdStr);
            double amountUsed = Double.parseDouble(amountUsedStr);
            double distanceTraveled = Double.parseDouble(distanceTraveledStr);

            EnergyUsage usage = new EnergyUsage();
            usage.setUsageId(usageId);
            usage.setVehicleId(vehicleId);
            usage.setFuelEnergyType(fuelEnergyType);
            usage.setAmountUsed(amountUsed);
            usage.setDistanceTraveled(distanceTraveled);
            usage.setTimestamp(timestamp);

            energyLogic.updateEnergyUsage(usage);
            response.sendRedirect(request.getContextPath() + "/energy");

        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Invalid number format");
            request.getRequestDispatcher("/WEB-INF/energy/edit.jsp").forward(request, response);
        }
    }
}
