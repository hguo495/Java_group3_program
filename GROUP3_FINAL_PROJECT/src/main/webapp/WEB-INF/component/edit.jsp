<%-- 
    Document   : component-edit.jsp
    Created on : Apr 5, 2025
    Author     : Songmei Jin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Component</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5 bg-white p-4 rounded shadow-sm">
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/components">Components</a></li>
            <li class="breadcrumb-item active" aria-current="page">Edit Component</li>
        </ol>
    </nav>

    <h3 class="mb-4">Update Component Status</h3>
    
    <div class="alert alert-info">
        <div class="d-flex">
            <div class="me-3">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-info-circle" viewBox="0 0 16 16">
                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                    <path d="m8.93 6.588-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533L8.93 6.588zM9 4.5a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
                </svg>
            </div>
            <div>
                <h5 class="alert-heading">Component Status Thresholds</h5>
                <p class="mb-0">Maintenance alerts will be triggered if:</p>
                <ul class="mb-0">
                    <li>Hours used exceeds 1000 hours</li>
                    <li>Wear percentage exceeds 75%</li>
                </ul>
            </div>
        </div>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/components">
        <input type="hidden" name="action" value="update"/>
        <input type="hidden" name="componentId" value="${component.componentId}"/>

        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Component ID</label>
                <input type="text" class="form-control" value="${component.componentId}" readonly/>
            </div>
            <div class="col-md-6">
                <label class="form-label">Vehicle ID</label>
                <input type="text" class="form-control" value="${component.vehicleId}" readonly/>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Component Type</label>
                <input type="text" class="form-control" value="${component.type}" readonly/>
            </div>
            <div class="col-md-6">
                <label class="form-label">Diagnostic Status</label>
                <select class="form-select" name="diagnosticStatus">
                    <option value="Normal" ${component.diagnosticStatus == 'Normal' ? 'selected' : ''}>Normal</option>
                    <option value="Warning" ${component.diagnosticStatus == 'Warning' ? 'selected' : ''}>Warning</option>
                    <option value="Critical" ${component.diagnosticStatus == 'Critical' ? 'selected' : ''}>Critical</option>
                </select>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Hours Used <span class="text-danger">*</span></label>
                <input type="number" class="form-control" name="hoursUsed" value="${component.hoursUsed}" step="0.1" min="0" required/>
                <div class="progress mt-2" style="height: 5px;">
                    <div class="progress-bar ${component.hoursUsed > 1000 ? 'bg-danger' : 'bg-primary'}" role="progressbar" 
                         style="width: ${Math.min(component.hoursUsed/20, 100)}%;" aria-valuemin="0" aria-valuemax="2000"></div>
                </div>
                <small class="text-muted">Current: ${component.hoursUsed} hours | Threshold: 1000 hours</small>
            </div>
            <div class="col-md-6">
                <label class="form-label">Wear Percentage <span class="text-danger">*</span></label>
                <input type="number" class="form-control" name="wearPercentage" value="${component.wearPercentage}" step="0.1" min="0" max="100" required/>
                <div class="progress mt-2" style="height: 5px;">
                    <div class="progress-bar ${component.wearPercentage > 75 ? 'bg-danger' : 'bg-primary'}" role="progressbar" 
                         style="width: ${component.wearPercentage}%;" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
                <small class="text-muted">Current: ${component.wearPercentage}% | Threshold: 75%</small>
            </div>
        </div>

        <div class="d-flex justify-content-between mt-4">
            <a href="${pageContext.request.contextPath}/components" class="btn btn-secondary">Cancel</a>
            <button type="submit" class="btn btn-primary">Update Component</button>
        </div>
    </form>
</div>
</body>
</html>