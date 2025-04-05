package viewlayer;

import businesslayer.ReportBusinessLogic;
import entity.Report;
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
 * Servlet for handling report-related HTTP requests.
 * @author Hongchen Guo
 */
@WebServlet(name = "ReportServlet", urlPatterns = {"/reports"})
public class ReportServlet extends HttpServlet {
    
    /**
     * Handles GET requests for report data.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Check if user is logged in and is a Manager
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String userType = (String) session.getAttribute("userType");
        if (!"Manager".equals(userType)) {
            request.setAttribute("error", "Only managers can access reports");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
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
            
            ReportBusinessLogic reportLogic = new ReportBusinessLogic(creds);
            
            switch (action) {
                case "list":
                    listReports(request, response, reportLogic);
                    break;
                case "view":
                    viewReport(request, response, reportLogic);
                    break;
                case "showGenerateForm":
                    showGenerateForm(request, response);
                    break;
                default:
                    listReports(request, response, reportLogic);
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Handles POST requests for generating reports.
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
            request.setAttribute("error", "Only managers can generate reports");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");
            
            ReportBusinessLogic reportLogic = new ReportBusinessLogic(creds);
            
            if ("generate".equals(action)) {
                // Extract form parameters
                String type = request.getParameter("type");
                String data = request.getParameter("data");
                
                // Validate input
                if (type == null || type.isEmpty() || data == null || data.isEmpty()) {
                    request.setAttribute("error", "All fields are required");
                    request.getRequestDispatcher("/WEB-INF/reports/generate.jsp").forward(request, response);
                    return;
                }
                
                // Generate report
                reportLogic.generateReport(type, data);
            }
            
            response.sendRedirect(request.getContextPath() + "/reports");
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Lists all reports.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param reportLogic the business logic for reports
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void listReports(HttpServletRequest request, HttpServletResponse response,
            ReportBusinessLogic reportLogic) 
            throws ServletException, IOException, SQLException {
        
        List<Report> reports = reportLogic.getAllReports();
        request.setAttribute("reports", reports);
        request.getRequestDispatcher("/WEB-INF/reports/list.jsp").forward(request, response);
    }
    
    /**
     * Views a specific report.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param reportLogic the business logic for reports
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database error occurs
     */
    private void viewReport(HttpServletRequest request, HttpServletResponse response,
            ReportBusinessLogic reportLogic) 
            throws ServletException, IOException, SQLException {
        
        String reportIdStr = request.getParameter("id");
        if (reportIdStr == null || reportIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/reports");
            return;
        }
        
        try {
            int reportId = Integer.parseInt(reportIdStr);
            
            // In a real application, you would have a getReportById method
            // For now, we'll just get all reports and find the one with the matching ID
            List<Report> reports = reportLogic.getAllReports();
            Report report = null;
            
            for (Report r : reports) {
                if (r.getReportId() == reportId) {
                    report = r;
                    break;
                }
            }
            
            if (report == null) {
                request.setAttribute("error", "Report not found");
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                return;
            }
            
            request.setAttribute("report", report);
            request.getRequestDispatcher("/WEB-INF/reports/view.jsp").forward(request, response);
            
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Invalid report ID");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Shows the form for generating a new report.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void showGenerateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/reports/generate.jsp").forward(request, response);
    }
}