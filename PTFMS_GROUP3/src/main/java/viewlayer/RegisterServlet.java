package viewlayer;

import businesslayer.UserBusinessLogic;
import transferobjects.CredentialsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet for handling user registration.
 * @author Hongchen Guo
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    
    /**
     * Handles GET requests for the registration form.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/auth/register.jsp").forward(request, response);
    }
    
    /**
     * Handles POST requests for user registration.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String type = request.getParameter("type");
        
        // Validate input
        if (name == null || name.isEmpty() || 
            email == null || email.isEmpty() || 
            password == null || password.isEmpty() ||
            type == null || type.isEmpty()) {
            
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/WEB-INF/auth/register.jsp").forward(request, response);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/WEB-INF/auth/register.jsp").forward(request, response);
            return;
        }
        
        if (!type.equals("Manager") && !type.equals("Operator")) {
            request.setAttribute("error", "Invalid user type");
            request.getRequestDispatcher("/WEB-INF/auth/register.jsp").forward(request, response);
            return;
        }
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");
            
            UserBusinessLogic userLogic = new UserBusinessLogic(creds);
            userLogic.registerUser(name, email, password, type);
            
            // Registration successful
            request.setAttribute("success", "Registration successful. You can now login.");
            request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Registration failed: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/auth/register.jsp").forward(request, response);
        }
    }
}