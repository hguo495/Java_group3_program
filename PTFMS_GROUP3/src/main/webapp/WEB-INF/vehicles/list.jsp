<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vehicle List - Public Transit Fleet Management System</title>
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
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table th, table td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        table th {
            background-color: #f9f9f9;
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
            <h2>Vehicle Management</h2>
            
            <div class="actions">
                <c:if test="${userType eq 'Manager'}">
                    <a href="vehicles?action=showAddForm" class="btn">Add New Vehicle</a>
                </c:if>
            </div>
            
            <div class="card">
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Type</th>
                        <th>Number</th>
                        <th>Fuel Type</th>
                        <th>Route</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    <c:forEach var="vehicle" items="${vehicles}">
                        <tr>
                            <td>${vehicle.vehicleId}</td>
                            <td>${vehicle.type}</td>
                            <td>${vehicle.number}</td>
                            <td>${vehicle.fuelType}</td>
                            <td>${vehicle.route}</td>
                            <td>${vehicle.status}</td>
                            <td>
                                <a href="vehicles?action=view&id=${vehicle.vehicleId}" class="btn">View</a>
                                <c:if test="${userType eq 'Manager'}">
                                    <a href="vehicles?action=showEditForm&id=${vehicle.vehicleId}" class="btn">Edit</a>
                                    <a href="javascript:confirmDelete('${vehicle.vehicleId}')" class="btn btn-danger">Delete</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
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