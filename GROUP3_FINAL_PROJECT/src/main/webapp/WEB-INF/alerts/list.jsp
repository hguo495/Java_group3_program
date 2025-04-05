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
        .status-processing {
            background-color: #f9e79f;
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
        .btn-schedule {
            background-color: #9b59b6;
        }
        .btn-schedule:hover {
            background-color: #8e44ad;
        }
        .btn-view {
            background-color: #3498db;
        }
        .btn-view:hover {
            background-color: #2980b9;
        }
        .btn-back {
            background-color: #7f8c8d;
            margin-bottom: 20px;
            display: inline-flex;
            align-items: center;
        }
        .btn-back:hover {
            background-color: #6c7a7a;
        }
        .btn-back svg {
            margin-right: 5px;
        }
        .navigation {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .breadcrumb {
            display: flex;
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .breadcrumb li {
            display: inline;
        }
        .breadcrumb li+li:before {
            content: ">";
            padding: 0 8px;
            color: #777;
        }
        .breadcrumb a {
            color: #3498db;
            text-decoration: none;
        }
        .breadcrumb a:hover {
            text-decoration: underline;
        }
        .summary-cards {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }
        .summary-card {
            flex: 1;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            padding: 15px;
            display: flex;
            align-items: center;
        }
        .summary-card-icon {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
            font-size: 20px;
            font-weight: bold;
            color: white;
        }
        .summary-card-content h3 {
            margin: 0;
            color: #7f8c8d;
            font-size: 14px;
        }
        .summary-card-content p {
            margin: 5px 0 0;
            font-size: 24px;
            font-weight: bold;
        }
        .pending-icon {
            background-color: #e74c3c;
        }
        .processing-icon {
            background-color: #f39c12;
        }
        .resolved-icon {
            background-color: #2ecc71;
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
        .action-buttons {
            display: flex;
            gap: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="navigation">
            <ul class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}/maintenance">Maintenance</a></li>
                <li>Alert Management</li>
            </ul>
            <a href="${pageContext.request.contextPath}/maintenance" class="btn btn-back">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z"/>
                </svg>
                Back to Maintenance
            </a>
        </div>
        
        <h1>Alert Management</h1>
        
        <!-- Summary Cards -->
        <div class="summary-cards">
            <div class="summary-card">
                <div class="summary-card-icon pending-icon">
                    ${pendingCount}
                </div>
                <div class="summary-card-content">
                    <h3>Pending Alerts</h3>
                    <p>Need Attention</p>
                </div>
            </div>
            <div class="summary-card">
                <div class="summary-card-icon processing-icon">
                    ${processingCount}
                </div>
                <div class="summary-card-content">
                    <h3>Processing</h3>
                    <p>In Progress</p>
                </div>
            </div>
            <div class="summary-card">
                <div class="summary-card-icon resolved-icon">
                    ${resolvedCount}
                </div>
                <div class="summary-card-content">
                    <h3>Resolved</h3>
                    <p>Completed</p>
                </div>
            </div>
        </div>
        
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
                            <option value="Processing" ${param.status == 'Processing' ? 'selected' : ''}>Processing</option>
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
                    <tr class="status-${alert.status == 'Pending' ? 'pending' : (alert.status == 'Processing' ? 'processing' : 'resolved')}">
                        <td>${alert.alertId}</td>
                        <td>
                            <span class="badge badge-${alert.type == 'Maintenance' ? 'maintenance' : alert.type == 'Fuel' ? 'fuel' : 'other'}">${alert.type}</span>
                        </td>
                        <td>${alert.vehicleId}</td>
                        <td>${alert.message}</td>
                        <td>${alert.timestamp}</td>
                        <td>${alert.status}</td>
                        <td>
                            <div class="action-buttons">
                                <a href="${pageContext.request.contextPath}/alerts?action=view&id=${alert.alertId}" class="btn btn-view btn-action">View</a>
                                
                                <c:if test="${alert.status == 'Pending' || alert.status == 'Processing'}">
                                    <form action="${pageContext.request.contextPath}/alerts" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="resolve">
                                        <input type="hidden" name="id" value="${alert.alertId}">
                                        <button type="submit" class="btn btn-resolve btn-action">Resolve</button>
                                    </form>
                                </c:if>
                                
                                <!-- Add the Schedule Maintenance button for maintenance alerts -->
                                <c:if test="${alert.type == 'Maintenance' && (alert.status == 'Pending' || alert.status == 'Processing')}">
                                    <a href="${pageContext.request.contextPath}/maintenance?action=form&fromAlert=${alert.alertId}" 
                                       class="btn btn-schedule btn-action">Schedule</a>
                                </c:if>
                            </div>
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