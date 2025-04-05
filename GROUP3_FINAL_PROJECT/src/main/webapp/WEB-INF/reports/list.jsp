<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Report Management</title>
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
            text-decoration: none;
            display: inline-block;
        }
        .btn:hover {
            background-color: #2980b9;
        }
        .btn-add {
            background-color: #2ecc71;
            margin-bottom: 15px;
        }
        .btn-add:hover {
            background-color: #27ae60;
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
        .badge-fuel {
            background-color: #f39c12;
            color: white;
        }
        .badge-maintenance {
            background-color: #e74c3c;
            color: white;
        }
        .badge-performance {
            background-color: #2ecc71;
            color: white;
        }
        .btn-action {
            padding: 5px 10px;
            margin-right: 5px;
            font-size: 12px;
        }
        .btn-view {
            background-color: #3498db;
        }
        .btn-view:hover {
            background-color: #2980b9;
        }
        .btn-export {
            background-color: #9b59b6;
        }
        .btn-export:hover {
            background-color: #8e44ad;
        }
        .data-preview {
            max-width: 300px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            font-family: monospace;
            font-size: 12px;
            color: #7f8c8d;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Report Management</h1>
        
        <a href="${pageContext.request.contextPath}/reports?action=showGenerateForm" class="btn btn-add">+ Generate New Report</a>
        
        <div class="filter-section">
            <form id="filterForm" action="${pageContext.request.contextPath}/reports" method="get">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="type">Report Type:</label>
                        <select id="type" name="type">
                            <option value="">All Types</option>
                            <option value="Fuel Usage" ${param.type == 'Fuel Usage' ? 'selected' : ''}>Fuel Usage</option>
                            <option value="Maintenance Cost" ${param.type == 'Maintenance Cost' ? 'selected' : ''}>Maintenance Cost</option>
                            <option value="Performance" ${param.type == 'Performance' ? 'selected' : ''}>Performance</option>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label for="startDate">Start Date:</label>
                        <input type="date" id="startDate" name="startDate" value="${param.startDate}">
                    </div>
                    <div class="filter-group">
                        <label for="endDate">End Date:</label>
                        <input type="date" id="endDate" name="endDate" value="${param.endDate}">
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
                    <th>Report ID</th>
                    <th>Type</th>
                    <th>Data Preview</th>
                    <th>Generated Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${reports}" var="report">
                    <tr>
                        <td>${report.reportId}</td>
                        <td>
                            <span class="badge badge-${report.type == 'Fuel Usage' ? 'fuel' : report.type == 'Maintenance Cost' ? 'maintenance' : 'performance'}">${report.type}</span>
                        </td>
                        <td>
                            <div class="data-preview">${report.data}</div>
                        </td>
                        <td>${report.timestamp}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/reports?action=view&id=${report.reportId}" class="btn btn-view btn-action">View</a>
                            <a href="${pageContext.request.contextPath}/reports?action=export&id=${report.reportId}" class="btn btn-export btn-action">Export</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty reports}">
                    <tr>
                        <td colspan="5" style="text-align: center;">No reports found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
    
    <script>
        function resetFilters() {
            document.getElementById("type").value = "";
            document.getElementById("startDate").value = "";
            document.getElementById("endDate").value = "";
            document.getElementById("filterForm").submit();
        }
    </script>
</body>
</html>