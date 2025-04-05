<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Generate Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input, select, textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }
        textarea {
            height: 200px;
            font-family: monospace;
        }
        .error-message {
            color: #e74c3c;
            margin-bottom: 15px;
            padding: 10px;
            background-color: #fadbd8;
            border-radius: 4px;
            display: none;
        }
        .btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            text-decoration: none;
            display: inline-block;
        }
        .btn-generate {
            background-color: #2ecc71;
        }
        .btn-generate:hover {
            background-color: #27ae60;
        }
        .btn-cancel {
            background-color: #95a5a6;
            margin-left: 10px;
        }
        .btn-cancel:hover {
            background-color: #7f8c8d;
        }
        .form-actions {
            margin-top: 20px;
        }
        .report-type-info {
            margin-top: 5px;
            font-size: 12px;
            color: #7f8c8d;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Generate Report</h1>
        
        <c:if test="${not empty error}">
            <div class="error-message" style="display: block;">
                ${error}
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/reports" method="post" id="generateReportForm" onsubmit="return validateForm()">
            <input type="hidden" name="action" value="generate">
            
            <div class="form-group">
                <label for="type">Report Type:</label>
                <select id="type" name="type" required onchange="updateInfoText()">
                    <option value="">Select report type</option>
                    <option value="Fuel Usage">Fuel Usage</option>
                    <option value="Maintenance Cost">Maintenance Cost</option>
                    <option value="Performance">Performance</option>
                </select>
                <div id="typeInfo" class="report-type-info"></div>
            </div>
            
            <div class="form-group">
                <label for="data">Report Data:</label>
                <textarea id="data" name="data" required placeholder="Enter report data here..."></textarea>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-generate">Generate Report</button>
                <a href="${pageContext.request.contextPath}/reports" class="btn btn-cancel">Cancel</a>
            </div>
        </form>
    </div>
    
    <script>
        function validateForm() {
            // Get form values
            const type = document.getElementById("type").value;
            const data = document.getElementById("data").value;
            
            // Check if all fields are filled
            if (!type || !data) {
                showError("All fields are required");
                return false;
            }
            
            return true;
        }
        
        function showError(message) {
            // Create error element if it doesn't exist
            let errorElement = document.querySelector(".error-message");
            if (!errorElement) {
                errorElement = document.createElement("div");
                errorElement.className = "error-message";
                const form = document.getElementById("generateReportForm");
                form.parentNode.insertBefore(errorElement, form);
            }
            
            errorElement.textContent = message;
            errorElement.style.display = "block";
        }
        
        function updateInfoText() {
            const type = document.getElementById("type").value;
            const infoElement = document.getElementById("typeInfo");
            
            if (type === "Fuel Usage") {
                infoElement.textContent = "Includes fuel consumption statistics for vehicles, cost analysis, and efficiency metrics.";
            } else if (type === "Maintenance Cost") {
                infoElement.textContent = "Details maintenance expenses, part replacements, and service costs for the fleet.";
            } else if (type === "Performance") {
                infoElement.textContent = "Analyzes vehicle performance metrics, uptime/downtime statistics, and operational efficiency.";
            } else {
                infoElement.textContent = "";
            }
        }
    </script>
</body>
</html>