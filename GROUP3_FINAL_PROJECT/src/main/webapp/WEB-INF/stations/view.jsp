<%-- 
    Document   : view.jsp
    Created on : Apr 3, 2025, 2:36:41 p.m.
    Author     : Songmei Jin
--%>

<!-- /WEB-INF/stations/view.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Station</title>
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
    <h1>Station Details</h1>

    <div class="details">
        <p><label>Station ID:</label> ${station.stationId}</p>
        <p><label>Name:</label> ${station.name}</p>
        <p><label>Location:</label> ${station.location}</p>
        <p><label>Route ID:</label> ${station.routeId}</p>
    </div>

    <a href="${pageContext.request.contextPath}/stations" class="btn">Back to List</a>
</div>
</body>
</html>