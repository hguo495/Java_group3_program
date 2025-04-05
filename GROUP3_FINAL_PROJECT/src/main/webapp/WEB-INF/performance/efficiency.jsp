<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Operator Efficiency Metrics</title>
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
        h1 { 
            color: #2c3e50; 
            margin-bottom: 20px; 
        }
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
        .btn:hover { 
            background-color: #2980b9; 
        }
        table {
            width: 100%; 
            border-collapse: collapse; 
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd; 
            padding: 12px; 
            text-align: left;
        }
        th { 
            background-color: #3498db; 
            color: white; 
        }
        tr:nth-child(even) { 
            background-color: #f2f2f2; 
        }
        tr:hover { 
            background-color: #e9e9e9; 
        }
        .action-col { 
            width: 120px; 
        }
        .btn-action { 
            padding: 5px 10px; 
            margin-right: 5px; 
            font-size: 12px; 
        }
        .btn-details { 
            background-color: #2ecc71; 
            color: white; 
        }
        .btn-details:hover { 
            background-color: #27ae60; 
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Operator Efficiency Metrics</h1>

    <div class="filter-section">
        <form id="filterForm" action="${pageContext.request.contextPath}/operator-performance" method="get">
            <input type="hidden" name="action" value="efficiency">
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

    <table>
        <thead>
            <tr>
                <th>Operator ID</th>
                <th>Total Trips</th>
                <th>Avg Trip Duration</th>
                <th>Avg Idle Time</th>
                <th>Total Distance</th>
                <th>Avg Passenger Count</th>
                <th>Avg Fuel Consumption</th>
                <th class="action-col">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="metric" items="${efficiencyMetrics}">
                <tr>
                    <td>${metric.operatorId}</td>
                    <td>${metric.totalTrips}</td>
                    <td>
                        <fmt:formatNumber value="${metric.avgTripDuration}" 
                                          type="number" 
                                          maxFractionDigits="2"/> min
                    </td>
                    <td>
                        <fmt:formatNumber value="${metric.avgIdleTime}" 
                                          type="number" 
                                          maxFractionDigits="2"/> min
                    </td>
                    <td>
                        <fmt:formatNumber value="${metric.totalDistance}" 
                                          type="number" 
                                          maxFractionDigits="2"/> km
                    </td>
                    <td>
                        <fmt:formatNumber value="${metric.avgPassengerCount}" 
                                          type="number" 
                                          maxFractionDigits="2"/>
                    </td>
                    <td>
                        <fmt:formatNumber value="${metric.avgFuelConsumption}" 
                                          type="number" 
                                          maxFractionDigits="2"/>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/operator-performance?action=detailed&operatorId=${metric.operatorId}" 
                           class="btn btn-action btn-details">
                            View Details
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty efficiencyMetrics}">
                <tr>
                    <td colspan="8" style="text-align: center;">No efficiency data available</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</div>

<script>
    function resetFilters() {
        document.getElementById("startDate").value = "";
        document.getElementById("endDate").value = "";
        document.getElementById("filterForm").submit();
    }
</script>
</body>
</html><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Operator Efficiency Metrics</title>
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
        h1 { 
            color: #2c3e50; 
            margin-bottom: 20px; 
        }
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
        .btn:hover { 
            background-color: #2980b9; 
        }
        table {
            width: 100%; 
            border-collapse: collapse; 
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd; 
            padding: 12px; 
            text-align: left;
        }
        th { 
            background-color: #3498db; 
            color: white; 
        }
        tr:nth-child(even) { 
            background-color: #f2f2f2; 
        }
        tr:hover { 
            background-color: #e9e9e9; 
        }
        .action-col { 
            width: 120px; 
        }
        .btn-action { 
            padding: 5px 10px; 
            margin-right: 5px; 
            font-size: 12px; 
        }
        .btn-details { 
            background-color: #2ecc71; 
            color: white; 
        }
        .btn-details:hover { 
            background-color: #27ae60; 
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Operator Efficiency Metrics</h1>

    <div class="filter-section">
        <form id="filterForm" action="${pageContext.request.contextPath}/operator-performance" method="get">
            <input type="hidden" name="action" value="efficiency">
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

    <table>
        <thead>
            <tr>
                <th>Operator ID</th>
                <th>Total Trips</th>
                <th>Avg Trip Duration</th>
                <th>Avg Idle Time</th>
                <th>Total Distance</th>
                <th>Avg Passenger Count</th>
                <th>Avg Fuel Consumption</th>
                <th class="action-col">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="metric" items="${efficiencyMetrics}">
                <tr>
                    <td>${metric.operatorId}</td>
                    <td>${metric.totalTrips}</td>
                    <td>
                        <fmt:formatNumber value="${metric.avgTripDuration}" 
                                          type="number" 
                                          maxFractionDigits="2"/> min
                    </td>
                    <td>
                        <fmt:formatNumber value="${metric.avgIdleTime}" 
                                          type="number" 
                                          maxFractionDigits="2"/> min
                    </td>
                    <td>
                        <fmt:formatNumber value="${metric.totalDistance}" 
                                          type="number" 
                                          maxFractionDigits="2"/> km
                    </td>
                    <td>
                        <fmt:formatNumber value="${metric.avgPassengerCount}" 
                                          type="number" 
                                          maxFractionDigits="2"/>
                    </td>
                    <td>
                        <fmt:formatNumber value="${metric.avgFuelConsumption}" 
                                          type="number" 
                                          maxFractionDigits="2"/>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/operator-performance?action=detailed&operatorId=${metric.operatorId}" 
                           class="btn btn-action btn-details">
                            View Details
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty efficiencyMetrics}">
                <tr>
                    <td colspan="8" style="text-align: center;">No efficiency data available</td>
                </tr>
            </c:if>
        </tbody>
    </table>
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