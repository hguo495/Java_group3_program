package viewlayer;

import businesslayer.OperatorPerformanceBusinessLogic;
import businesslayer.UserBusinessLogic;
import entity.User;
import transferobjects.CredentialsDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Servlet for handling operator performance requests.
 * @author Hongchen Guo
 */
@WebServlet(name = "OperatorPerformanceServlet", urlPatterns = {"/operator-performance"})
public class OperatorPerformanceServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String operatorIdStr = request.getParameter("operatorId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        try {
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");
            
            // 获取操作员列表
            UserBusinessLogic userLogic = new UserBusinessLogic(creds);
            List<User> operators = userLogic.getAllUsers();
            request.setAttribute("operators", operators);
            
            // 获取性能业务逻辑
            OperatorPerformanceBusinessLogic performanceLogic = new OperatorPerformanceBusinessLogic(creds);
            
            // 如果选择了具体操作员
            Map<String, Object> detailedPerformance = null;
            if (operatorIdStr != null && !operatorIdStr.isEmpty()) {
                int operatorId = Integer.parseInt(operatorIdStr);
                detailedPerformance = 
                    performanceLogic.getOperatorDetailedPerformance(operatorId, startDate, endDate);
            }
            
            request.setAttribute("detailedPerformance", detailedPerformance);
            
            request.getRequestDispatcher("/WEB-INF/performance/detailed.jsp").forward(request, response);
            
        } catch (SQLException ex) {
            request.setAttribute("error", "Database error: " + ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
}