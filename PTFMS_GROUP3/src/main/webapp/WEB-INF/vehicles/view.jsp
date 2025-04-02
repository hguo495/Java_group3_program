<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Vehicle - Public Transit Fleet Management System</title>
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
        .details {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        .detail-item {
            margin-bottom: 15px;
        }
        .detail-label {
            font-weight: bold;
            color: #7f8c8d;
        }
        .detail-value {
            margin-top: 5px;
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
        }
        .btn:hover {
            background-color: #2980b9;
        }
        .btn-danger {
            background-color: #e74c3c;
        }
        .btn-danger:hover {
            background-color: #c0392b;
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
            <h2>Vehicle Details</h2>
            
            <div class="actions">
                <a href="vehicles" class="btn">Back to List</a>
                <c:if test="${userType eq 'Manager'}">
                    <a href="vehicles?action=showEditForm&id=${vehicle.vehicleId}" class="btn">Edit</a>
                    <a href="javascript:confirmDelete('${vehicle.vehicleId}')" class="btn btn-danger">Delete</a>
                </c:if>
            </div>
            
            <div class="card">
                <div class="details">
                    <div class="detail-item">
                        <div class="detail-label">ID</div>
                        <div class="detail-value">${vehicle.vehicleId}</div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-label">Type</div>
                        <div class="detail-value">${vehicle.type}</div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-label">Number</div>
                        <div class="detail-value">${vehicle.number}</div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-label">Fuel Type</div>
                        <div class="detail-value">${vehicle.fuelType}</div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-label">Consumption Rate</div>
                        <div class="detail-value">${vehicle.consumptionRate}</div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-label">Maximum Passengers</div>
                        <div class="detail-value">${vehicle.maxPassengers}</div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-label">Route</div>
                        <div class="detail-value">${vehicle.route}</div>
                    </div>
                    <div class="detail-item">
                        <div class="detail-label">Status</div>
                        <div class="detail-value">${vehicle.status}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        function confirmDelete(id) {
            if (confirm("Are you sure you want to delete this vehicle?")) {
                window.location.href = "vehicles?action=delete&id=" + id;
            }
        }
    </script>
</body>
</html>