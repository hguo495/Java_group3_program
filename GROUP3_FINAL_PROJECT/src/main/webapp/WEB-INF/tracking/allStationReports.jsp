<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Vehicles Station Reports</title>
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
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1, h2, h3 {
            color: #2c3e50;
        }
        .report-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .filter-section {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .filter-row {
            display: flex;
            gap: 15px;
            margin-bottom: 10px;
        }
        .filter-group {
            flex: 1;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #2c3e50;
        }
        select, input {
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
            padding: 10px 16px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #2980b9;
        }
        .vehicle-section {
            margin-bottom: 30px;
            border: 1px solid #eee;
            border-radius: 5px;
            overflow: hidden;
        }
        .vehicle-header {
            background-color: #d6eaf8;
            padding: 12px 15px;
            font-weight: bold;
            color: #2c3e50;
            display: flex;
            justify-content: space-between;
        }
        .view-details {
            font-size: 12px;
            background-color: #3498db;
            color: white;
            padding: 4px 8px;
            border-radius: 3px;
            text-decoration: none;
        }
        .view-details:hover {
            background-color: #2980b9;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px 14px;
            border-bottom: 1px solid #eee;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            color: #2c3e50;
        }
        tr:hover {
            background-color: #f9f9f9;
        }
        .time-cell {
            white-space: nowrap;
        }
        .dwell-time {
            font-weight: bold;
            color: #3498db;
        }
        .no-data {
            text-align: center;
            color: #7f8c8d;
            font-style: italic;
            padding: 15px;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .pagination a {
            color: #3498db;
            padding: 8px 16px;
            text-decoration: none;
            border: 1px solid #ddd;
            margin: 0 4px;
        }
        .pagination a.active {
            background-color: #3498db;
            color: white;
            border: 1px solid #3498db;
        }
        .pagination a:hover:not(.active) {
            background-color: #ddd;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="report-header">
        <h1>All Vehicles Station Reports</h1>
        <a href="${pageContext.request.contextPath}/tracking?action=gps" class="btn">Back to GPS Tracking</a>
    </div>

    <div class="filter-section">
        <form action="${pageContext.request.contextPath}/tracking" method="get">
            <input type="hidden" name="action" value="allStationReports" />
            <div class="filter-row">
                <div class="filter-group">
                    <label for="routeId">Filter by Route:</label>
                    <select id="routeId" name="routeId">
                        <option value="">All Routes</option>
                        <option value="1">Route 1</option>
                        <option value="2">Route 2</option>
                        <option value="3">Route 3</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label for="date">Filter by Date:</label>
                    <input type="date" id="date" name="date" />
                </div>
                <div class="filter-group" style="display: flex; align-items: flex-end;">
                    <button type="submit" class="btn">Apply Filters</button>
                </div>
            </div>
        </form>
    </div>

    <c:choose>
        <c:when test="${not empty allReports}">
            <c:forEach var="vehicleEntry" items="${allReports}">
                <div class="vehicle-section">
                    <div class="vehicle-header">
                        <span>Vehicle ID: ${vehicleEntry.key}</span>
                        <a href="${pageContext.request.contextPath}/tracking?action=stationReport&vehicleId=${vehicleEntry.key}" class="view-details">View Details</a>
                    </div>
                    
                    <c:choose>
                        <c:when test="${not empty vehicleEntry.value}">
                            <table>
                                <thead>
                                <tr>
                                    <th>Station Name</th>
                                    <th>Station ID</th>
                                    <th>Arrival Time</th>
                                    <th>Departure Time</th>
                                    <th>Dwell Time (min)</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="report" items="${vehicleEntry.value}" varStatus="status">
                                    <c:if test="${status.index < 5}"> <!-- Limit to 5 entries per vehicle for clarity -->
                                        <tr>
                                            <td>${report.stationName}</td>
                                            <td>${report.stationId}</td>
                                            <td class="time-cell">${report.arrivalTime}</td>
                                            <td class="time-cell">${report.departureTime}</td>
                                            <td class="dwell-time">${report.dwellTimeMinutes}</td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${vehicleEntry.value.size() > 5}">
                                    <tr>
                                        <td colspan="5" style="text-align: center;">
                                            <a href="${pageContext.request.contextPath}/tracking?action=stationReport&vehicleId=${vehicleEntry.key}">View all ${vehicleEntry.value.size()} entries...</a>
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <div class="no-data">
                                No station visits recorded for this vehicle.
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="no-data" style="padding: 30px;">
                No station reports available for any vehicles.
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>