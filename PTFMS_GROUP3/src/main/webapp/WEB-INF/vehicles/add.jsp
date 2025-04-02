<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Vehicle - Public Transit Fleet Management System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .header {
            background-color: #2c3e50;
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .user-info {
            display: flex;
            align-items: center;
        }
        .user-info span {
            margin-right: 15px;
        }
        .container {
            display: flex;
            min-height: calc(100vh - 60px);
        }
        .sidebar {
            width: 250px;
            background-color: #34495e;
            padding: 20px 0;
            color: white;
        }
        .sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .sidebar li {
            padding: 10px 20px;
        }
        .sidebar li a {
            color: white;
            text-decoration: none;
            display: block;
        }
        .sidebar li:hover {
            background-color: #2c3e50;
        }
        .main-content {
            flex: 1;
            padding: 20px;
        }
        .card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-bottom: 20px;
        }
        .actions {
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #7f8c8d;
        }
        input[type="text"],
        input[type="number"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn {
            display: inline-block;
            background-color: #3498db;
            color: white;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
            margin-right: 5px;
            border: none;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #2980b9;
        }
        .error {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Public Transit Fleet Management System</h1>
        <div class="user-info">
            <span>Welcome, ${userName}</span>
            <a href="logout" class="btn">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <div class="sidebar">
            <ul>
                <li><a href="dashboard">Dashboard</a></li>
                <li><a href="vehicles">Vehicles</a></li>
                <li><a href="tracking">GPS Tracking</a></li>
                <li><a href="energy">Energy Usage</a></li>
                <li><a href="maintenance">Maintenance</a></li>
                <li><a href="stations">Stations</a></li>
                <li><a href="reports">Reports</a></li>
                <li><a href="alerts">Alerts</a></li>
            </ul>
        </div>
        
        <div class="main-content">
            <h2>Add New Vehicle</h2>
            
            <div class="actions">
                <a href="vehicles" class="btn">Back to List</a>
            </div>
            
            <c:if test="${error != null}">
                <div class="error">${error}</div>
            </c:if>
            
            <div class="card">
                <form action="vehicles" method="post">
                  <input type="hidden" name="action" value="add">
                    
                    <div class="form-group">
                        <label for="type">Vehicle Type</label>
                        <select id="type" name="type" required>
                            <option value="">Select Type</option>
                            <option value="Diesel Bus">Diesel Bus</option>
                            <option value="Electric Light Rail">Electric Light Rail</option>
                            <option value="Diesel-Electric Train">Diesel-Electric Train</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="number">Vehicle Number</label>
                        <input type="text" id="number" name="number" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="fuelType">Fuel/Energy Type</label>
                        <select id="fuelType" name="fuelType" required>
                            <option value="">Select Fuel Type</option>
                            <option value="Diesel">Diesel</option>
                            <option value="CNG">CNG</option>
                            <option value="Electric">Electric</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="consumptionRate">Consumption Rate</label>
                        <input type="number" id="consumptionRate" name="consumptionRate" step="0.01" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="maxPassengers">Maximum Passengers</label>
                        <input type="number" id="maxPassengers" name="maxPassengers" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="route">Route</label>
                        <input type="text" id="route" name="route" required>
                    </div>
                    
                    <button type="submit" class="btn">Add Vehicle</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>