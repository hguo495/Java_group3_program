package viewlayer;

import businesslayer.AlertBusinessLogic;
import businesslayer.ComponentBusinessLogic;
import entity.Component;
import transferobjects.CredentialsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ComponentServlet", urlPatterns = {"/component"})
public class ComponentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("root");
            creds.setPassword("1234");

            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            List<Component> components = componentLogic.getAllComponents();

            request.setAttribute("components", components);
            request.getRequestDispatcher("/WEB-INF/components/add.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String vehicleId = request.getParameter("vehicleId");
        String type = request.getParameter("type");
        String hoursUsedStr = request.getParameter("hoursUsed");
        String wearPercentageStr = request.getParameter("wearPercentage");
        String diagnosticStatus = request.getParameter("diagnosticStatus");

        if (vehicleId == null || type == null || hoursUsedStr == null || wearPercentageStr == null || diagnosticStatus == null ||
                vehicleId.isEmpty() || type.isEmpty() || hoursUsedStr.isEmpty() || wearPercentageStr.isEmpty() || diagnosticStatus.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/WEB-INF/components/add.jsp").forward(request, response);
            return;
        }

        try {
            double hoursUsed = Double.parseDouble(hoursUsedStr);
            double wearPercentage = Double.parseDouble(wearPercentageStr);

            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("root");
            creds.setPassword("1234");

            Component component = new Component();
            component.setVehicleId(vehicleId);
            component.setType(type);
            component.setHoursUsed(hoursUsed);
            component.setWearPercentage(wearPercentage);
            component.setDiagnosticStatus(diagnosticStatus);

            ComponentBusinessLogic componentLogic = new ComponentBusinessLogic(creds);
            componentLogic.addComponent(component); // 自动触发 Alert 检查

            response.sendRedirect(request.getContextPath() + "/component");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid number format.");
            request.getRequestDispatcher("/WEB-INF/components/add.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}
