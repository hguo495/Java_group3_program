<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Operator Dashboard - Public Transit Fleet Management System</title>
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
        .card-title {
            margin-top: 0;
            color: #2c3e50;
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
        }
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }
        .alert {
            background-color: #e74c3c;
            color: white;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 10px;
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
        }
        .btn:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Public Transit Fleet Management System</h1>
        <div class="user-info">
            <span>Welcome, ${userName} (Operator)</span>
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
            </ul>
        </div>
        
        <div class="main-content">
            <h2>Operator Dashboard</h2>
                          
                <div class="card">
                    <h3 class="card-title">Vehicle Status</h3>
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Type</th>
                            <th>Status</th>
                            <th>Route</th>
                        </tr>
                        <c:forEach var="vehicle" items="${vehicles}">
                            <tr>
                                <td>${vehicle.vehicleId}</td>
                                <td>${vehicle.type}</td>
                                <td>${vehicle.status}</td>
                                <td>${vehicle.route}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                
                <div class="card">
                    <h3 class="card-title">Quick Actions</h3>
                    <p><a href="tracking?action=showAddGpsForm" class="btn">Submit GPS Data</a></p>
                    <p><a href="energy?action=showAddForm" class="btn">Record Energy Usage</a></p>
                    <p><a href="tracking?action=showAddOperatorLogForm" class="btn">Log Break/Out-of-Service</a></p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>