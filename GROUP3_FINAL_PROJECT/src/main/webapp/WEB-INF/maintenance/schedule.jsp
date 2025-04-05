<%-- 
    Document   : schedule.jsp
    Created on : Apr 2, 2025, 10:07:02 a.m.
    Author     : MM
    Modified by: Claude AI Assistant
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Schedule Maintenance</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        /* Custom styles for priority indicators */
        .priority-badge {
            display: inline-block;
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
            font-weight: 700;
            border-radius: 0.25rem;
            margin-left: 0.5rem;
        }
        .priority-normal {
            background-color: #0d6efd;
            color: white;
        }
        .priority-high {
            background-color: #fd7e14;
            color: white;
        }
        .priority-urgent {
            background-color: #dc3545;
            color: white;
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-5 bg-white p-4 rounded shadow-sm">
    <!-- Navigation breadcrumb for easy navigation -->
    <nav aria-label="breadcrumb" class="mb-4">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/maintenance">Maintenance</a></li>
            <c:if test="${not empty alertBasedMaintenance}">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/alerts">Alerts</a></li>
            </c:if>
            <li class="breadcrumb-item active" aria-current="page">Schedule Task</li>
        </ol>
    </nav>
    
    <!-- Alert-based maintenance banner - displayed only when creating from an alert -->
    <c:if test="${not empty alertBasedMaintenance}">
        <div class="alert alert-warning mb-4">
            <div class="d-flex align-items-center">
                <div class="me-3">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
                        <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                    </svg>
                </div>
                <div>
                    <h5 class="alert-heading">Creating Maintenance Task from Alert #${alertId}</h5>
                    <p class="mb-0">${alertMessage}</p>
                </div>
            </div>
        </div>
    </c:if>
    
    <h3 class="mb-4">Schedule a New Maintenance Task</h3>
    <form method="post" action="${pageContext.request.contextPath}/maintenance">
        <!-- Hidden fields for form processing -->
        <input type="hidden" name="action" value="schedule"/>
        
        <!-- Include alert ID if task is being created from an alert -->
        <c:if test="${not empty alertId}">
            <input type="hidden" name="alertId" value="${alertId}"/>
        </c:if>

        <!-- Two-column layout for form fields -->
        <div class="row">
            <!-- Vehicle selection with pre-selection from alert if available -->
            <div class="col-md-6 mb-3">
                <label class="form-label">Vehicle ID</label>
                <select class="form-select" name="vehicleId" required>
                    <option value="">Select a vehicle</option>
                    <c:forEach var="vehicle" items="${vehicles}">
                        <option value="${vehicle.vehicleId}" ${vehicle.vehicleId == selectedVehicleId ? 'selected' : ''}>${vehicle.vehicleId}</option>
                    </c:forEach>
                </select>
            </div>
            
            <!-- Component selection with pre-selection from alert if available -->
            <div class="col-md-6 mb-3">
                <label class="form-label">Component</label>
                <select class="form-select" name="componentId" required id="componentSelect">
                    <option value="">Select a component</option>
                    <c:forEach var="component" items="${components}">
                        <option value="${component.componentId}" ${component.componentId == selectedComponentId ? 'selected' : ''}>${component.type} (ID: ${component.componentId})</option>
                    </c:forEach>
                </select>
                <!-- Display component details dynamically via JavaScript -->
                <div id="componentDetails" class="small text-muted mt-1"></div>
            </div>
        </div>

        <!-- Issue description field with pre-filled content from alert if available -->
        <div class="mb-3">
            <label class="form-label">Issue Description</label>
            <textarea class="form-control" name="issue" rows="3" required>${issueDescription}</textarea>
        </div>
        
        <div class="row">
            <!-- Date selector with tomorrow as default value -->
            <div class="col-md-6 mb-3">
                <label class="form-label">Scheduled Date</label>
                <input type="date" class="form-control" name="scheduledDate" required/>
            </div>
            
            <!-- Priority selector with auto-selection based on alert urgency -->
            <div class="col-md-6 mb-3">
                <label class="form-label">Priority</label>
                <select class="form-select" name="priority" id="prioritySelect">
                    <option value="Normal" ${not empty alertBasedMaintenance && alertMessage.contains('URGENT') ? '' : 'selected'}>Normal</option>
                    <option value="High" ${not empty alertBasedMaintenance && alertMessage.contains('HIGH PRIORITY') ? 'selected' : ''}>High</option>
                    <option value="Urgent" ${not empty alertBasedMaintenance && alertMessage.contains('URGENT') ? 'selected' : ''}>Urgent</option>
                </select>
                <!-- Visual indicator for selected priority -->
                <div id="priorityIndicator" class="mt-2"></div>
            </div>
        </div>
        
        <!-- Form action buttons -->
        <div class="d-flex justify-content-between mt-4">
            <a href="${pageContext.request.contextPath}/maintenance" class="btn btn-secondary">Cancel</a>
            <button type="submit" class="btn btn-primary">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar-check me-1" viewBox="0 0 16 16">
                    <path d="M10.854 7.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7.5 9.793l2.646-2.647a.5.5 0 0 1 .708 0z"/>
                    <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0