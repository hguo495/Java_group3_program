package viewlayer;

import businesslayer.UserBusinessLogic;
import entity.User;
import transferobjects.CredentialsDTO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet for handling user authentication.
 * @author Hongchen Guo
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login", "/logout"})
public class LoginServlet extends HttpServlet {
    
    /**
     * Handles GET requests for login/logout operations.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        if ("/logout".equals(path)) {
            // Handle logout
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // Show login form
            request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles POST requests for login operations.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
            return;
        }
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("CST8288");
            creds.setPassword("CST8288");
            
            UserBusinessLogic userLogic = new UserBusinessLogic(creds);
            User user = userLogic.authenticateUser(email, password);
            
            if (user != null) {
                // Authentication successful
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userType", user.getType());
                session.setAttribute("userName", user.getName());
                
                // Redirect to dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                // Authentication failed
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
            }
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/auth/login.jsp").forward(request, response);
        }
    }
}