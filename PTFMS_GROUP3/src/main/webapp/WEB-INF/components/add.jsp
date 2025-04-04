<%-- 
    Document   : add
    Created on : 2025年4月4日, 01:14:30
    Author     : 86139
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Component</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5 bg-white p-4 rounded shadow-sm">
    <h3 class="mb-4">Add New Component</h3>

    <form action="${pageContext.request.contextPath}/component" method="post">
        <div class="mb-3">
            <label class="form-label">Vehicle ID</label>
            <input type="text" class="form-control" name="vehicleId" required placeholder="e.g. BUS-001" />
        </div>

        <div class="mb-3">
            <label class="form-label">Component Type</label>
            <input type="text" class="form-control" name="type" required placeholder="e.g. Brake" />
        </div>

        <div class="mb-3">
            <label class="form-label">Hours Used</label>
            <input type="number" class="form-control" name="hoursUsed" step="0.1" required placeholder="e.g. 11000" />
        </div>

        <div class="mb-3">
            <label class="form-label">Wear Percentage</label>
            <input type="number" class="form-control" name="wearPercentage" step="0.1" required placeholder="e.g. 85" />
        </div>

        <div class="mb-3">
            <label class="form-label">Diagnostic Status</label>
            <input type="text" class="form-control" name="diagnosticStatus" required placeholder="e.g. Degraded" />
        </div>

        <button type="submit" class="btn btn-primary">Add Component</button>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Cancel</a>
    </form>

    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3">${error}</div>
    </c:if>
</div>
</body>
</html>

