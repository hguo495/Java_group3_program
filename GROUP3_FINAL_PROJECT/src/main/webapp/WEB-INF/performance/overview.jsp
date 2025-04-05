<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Operator Detailed Performance</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 { color: #2c3e50; margin-bottom: 20px; }
        .filter-section { 
            background-color: #f9f9f9; 
            padding: 15px; 
            border-radius: 5px; 
            margin-bottom: 20px; 
        }
        .filter-row { 
            display: flex; 
            flex-wrap: wrap; 
            gap: 15px; 
            margin-bottom: 10px; 
        }
        .filter-group { 
            flex: 1; 
            min-width: 200px; 
        }
        label { 
            display: block; 
            margin-bottom: 5px; 
            font-weight: bold; 
        }
        input, select {
            width: 100%; 
            padding: 8px; 
            border: 1px solid #ddd; 
            border-radius: 4px; 
            box-sizing: border-box;
        }
        .btn {
            background-color: #3498db; 
            color: white; 
            border: none; 
            padding: 10px 15px;
            border-radius: 4px; 
            cursor: pointer; 
            font-size: 14px;
        }
        .btn:hover { background-color: #2980b9; }
        .performance-section {
            background-color: #f9f9f9;
            border-radius: 5px;
            padding: 20px;
            margin-bottom: 20px;
        }
        .performance-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 15px;
        }
        .performance-card {
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            text-align: center;
        }
        .performance-card h3 {
            margin-bottom: 10px;
            color: #2c3e50;
        }
        .performance-card .value {
            font-size: 24px;
            font-weight: bold;
            color: #3498db;
        }
        .back-link {
            display: block;
            margin-top: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Detailed Performance for Operator ${detailedPerformance.operatorId}</h1>

    <div class="filter-section">
        <form id="filterForm" action="${pageContext.request.contextPath}/operator-performance" method="get">
            <input type="hidden" name="action" value="detailed">
            <input type="hidden" name="operatorId" value="${detailedPerformance.operatorId}">
            <div class="filter-row">
                <div class="filter-group">
                    <label for="startDate">Start Date:</label>
                    <input type="date" id="startDate" name="startDate" value="${param.startDate}">
                </div>
                <div class="filter-group">
                    <label for="endDate">End Date:</label>
                    <input type="date" id="endDate" name="endDate" value="${param.endDate}">
                </div>
                <div class="filter-group" style="align-self: flex-end;">
                    <button type="submit" class="btn">Apply Filters</button>
                    <button type="button" class="btn" style="margin-left: 10px;" onclick="resetFilters()">Reset</button>
                </div>
            </div>
        </form>
    </div>

    <div class="performance-section">
        <div class="performance-grid">
            <div class="performance-card">
                <h3>Total Trips</h3>
                <div class="value">${detailedPerformance.totalTrips}</div>
            </div>
            <div class="performance-card">
                <h3>On-Time Trips</h3>
                <div class="value">${detailedPerformance.onTimeTrips}</div>
            </div>
            <div class="performance-card">
                <h3>On-Time Percentage</h3>
                <div class="value">
                    <fmt:formatNumber value="${detailedPerformance.onTimePercentage}" 
                                      type="number" 
                                      maxFractionDigits="2"/>%
                </div>
            </div>
            <div class="performance-card">
                <h3>Avg Trip Duration</h3>
                <div class="value">
                    <fmt:formatNumber value="${detailedPerformance.avgTripDuration}" 
                                      type="number" 
                                      maxFractionDigits="2"/> min
                </div>
            </div>
            <div class="performance-card">
                <h3>Avg Idle Time</h3>
                <div class="value">
                    <fmt:formatNumber value="${detailedPerformance.avgIdleTime}" 
                                      type="number" 
                                      maxFractionDigits="2"/> min
                </div>
            </div>
            <div class="performance-card">
                <h3>Total Distance</h3>
                <div class="value">
                    <fmt:formatNumber value="${detailedPerformance.totalDistance}" 
                                      type="number" 
                                      maxFractionDigits="2"/> km
                </div>
            </div>
        </div>
    </div>

    <div class="back-link">
        <a href="${pageContext.request.contextPath}/operator-performance" class="btn">Back to Overview</a>
    </div>
</div>

<script>
    function resetFilters() {
        document.getElementById("startDate").value = "";
        document.getElementById("endDate").value = "";
        document.getElementById("filterForm").submit();
    }
</script>
</body>
</html>