package viewlayer;

import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            // Default action: redirect to VehicleServlet
            request.getRequestDispatcher("/VehicleServlet").forward(request, response);
            return;
        }

        // Dispatch based on action
        switch (action) {
            case "user":
                request.getRequestDispatcher("/LoginServlet").forward(request, response);
                break;
            case "vehicle":
                request.getRequestDispatcher("/VehicleServlet").forward(request, response);
                break;
            case "alert":
                request.getRequestDispatcher("/AlertServlet").forward(request, response);
                break;
            case "report":
                request.getRequestDispatcher("/ReportServlet").forward(request, response);
                break;
            case "logout":
                session.invalidate();
                response.sendRedirect("LoginServlet");
                break;
            default:
                request.getRequestDispatcher("/VehicleServlet").forward(request, response);
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Front Controller for PTFMS";
    }
}