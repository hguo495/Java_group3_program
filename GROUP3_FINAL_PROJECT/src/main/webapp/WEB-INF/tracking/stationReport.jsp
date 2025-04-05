<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vehicle Station Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1, h2 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        .report-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .vehicle-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .vehicle-info p {
            margin: 5px 0;
        }
        .vehicle-info strong {
            color: #2c3e50;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #f9f9f9;
            border-radius: 4px;
            overflow: hidden;
            margin-bottom: 20px;
        }
        th, td {
            padding: 12px 14px;
            border-bottom: 1px solid #eee;
            text-align: left;
        }
        th {
            background-color: #d6eaf8;
            color: #2c3e50;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .time-cell {
            white-space: nowrap;
        }
        .dwell-time {
            font-weight: bold;
            color: #3498db;
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
        .no-data {
            text-align: center;
            color: #7f8c8d;
            font-style: italic;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="report-header">
        <h1>Vehicle Station Report</h1>
        <a href="${pageContext.request.contextPath}/tracking?action=gps" class="btn">Back to GPS Tracking</a>
    </div>

    <div class="vehicle-info">
        <h2>Vehicle Information</h2>
        <p><strong>Vehicle ID:</strong> ${vehicleId}</p>
    </div>

    <c:choose>
        <c:when test="${not empty report}">
            <table>
                <thead>
                <tr>
                    <th>Station Name</th>
                    <th>Station ID</th>
                    <th>Arrival Time</th>
                    <th>Departure Time</th>
                    <th>Dwell Time (minutes)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${report}">
                    <tr>
                        <td>${entry.stationName}</td>
                        <td>${entry.stationId}</td>
                        <td class="time-cell">${entry.arrivalTime}</td>
                        <td class="time-cell">${entry.departureTime}</td>
                        <td class="dwell-time">${entry.dwellTimeMinutes}</td>
                    </tr>
                </c:forEach>
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
</body>
</html>