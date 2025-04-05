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
            margin: 0;
            padding: 0;
        }
        /* Navigation bar styles */
        .navbar {
            background-color: #2c3e50;
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
        }
        .navbar a {
            float: left;
            color: white;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 16px;
        }
        .navbar a:hover {
            background-color: #3498db;
        }
        .navbar a.active {
            background-color: #3498db;
        }
        /* Dropdown styles */
        .dropdown {
            float: left;
            overflow: hidden;
        }
        .dropdown .dropbtn {
            font-size: 16px;
            border: none;
            outline: none;
            color: white;
            padding: 14px 16px;
            background-color: inherit;
            font-family: inherit;
            margin: 0;
            cursor: pointer;
        }
        .dropdown:hover .dropbtn {
            background-color: #3498db;
        }
        .dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
        }
        .dropdown-content a {
            float: none;
            color: #2c3e50;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            text-align: left;
        }
        .dropdown-content a:hover {
            background-color: #ddd;
            color: #2c3e50;
        }
        .dropdown:hover .dropdown-content {
            display: block;
        }
        
        /* Main content styles */
        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 0 20px;
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
    <!-- Load Google Maps API -->
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDPFsh3dfcAIzq8V_upakHPKdNL--cRdHQ"></script>
    <!-- Map initialization script -->
    <script>
        function initMap() {
            var mapDiv = document.getElementById("map");
            var map = new google.maps.Map(mapDiv, {
                center: { lat: 45.4215, lng: -75.6972 },
                zoom: 12
            });
            
            // Add markers for each vehicle
            <c:forEach items="${deviceList}" var="device">
                var marker = new google.maps.Marker({
                    position: { lat: ${device.latitude}, lng: ${device.longitude} },
                    map: map,
                    title: "Vehicle ID: ${device.vehicleId}"
                });
            </c:forEach>
        }
        window.onload = initMap;
    </script>
</head>
<body>
    <!-- Navigation menu -->
    <div class="navbar">
        <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
        
        <div class="dropdown">
            <button class="dropbtn">Stations</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/stations">View All Stations</a>
                <a href="${pageContext.request.contextPath}/stations?action=addForm">Add New Station</a>
            </div>
        </div>
        
        <div class="dropdown">
            <button class="dropbtn" style="background-color: #3498db;">Tracking</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/tracking?action=gps">GPS Tracking</a>
                <a href="${pageContext.request.contextPath}/tracking?action=allStationReports">Station Reports</a>
                <a href="${pageContext.request.contextPath}/tracking?action=operator">Operator Logs</a>
                <a href="${pageContext.request.contextPath}/tracking?action=addOperatorLogForm">Add Operator Log</a>
            </div>
        </div>
        
        <div class="dropdown">
            <button class="dropbtn">Reports</button>
            <div class="dropdown-content">
                <a href="${pageContext.request.contextPath}/reports">View All Reports</a>
                <a href="${pageContext.request.contextPath}/reports?action=showGenerateForm">Generate Report</a>
            </div>
        </div>
        
        <a href="${pageContext.request.contextPath}/logout" style="float:right;">Logout</a>
    </div>

    <div class="container">
        <h1>GPS Tracking System</h1>
        
        <div class="map-container" id="map">
            <!-- Map loading placeholder -->
            <p>Loading map...</p>
        </div>
        
        <div class="tracking-info">
            <h2>Device Information</h2>
            <table>
                <thead>
                    <tr>
                        <th>Vehicle ID</th>
                        <th>Last Update</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Station</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${deviceList}" var="device">
                        <tr>
                            <td>${device.vehicleId}</td>
                            <td>${device.timestamp}</td>
                            <td>${device.latitude}</td>
                            <td>${device.longitude}</td>
                            <td>${empty device.stationId ? 'En Route' : device.stationId}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/tracking?action=stationReport&vehicleId=${device.vehicleId}">View Report</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>