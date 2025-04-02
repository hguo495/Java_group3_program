<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GPS Tracking System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .map-container {
            width: 100%;
            height: 500px;
            border: 1px solid #ccc;
            margin-top: 20px;
        }
        .tracking-info {
            margin-top: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>GPS Tracking System</h1>
        
        <div class="map-container" id="map">
            <!-- Map will be displayed here -->
            <p>Loading map...</p>
        </div>
        
        <div class="tracking-info">
            <h2>Device Information</h2>
            <table>
                <thead>
                    <tr>
                        <th>Device ID</th>
                        <th>Last Update</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Speed</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${deviceList}" var="device">
                        <tr>
                            <td>${device.id}</td>
                            <td>${device.lastUpdateTime}</td>
                            <td>${device.latitude}</td>
                            <td>${device.longitude}</td>
                            <td>${device.speed} km/h</td>
                            <td>${device.status}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Map API scripts can be added here -->
    <script>
        // Add JavaScript code for map initialization and tracking functionality
        function initMap() {
            // Map initialization code
            document.getElementById('map').innerHTML = "Map initialized. Please ensure the correct map API is included";
        }
        
        // Call initialization function when page loads
        window.onload = function() {
            initMap();
        };
    </script>
</body>
</html>