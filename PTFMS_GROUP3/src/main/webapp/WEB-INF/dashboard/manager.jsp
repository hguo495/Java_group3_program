<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Dashboard - Public Transit Fleet Management System</title>
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
            <span>Welcome, ${userName} (Manager)</span>
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
            <h2>Manager Dashboard</h2>
            
            <div class="dashboard-grid">
                <div class="card">
                    <h3 class="card-title">Alerts</h3>
                    <c:if test="${not empty alerts}">
                        <c:forEach var="alert" items="${alerts}">
                            <div class="alert">
                                <strong>${alert.type}:</strong> ${alert.message} (Vehicle: ${alert.vehicleId})
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty alerts}">
                        <p>No active alerts.</p>
                    </c:if>
                </div>
                
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
                    <h3 class="card-title">Recent Reports</h3>
                    <table>
                        <tr>
                            <th>Type</th>
                            <th>Timestamp</th>
                            <th>Action</th>
                        </tr>
                        <c:forEach var="report" items="${reports}" end="4">
                            <tr>
                                <td>${report.type}</td>
                                <td>${report.timestamp}</td>
                                <td><a href="reports?action=view&id=${report.reportId}" class="btn">View</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                
                <div class="card">
                    <h3 class="card-title">Station Reports</h3>
                    <table>
                        <tr>
                            <th>Vehicle</th>
                            <th>Stations Visited</th>
                            <th>Action</th>
                        </tr>
                        <c:forEach var="entry" items="${stationReports}">
                            <tr>
                                <td>${entry.key}</td>
                                <td>${entry.value.size()}</td>
                                <td><a href="tracking?action=stationReport&vehicleId=${entry.key}" class="btn">View</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>