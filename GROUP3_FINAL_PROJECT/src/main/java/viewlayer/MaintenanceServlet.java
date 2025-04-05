package viewlayer;

import businesslayer.ComponentBusinessLogic;
import businesslayer.VehicleBusinessLogic;
import dataaccesslayer.MaintenanceTaskDAO;
import dataaccesslayer.MaintenanceTaskDAOImpl;
import entity.Component;
import entity.MaintenanceTask;
import entity.Vehicle;
import transferobjects.CredentialsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * MaintenanceServlet handles all maintenance-related functionality in the PTFMS application.
 * It supports listing, filtering, adding, editing, and deleting maintenance tasks.
 *
 * <p>GET requests are used for displaying data and forms, while POST requests handle form submissions.</p>
 *
 * @author Honchen Guo
 * @modifiedBy Mei
 */
@WebServlet(name = "MaintenanceServlet", urlPatterns = {"/maintenance"})
public class MaintenanceServlet extends HttpServlet {

    /**
     * Handles GET requests for displaying maintenance tasks, forms, and filtering options.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "tasks"; // Default action
        }

        try {
            // Setup database credentials
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");

            MaintenanceTaskDAO taskDAO = new MaintenanceTaskDAOImpl(creds);
            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);

            switch (action) {
                case "form":
                    // Show the form to schedule a new maintenance task
                    request.setAttribute("vehicles", vehicleLogic.getAllVehicles());
                    request.setAttribute("components", componentLogic.getAllComponents());
                    request.getRequestDispatcher("/WEB-INF/maintenance/schedule.jsp").forward(request, response);
                    break;

                case "edit":
                    // Show the edit form for a specific task
                    String taskIdStr = request.getParameter("taskId");
                    if (taskIdStr != null) {
                        int taskId = Integer.parseInt(taskIdStr);
                        MaintenanceTask task = taskDAO.getAllMaintenanceTasks().stream()
                                .filter(t -> t.getTaskId() == taskId)
                                .findFirst()
                                .orElse(null);
                        if (task != null) {
                            request.setAttribute("task", task);
                            request.setAttribute("vehicles", vehicleLogic.getAllVehicles());
                            request.setAttribute("components", componentLogic.getAllComponents());
                            request.getRequestDispatcher("/WEB-INF/maintenance/edit.jsp").forward(request, response);
                        } else {
                            request.setAttribute("error", "Task not found.");
                            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                        }
                    }
                    break;

                case "delete":
                    // Delete the specified task
                    String taskIdToDelete = request.getParameter("taskId");
                    if (taskIdToDelete != null) {
                        int taskId = Integer.parseInt(taskIdToDelete);
                        taskDAO.deleteMaintenanceTask(taskId);
                    }
                    // Refresh task list
                    List<MaintenanceTask> tasksAfterDelete = taskDAO.getAllMaintenanceTasks();
                    request.setAttribute("tasks", tasksAfterDelete);
                    request.getRequestDispatcher("/WEB-INF/maintenance/tasks.jsp").forward(request, response);
                    break;

                default:
                    // Default action: show list of tasks with optional filters
                    String vehicleId = request.getParameter("vehicleId");
                    String component = request.getParameter("component");
                    String startDate = request.getParameter("startDate");
                    String endDate = request.getParameter("endDate");

                    List<MaintenanceTask> tasks = taskDAO.getAllMaintenanceTasks();

                    // Apply filters if provided
                    if (vehicleId != null && !vehicleId.isEmpty()) {
                        tasks = tasks.stream()
                                .filter(t -> t.getVehicleId().equalsIgnoreCase(vehicleId))
                                .toList();
                    }
                    if (component != null && !component.isEmpty()) {
                        tasks = tasks.stream()
                                .filter(t -> String.valueOf(t.getComponentId()).equals(component))
                                .toList();
                    }
                    if (startDate != null && !startDate.isEmpty()) {
                        tasks = tasks.stream()
                                .filter(t -> t.getScheduledDate().compareTo(startDate) >= 0)
                                .toList();
                    }
                    if (endDate != null && !endDate.isEmpty()) {
                        tasks = tasks.stream()
                                .filter(t -> t.getScheduledDate().compareTo(endDate) <= 0)
                                .toList();
                    }

                    request.setAttribute("tasks", tasks);
                    request.getRequestDispatcher("/WEB-INF/maintenance/tasks.jsp").forward(request, response);
                    break;
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles POST requests for scheduling or updating maintenance tasks.
     *
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            // Setup credentials
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");

            MaintenanceTaskDAO taskDAO = new MaintenanceTaskDAOImpl(creds);

            if ("schedule".equals(action)) {
                // Schedule a new maintenance task
                String vehicleId = request.getParameter("vehicleId");
                String componentIdStr = request.getParameter("componentId");
                String description = request.getParameter("issue");
                String scheduledDate = request.getParameter("scheduledDate");

                // Validate form inputs
                if (vehicleId == null || vehicleId.isEmpty()
                        || componentIdStr == null || componentIdStr.isEmpty()
                        || description == null || description.isEmpty()
                        || scheduledDate == null || scheduledDate.isEmpty()) {
                    request.setAttribute("error", "All fields are required.");
                    doGet(request, response);
                    return;
                }

                int componentId = Integer.parseInt(componentIdStr);

                MaintenanceTask task = new MaintenanceTask();
                task.setVehicleId(vehicleId);
                task.setComponentId(componentId);
                task.setDescription(description);
                task.setScheduledDate(scheduledDate);
                task.setStatus("Scheduled");

                taskDAO.addMaintenanceTask(task);

                // Refresh task list
                List<MaintenanceTask> tasks = taskDAO.getAllMaintenanceTasks();
                request.setAttribute("tasks", tasks);
                request.getRequestDispatcher("/WEB-INF/maintenance/tasks.jsp").forward(request, response);

            } else if ("update".equals(action)) {
                // Update an existing task
                String taskIdStr = request.getParameter("taskId");
                String vehicleId = request.getParameter("vehicleId");
                String componentIdStr = request.getParameter("componentId");
                String description = request.getParameter("issue");
                String scheduledDate = request.getParameter("scheduledDate");

                // Validate form inputs
                if (taskIdStr == null || vehicleId == null || vehicleId.isEmpty()
                        || componentIdStr == null || componentIdStr.isEmpty()
                        || description == null || description.isEmpty()
                        || scheduledDate == null || scheduledDate.isEmpty()) {
                    request.setAttribute("error", "All fields are required.");
                    doGet(request, response);
                    return;
                }

                int taskId = Integer.parseInt(taskIdStr);
                int componentId = Integer.parseInt(componentIdStr);

                MaintenanceTask task = new MaintenanceTask();
                task.setTaskId(taskId);
                task.setVehicleId(vehicleId);
                task.setComponentId(componentId);
                task.setDescription(description);
                task.setScheduledDate(scheduledDate);
                task.setStatus("Scheduled");

                taskDAO.updateMaintenanceTask(task);

                // Refresh task list
                List<MaintenanceTask> tasks = taskDAO.getAllMaintenanceTasks();
                request.setAttribute("tasks", tasks);
                request.getRequestDispatcher("/WEB-INF/maintenance/tasks.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}
