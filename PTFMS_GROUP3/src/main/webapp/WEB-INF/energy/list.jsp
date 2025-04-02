<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Energy Consumption Management</title>
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
        .action-col {
            width: 120px;
        }
        .btn-action {
            padding: 5px 10px;
            margin-right: 5px;
            font-size: 12px;
        }
        .btn-edit {
            background-color: #f39c12;
        }
        .btn-edit:hover {
            background-color: #e67e22;
        }
        .btn-delete {
            background-color: #e74c3c;
        }
        .btn-delete:hover {
            background-color: #c0392b;
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
        <h1>Energy Consumption Management</h1>
        
        <div class="filter-section">
            <form id="filterForm" action="${pageContext.request.contextPath}/energy/list" method="get">
                <div class="filter-row">
                    <div class="filter-group">
                        <label for="facility">Facility:</label>
                        <select id="facility" name="facilityId">
                            <option value="">All Facilities</option>
                            <c:forEach items="${facilityList}" var="facility">
                                <option value="${facility.id}" ${param.facilityId == facility.id ? 'selected' : ''}>${facility.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label for="energyType">Energy Type:</label>
                        <select id="energyType" name="energyType">
                            <option value="">All Types</option>
                            <option value="electricity" ${param.energyType == 'electricity' ? 'selected' : ''}>Electricity</option>
                            <option value="gas" ${param.energyType == 'gas' ? 'selected' : ''}>Natural Gas</option>
                            <option value="water" ${param.energyType == 'water' ? 'selected' : ''}>Water</option>
                            <option value="steam" ${param.energyType == 'steam' ? 'selected' : ''}>Steam</option>
                            <option value="solar" ${param.energyType == 'solar' ? 'selected' : ''}>Solar</option>
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
        
        <a href="${pageContext.request.contextPath}/energy/add" class="btn btn-add">+ Add New Energy Record</a>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Facility</th>
                    <th>Energy Type</th>
                    <th>Reading Date</th>
                    <th>Consumption</th>
                    <th>Unit</th>
                    <th>Cost</th>
                    <th>CO2 Emission (kg)</th>
                    <th class="action-col">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${energyRecords}" var="record">
                    <tr>
                        <td>${record.id}</td>
                        <td>${record.facilityName}</td>
                        <td>${record.energyType}</td>
                        <td><fmt:formatDate value="${record.readingDate}" pattern="yyyy-MM-dd" /></td>
                        <td><fmt:formatNumber value="${record.consumption}" pattern="#,##0.00" /></td>
                        <td>${record.unit}</td>
                        <td>$<fmt:formatNumber value="${record.cost}" pattern="#,##0.00" /></td>
                        <td><fmt:formatNumber value="${record.co2Emission}" pattern="#,##0.00" /></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/energy/edit?id=${record.id}" class="btn btn-edit btn-action">Edit</a>
                            <a href="#" onclick="confirmDelete(${record.id})" class="btn btn-delete btn-action">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty energyRecords}">
                    <tr>
                        <td colspan="9" style="text-align: center;">No energy records found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/energy/list?page=${currentPage - 1}&${queryString}">Previous</a>
            </c:if>
            
            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage == i}">
                        <a class="active">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/energy/list?page=${i}&${queryString}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            
            <c:if test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/energy/list?page=${currentPage + 1}&${queryString}">Next</a>
            </c:if>
        </div>
    </div>
    
    <script>
        function confirmDelete(id) {
            if (confirm("Are you sure you want to delete this energy record?")) {
                window.location.href = "${pageContext.request.contextPath}/energy/delete?id=" + id;
            }
        }
        
        function resetFilters() {
            document.getElementById("facility").value = "";
            document.getElementById("energyType").value = "";
            document.getElementById("startDate").value = "";
            document.getElementById("endDate").value = "";
            document.getElementById("filterForm").submit();
        }
    </script>
</body>
</html>