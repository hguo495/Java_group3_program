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

@WebServlet(name = "MaintenanceServlet", urlPatterns = {"/maintenance"})
public class MaintenanceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "tasks";
        }

        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");

            MaintenanceTaskDAO taskDAO = new MaintenanceTaskDAOImpl(creds);
            VehicleBusinessLogic vehicleLogic = new VehicleBusinessLogic(creds);
            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);

            if ("form".equals(action)) {
                // Show the new task form
                request.setAttribute("vehicles", vehicleLogic.getAllVehicles());
                request.setAttribute("components", componentLogic.getAllComponents());
                request.getRequestDispatcher("/WEB-INF/maintenance/schedule.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                // Show the edit task form
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
            } else if ("delete".equals(action)) {
                // Delete a task
                String taskIdStr = request.getParameter("taskId");
                if (taskIdStr != null) {
                    int taskId = Integer.parseInt(taskIdStr);
                    taskDAO.deleteMaintenanceTask(taskId);
                }
                // After delete, reload list
                List<MaintenanceTask> tasks = taskDAO.getAllMaintenanceTasks();
                request.setAttribute("tasks", tasks);
                request.getRequestDispatcher("/WEB-INF/maintenance/tasks.jsp").forward(request, response);
            } else {
                // Default: show all tasks with filters
                String vehicleId = request.getParameter("vehicleId");
                String component = request.getParameter("component");
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");

                List<MaintenanceTask> tasks = taskDAO.getAllMaintenanceTasks();
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
            }

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");

            MaintenanceTaskDAO taskDAO = new MaintenanceTaskDAOImpl(creds);

            if ("schedule".equals(action)) {
                String vehicleId = request.getParameter("vehicleId");
                String componentIdStr = request.getParameter("componentId");
                String description = request.getParameter("issue");
                String scheduledDate = request.getParameter("scheduledDate");

                if (vehicleId == null || vehicleId.isEmpty() || componentIdStr == null || componentIdStr.isEmpty()
                        || description == null || description.isEmpty() || scheduledDate == null || scheduledDate.isEmpty()) {
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

                // After insert, reload list
                List<MaintenanceTask> tasks = taskDAO.getAllMaintenanceTasks();
                request.setAttribute("tasks", tasks);
                request.getRequestDispatcher("/WEB-INF/maintenance/tasks.jsp").forward(request, response);
            } else if ("update".equals(action)) {
                // Update a task
                String taskIdStr = request.getParameter("taskId");
                String vehicleId = request.getParameter("vehicleId");
                String componentIdStr = request.getParameter("componentId");
                String description = request.getParameter("issue");
                String scheduledDate = request.getParameter("scheduledDate");

                if (taskIdStr == null || vehicleId == null || vehicleId.isEmpty() || componentIdStr == null || componentIdStr.isEmpty()
                        || description == null || description.isEmpty() || scheduledDate == null || scheduledDate.isEmpty()) {
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

                // After update, reload list
                List<MaintenanceTask> tasks = taskDAO.getAllMaintenanceTasks();
                request.setAttribute("tasks", tasks);
                request.getRequestDispatcher("/WEB-INF/maintenance/tasks.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}