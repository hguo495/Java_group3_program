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
    <!-- 加载 Google Maps API -->
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDPFsh3dfcAIzq8V_upakHPKdNL--cRdHQ"></script>
    <!-- 地图初始化脚本 -->
    <script>
        function initMap() {
            var mapDiv = document.getElementById("map");
            var map = new google.maps.Map(mapDiv, {
                center: { lat: 45.4215, lng: -75.6972 },
                zoom: 12
            });
        }
        window.onload = initMap;
    </script>
</head>
<body>
    <div class="container">
        <h1>GPS Tracking System</h1>
        
        <div class="map-container" id="map">
            <!-- 地图加载中 -->
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
                        
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${deviceList}" var="device">
                        <tr>
                            <td>${device.trackingId}</td>
                            <td>${device.timestamp}</td>
                            <td>${device.latitude}</td>
                            <td>${device.longitude}</td>
                            
                            
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
