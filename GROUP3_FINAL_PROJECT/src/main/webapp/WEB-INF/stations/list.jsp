<%-- 
    Document   : view.jsp
    Created on : Apr 3, 2025, 2:36:41 p.m.
    Author     : Hongchen guo
    Modifiedby : Songmei Jin
--%>
<!--updated on March 31-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Station Management</title>
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
        h1 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        .btn {
            background-color: #27ae60;
            color: white;
            border: none;
            padding: 10px 16px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
            margin-bottom: 20px;
            display: inline-block;
        }
        .btn:hover {
            background-color: #1e8449;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #f9f9f9;
            border-radius: 4px;
            overflow: hidden;
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
        .action-btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 13px;
        }
        .action-btn:hover {
            background-color: #2980b9;
        }
        .no-data {
            text-align: center;
            color: #7f8c8d;
            font-style: italic;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Station Management</h1>

    <a href="${pageContext.request.contextPath}/stations?action=addForm" class="btn">+ Add New Station</a>

    <table>
        <thead>
        <tr>
            <th>Station ID</th>
            <th>Name</th>
            <th>Location</th>
            <th>Route ID</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty stations}">
                <c:forEach var="station" items="${stations}">
                    <tr>
                        <td>${station.stationId}</td>
                        <td>${station.name}</td>
                        <td>${station.location}</td>
                        <td>${station.routeId}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/stations?action=view&id=${station.stationId}" class="action-btn">View</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="5" class="no-data">No stations found.</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
</body>
</html>
