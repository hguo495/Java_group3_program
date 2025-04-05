<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Operator Logs</title>
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
        h1 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        .header-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
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
        .btn-add {
            background-color: #27ae60;
        }
        .btn-add:hover {
            background-color: #219955;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
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
            background-color: #f9f9f9;
        }
        .status-active {
            color: #27ae60;
            font-weight: bold;
        }
        .status-completed {
            color: #7f8c8d;
        }
        .event-type {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            color: white;
        }
        .event-break {
            background-color: #f39c12;
        }
        .event-out-of-service {
            background-color: #e74c3c;
        }
        .event-maintenance {
            background-color: #3498db;
        }
        .event-fuel {
            background-color: #9b59b6;
        }
        .event-other {
            background-color: #7f8c8d;
        }
        .no-data {
            text-align: center;
            color: #7f8c8d;
            font-style: italic;
            padding: 30px;
            background-color: #f8f9fa;
            border-radius: 5px;
            margin-top: 20px;
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
    <div class="header-section">
        <h1>Operator Logs</h1>
        <a href="${pageContext.request.contextPath}/tracking?action=addOperatorLogForm" class="btn btn-add">+ Add New Log</a>
    </div>

    <div class="filter-section">
        <form action="${pageContext.request.contextPath}/tracking" method="get">
            <input type="hidden" name="action" value="operator" />
            <div class="filter-row">
                <div class="filter-group">
                    <label for="vehicleId">Filter by Vehicle:</label>
                    <input type="text" id="vehicleId" name="vehicleId" placeholder="Enter Vehicle ID" />
                </div>
                <div class="filter-group">
                    <label for="eventType">Filter by Event Type:</label>
                    <select id="eventType" name="eventType">
                        <option value="">All Event Types</option>
                        <option value="BREAK">Break</option>
                        <option value="OUT_OF_SERVICE">Out of Service</option>
                        <option value="MAINTENANCE">Maintenance</option>
                        <option value="FUEL_STOP">Fuel Stop</option>
                        <option value="OTHER">Other</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label for="dateRange">Date Range:</label>
                    <input type="date" id="dateRange" name="dateRange" />
                </div>
                <div class="filter-group" style="display: flex; align-items: flex-end;">
                    <button type="submit" class="btn">Apply Filters</button>
                </div>
            </div>
        </form>
    </div>

    <c:choose>
        <c:when test="${not empty logs}">
            <table>
                <thead>
                <tr>
                    <th>Log ID</th>
                    <th>Vehicle ID</th>
                    <th>Operator ID</th>
                    <th>Event Type</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="log" items="${logs}">
                    <tr>
                        <td>${log.logId}</td>
                        <td>${log.vehicleId}</td>
                        <td>${log.operatorId}</td>
                        <td>
                            <span class="event-type 
                                ${log.eventType == 'BREAK' ? 'event-break' : 
                                log.eventType == 'OUT_OF_SERVICE' ? 'event-out-of-service' : 
                                log.eventType == 'MAINTENANCE' ? 'event-maintenance' : 
                                log.eventType == 'FUEL_STOP' ? 'event-fuel' : 'event-other'}">
                                ${log.eventType}
                            </span>
                        </td>
                        <td>${log.startTime}</td>
                        <td>${empty log.endTime ? 'Ongoing' : log.endTime}</td>
                        <td>
                            <span class="${empty log.endTime ? 'status-active' : 'status-completed'}">
                                ${empty log.endTime ? 'Active' : 'Completed'}
                            </span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            
            <!-- Simple pagination example -->
            <div class="pagination">
                <a href="#">&laquo;</a>
                <a href="#" class="active">1</a>
                <a href="#">2</a>
                <a href="#">3</a>
                <a href="#">&raquo;</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="no-data">
                No operator logs found. Add a new log using the button above.
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>