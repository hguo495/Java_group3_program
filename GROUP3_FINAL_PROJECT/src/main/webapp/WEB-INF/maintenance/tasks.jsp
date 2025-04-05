<%-- 
    Document   : schedule.jsp
    Created on : April 02 2025
    Author     : MM
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Maintenance Management</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5 bg-white p-4 rounded shadow-sm">
    <h2 class="mb-4">Maintenance Management</h2>

    <form method="get" action="maintenance" class="row g-3 mb-3">
        <input type="hidden" name="action" value="tasks"/>
        <div class="col-md-3">
            <input type="text" name="vehicleId" class="form-control" placeholder="Vehicle ID" value="${param.vehicleId}">
        </div>
        <div class="col-md-3">
            <input type="text" name="component" class="form-control" placeholder="Component" value="${param.component}">
        </div>
        <div class="col-md-3">
            <input type="date" name="startDate" class="form-control" value="${param.startDate}">
        </div>
        <div class="col-md-3">
            <input type="date" name="endDate" class="form-control" value="${param.endDate}">
        </div>
        <div class="col-md-12 d-flex justify-content-end">
            <button class="btn btn-primary me-2">Apply Filters</button>
            <a href="maintenance?action=tasks" class="btn btn-secondary">Reset</a>
        </div>
    </form>

    <div class="mb-3 text-end">
        <a href="maintenance?action=form" class="btn btn-success">+ Add New Maintenance Task</a>
    </div>

    <table class="table table-bordered table-striped">
        <thead class="table-primary">
            <tr>
                <th>ID</th>
                <th>Vehicle ID</th>
                <th>Component</th>
                <th>Issue</th>
                <th>Scheduled Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${not empty tasks}">
                <c:forEach var="task" items="${tasks}">
                    <tr>
                        <td>${task.taskId}</td>
                        <td>${task.vehicleId}</td>
                        <td>${task.componentId}</td>
                        <td>${task.description}</td>
                        <td>${task.scheduledDate}</td>
                        <td>
                            <a href="maintenance?action=edit&taskId=${task.taskId}" class="btn btn-sm btn-primary me-2">Edit</a>
                            <a href="maintenance?action=delete&taskId=${task.taskId}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this task?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="6" class="text-center">No maintenance tasks found</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>
</body>
</html>