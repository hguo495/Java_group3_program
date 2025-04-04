package viewlayer;

import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * FrontController servlet for handling user requests and dispatching to
 * appropriate servlets based on the "action" parameter.
 *
 * <p>This servlet acts as a centralized request handler in the PTFMS
 * (Public Transit Fleet Management System) application.</p>
 *
 * @author Honchen Guo
 * 
 */
public class FrontController extends HttpServlet {

    /**
     * Handles GET requests by forwarding them to the processRequest method.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles POST requests by forwarding them to the processRequest method.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Processes both GET and POST requests. Determines which servlet to forward
     * the request to based on the "action" parameter. Also checks for user login status.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get current session and user object
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // If user is not logged in, redirect to LoginServlet
        if (user == null) {
            response.sendRedirect("LoginServlet");
            return;
        }

        // Get the action parameter to determine routing
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            // No action provided, forward to default page (VehicleServlet)
            request.getRequestDispatcher("/VehicleServlet").forward(request, response);
            return;
        }

        // Route request based on the action parameter
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
                // Invalidate session and redirect to login page
                session.invalidate();
                response.sendRedirect("LoginServlet");
                break;
            default:
                // Default to VehicleServlet if action is unrecognized
                request.getRequestDispatcher("/VehicleServlet").forward(request, response);
                break;
        }
    }

    /**
     * Returns a short description of this servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Front Controller for PTFMS";
    }
}
