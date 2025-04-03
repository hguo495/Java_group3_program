<%-- 
    Document   : schedule.jsp
    Created on : Apr 2, 2025, 10:07:02 a.m.
    Author     : MM
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Schedule Maintenance</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5 bg-white p-4 rounded shadow-sm">
    <h3 class="mb-4">Schedule a New Maintenance Task</h3>
    <form method="post" action="${pageContext.request.contextPath}/maintenance">
        <input type="hidden" name="action" value="schedule"/>

        <div class="mb-3">
            <label class="form-label">Vehicle ID</label>
            <select class="form-control" name="vehicleId" required>
                <option value="">Select a vehicle</option>
                <c:forEach var="vehicle" items="${vehicles}">
                    <option value="${vehicle.vehicleId}">${vehicle.vehicleId}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label class="form-label">Component</label>
            <select class="form-control" name="componentId" required>
                <option value="">Select a component</option>
                <c:forEach var="component" items="${components}">
                    <option value="${component.componentId}">${component.type} (ID: ${component.componentId})</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label class="form-label">Issue</label>
            <input type="text" class="form-control" name="issue" required/>
        </div>
        <div class="mb-3">
            <label class="form-label">Scheduled Date</label>
            <input type="date" class="form-control" name="scheduledDate" required/>
        </div>
        <button type="submit" class="btn btn-primary">Submit Task</button>
        <a href="${pageContext.request.contextPath}/maintenance" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>