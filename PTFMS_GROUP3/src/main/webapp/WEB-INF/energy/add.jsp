<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Energy Usage Record</title>
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
        input, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
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
        }
        .btn-save {
            background-color: #2ecc71;
        }
        .btn-save:hover {
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
    </style>
</head>
<body>
    <div class="container">
        <h1>Add Energy Usage Record</h1>
        
        <c:if test="${not empty error}">
            <div class="error-message" style="display: block;">
                ${error}
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/energy" method="post" id="addEnergyForm" onsubmit="return validateForm()">
            <input type="hidden" name="action" value="add">
            
            <div class="form-group">
                <label for="vehicleId">Vehicle:</label>
                <select id="vehicleId" name="vehicleId" required>
                    <option value="">Select a vehicle</option>
                    <c:forEach items="${vehicles}" var="vehicle">
                        <option value="${vehicle.vehicleId}">${vehicle.make} ${vehicle.model} (${vehicle.licensePlate})</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="fuelEnergyType">Fuel/Energy Type:</label>
                <select id="fuelEnergyType" name="fuelEnergyType" required>
                    <option value="">Select fuel/energy type</option>
                    <option value="Gasoline">Gasoline</option>
                    <option value="Diesel">Diesel</option>
                    <option value="Electricity">Electricity</option>
                    <option value="Natural Gas">Natural Gas</option>
                    <option value="Hydrogen">Hydrogen</option>
                    <option value="Biodiesel">Biodiesel</option>
                    <option value="Ethanol">Ethanol</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="amountUsed">Amount Used:</label>
                <input type="number" id="amountUsed" name="amountUsed" step="0.01" min="0" required>
                <small id="amountUsedHelp">For gasoline/diesel: liters, for electricity: kWh, etc.</small>
            </div>
            
            <div class="form-group">
                <label for="distanceTraveled">Distance Traveled (km):</label>
                <input type="number" id="distanceTraveled" name="distanceTraveled" step="0.1" min="0" required>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-save">Save Energy Usage</button>
                <a href="${pageContext.request.contextPath}/energy" class="btn btn-cancel">Cancel</a>
            </div>
        </form>
    </div>
    
    <script>
        function validateForm() {
            // Get form values
            const vehicleId = document.getElementById("vehicleId").value;
            const fuelEnergyType = document.getElementById("fuelEnergyType").value;
            const amountUsed = document.getElementById("amountUsed").value;
            const distanceTraveled = document.getElementById("distanceTraveled").value;
            
            // Check if all fields are filled
            if (!vehicleId || !fuelEnergyType || !amountUsed || !distanceTraveled) {
                showError("All fields are required");
                return false;
            }
            
            // Validate numeric fields
            if (isNaN(amountUsed) || parseFloat(amountUsed) <= 0) {
                showError("Amount used must be a positive number");
                return false;
            }
            
            if (isNaN(distanceTraveled) || parseFloat(distanceTraveled) <= 0) {
                showError("Distance traveled must be a positive number");
                return false;
            }
            
            return true;
        }
        
        function showError(message) {
            const errorElement = document.querySelector(".error-message");
            errorElement.textContent = message;
            errorElement.style.display = "block";
        }
    </script>
</body>
</html>