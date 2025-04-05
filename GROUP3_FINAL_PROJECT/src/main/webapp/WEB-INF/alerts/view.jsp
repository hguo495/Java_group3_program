<%-- 
    Document   : view.jsp
    Created on : Apr 3, 2025, 2:52:42 p.m.
    Author     : Songmei Jin
--%>

<!-- /WEB-INF/alerts/view.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Alert</title>
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
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        .details {
            margin-bottom: 20px;
        }
        .details p {
            margin: 5px 0;
        }
        .details label {
            font-weight: bold;
        }
        .badge {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
        }
        .badge-maintenance {
            background-color: #e74c3c;
            color: white;
        }
        .badge-fuel {
            background-color: #f39c12;
            color: white;
        }
        .badge-other {
            background-color: #3498db;
            color: white;
        }
        .btn {
            background-color: #7f8c8d;
            color: white;
            border: none;
            padding: 10px 16px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
            margin-top: 20px;
            display: inline-block;
        }
        .btn:hover {
            background-color: #6c757d;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Alert Details</h1>

    <div class="details">
        <p><label>Alert ID:</label> ${alert.alertId}</p>
        <p><label>Type:</label> 
            <span class="badge badge-${alert.type == 'Maintenance' ? 'maintenance' : alert.type == 'Fuel' ? 'fuel' : 'other'}">${alert.type}</span>
        </p>
        <p><label>Vehicle ID:</label> ${alert.vehicleId}</p>
        <p><label>Message:</label> ${alert.message}</p>
        <p><label>Timestamp:</label> ${alert.timestamp}</p>
        <p><label>Status:</label> ${alert.status}</p>
    </div>

    <a href="${pageContext.request.contextPath}/alerts" class="btn">Back to List</a>
</div>
</body>
</html>