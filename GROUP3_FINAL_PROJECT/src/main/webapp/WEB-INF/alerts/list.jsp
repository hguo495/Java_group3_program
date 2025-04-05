<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alert Management</title>
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
        .status-pending {
            background-color: #fef9e7;
        }
        .status-resolved {
            background-color: #eafaf1;
        }
        .btn-action {
            padding: 5px 10px;
            margin-right: 5px;
            font-size: 12px;
        }
        .btn-resolve {
            background-color: #2ecc71;
        }
        .btn-resolve:hover {
            background-color: #27ae60;
        }
        .btn-view {
            background-color: #3498db;
        }
        .btn-view:hover {
            background-color: #2980b9;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .pagination a {
            color: black;
            padding: 8px 16px;
            text-decoration: none;
            transition: background-color .3s;
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
        <h1>Alert Management</h1>
        
        <div class="filter-section">
            <form id="filterForm" action="${pageContext.request.contextPath}/alerts" method="get">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="type">Alert Type:</label>
                        <select id="type" name="type">
                            <option value="">All Types</option>
                            <option value="Maintenance" ${param.type == 'Maintenance' ? 'selected' : ''}>Maintenance</option>
                            <option value="Fuel" ${param.type == 'Fuel' ? 'selected' : ''}>Fuel</option>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label for="status">Status:</label>
                        <select id="status" name="status">
                            <option value="">All Statuses</option>
                            <option value="Pending" ${param.status == 'Pending' ? 'selected' : ''}>Pending</option>
                            <option value="Resolved" ${param.status == 'Resolved' ? 'selected' : ''}>Resolved</option>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label for="vehicleId">Vehicle ID:</label>
                        <input type="text" id="vehicleId" name="vehicleId" value="${param.vehicleId}">
                    </div>
                </div>
                <div class="filter-row">
                    <div class="filter-group" style="text-align: right;">
                        <button type="submit" class="btn">Apply Filters</button>
                        <button type="button" class="btn" style="margin-left: 10px;" onclick="resetFilters()">Reset</button>
                    </div>
                </div>
            </form>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>Alert ID</th>
                    <th>Type</th>
                    <th>Vehicle ID</th>
                    <th>Message</th>
                    <th>Timestamp</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${alerts}" var="alert">
                    <tr class="status-${alert.status == 'Pending' ? 'pending' : 'resolved'}">
                        <td>${alert.alertId}</td>
                        <td>
                            <span class="badge badge-${alert.type == 'Maintenance' ? 'maintenance' : alert.type == 'Fuel' ? 'fuel' : 'other'}">${alert.type}</span>
                        </td>
                        <td>${alert.vehicleId}</td>
                        <td>${alert.message}</td>
                        <td>${alert.timestamp}</td>
                        <td>${alert.status}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/alerts?action=view&id=${alert.alertId}" class="btn btn-view btn-action">View</a>
                            <c:if test="${alert.status == 'Pending'}">
                                <form action="${pageContext.request.contextPath}/alerts" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="resolve">
                                    <input type="hidden" name="id" value="${alert.alertId}">
                                    <button type="submit" class="btn btn-resolve btn-action">Resolve</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty alerts}">
                    <tr>
                        <td colspan="7" style="text-align: center;">No alerts found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/alerts?page=${currentPage - 1}&${queryString}">Previous</a>
                </c:if>
                
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage == i}">
                            <a class="active">${i}</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/alerts?page=${i}&${queryString}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                
                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/alerts?page=${currentPage + 1}&${queryString}">Next</a>
                </c:if>
            </div>
        </c:if>
    </div>
    
    <script>
        function resetFilters() {
            // Clear the form inputs
            document.getElementById("type").value = "";
            document.getElementById("status").value = "";
            document.getElementById("vehicleId").value = "";
            // Submit the form to reload the page with cleared filters
            document.getElementById("filterForm").submit();
        }
    </script>
</body>
</html>