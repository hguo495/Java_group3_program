<%-- 
    Document   : tasks.jsp
    Created on : April 02 2025
    Author     : Songmei Jin
    
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Maintenance Management</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        /* Custom styles for enhancing the task display */
        .dashboard-card {
            transition: transform 0.2s;
        }
        .dashboard-card:hover {
            transform: translateY(-5px);
        }
        
        /* Success message styling for task operations */
        .success-message {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        
        /* Custom styles for dashboard metrics */
        .metric-value {
            font-size: 2.5rem;
            font-weight: bold;
            color: #333;
        }
        
        /* Table row highlighting based on priority */
        .urgent-row {
            background-color: rgba(255, 193, 193, 0.5) !important;
        }
        
        .high-priority-row {
            background-color: rgba(255, 243, 205, 0.5) !important;
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-5 bg-white p-4 rounded shadow-sm">
    <!-- Display success message if present in session -->
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="success-message">
            <i class="bi bi-check-circle-fill"></i> ${sessionScope.successMessage}
        </div>
        <% session.removeAttribute("successMessage"); %>
    </c:if>

    <!-- Header section with title and action buttons -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Maintenance Management</h2>
        <div>
            <!-- Alert button with notification badge showing pending count -->
            <a href="${pageContext.request.contextPath}/alerts" class="btn btn-warning position-relative me-2">
                Maintenance Alerts
                <c:if test="${pendingAlertsCount > 0}">
                    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                        ${pendingAlertsCount}
                        <span class="visually-hidden">pending alerts</span>
                    </span>
                </c:if>
            </a>
            <!-- Button to add new maintenance task -->
            <a href="${pageContext.request.contextPath}/maintenance?action=form" class="btn btn-success">+ Add New Task</a>
        </div>
    </div>

    <!-- Filter section for task searching and filtering -->
    <form method="get" action="maintenance" class="row g-3 mb-3">
        <input type="hidden" name="action" value="tasks"/>
        <!-- Vehicle ID filter -->
        <div class="col-md-3">
            <input type="text" name="vehicleId" class="form-control" placeholder="Vehicle ID" value="${param.vehicleId}">
        </div>
        <!-- Component filter -->
        <div class="col-md-3">
            <input type="text" name="component" class="form-control" placeholder="Component" value="${param.component}">
        </div>
        <!-- Date range filters -->
        <div class="col-md-3">
            <input type="date" name="startDate" class="form-control" value="${param.startDate}" placeholder="Start Date">
        </div>
        <div class="col-md-3">
            <input type="date" name="endDate" class="form-control" value="${param.endDate}" placeholder="End Date">
        </div>
        <!-- Filter action buttons -->
        <div class="col-md-12 d-flex justify-content-end">
            <button class="btn btn-primary me-2">Apply Filters</button>
            <a href="maintenance?action=tasks" class="btn btn-secondary">Reset</a>
        </div>
    </form>

    <!-- Maintenance tasks table -->
    <table class="table table-bordered table-hover">
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
                <c:forEach var="task" items="${tasks}" varStatus="status">
                    <!-- Task row with data -->
                    <tr id="task-${task.taskId}">
                        <td>${task.taskId}</td>
                        <td>${task.vehicleId}</td>
                        <td>${task.componentId}</td>
                        <td>${task.description}</td>
                        <td>${task.scheduledDate}</td>
                        <td>
                            <!-- Action buttons for each task -->
                            <a href="maintenance?action=edit&taskId=${task.taskId}" class="btn btn-sm btn-primary me-2">Edit</a>
                            <a href="maintenance?action=delete&taskId=${task.taskId}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this task?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <!-- Displayed when no tasks are found -->
                <tr>
                    <td colspan="6" class="text-center">No maintenance tasks found</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</div>

<!-- Component status dashboard section -->
<div class="container mt-4 bg-white p-4 rounded shadow-sm">
    <h4 class="mb-3">Component Status Dashboard</h4>
    <div class="row">
        <!-- Components needing maintenance card -->
        <div class="col-md-6">
            <div class="card mb-3 dashboard-card">
                <div class="card-header bg-primary text-white">
                    Components Needing Maintenance
                </div>
                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <div class="display-4 me-3">${pendingAlertsCount}</div>
                        <div>
                            <p class="mb-0">Components have exceeded maintenance thresholds.</p>
                            <a href="${pageContext.request.contextPath}/alerts" class="btn btn-sm btn-outline-primary mt-2">View Details</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Scheduled maintenance tasks card -->
        <div class="col-md-6">
            <div class="card mb-3 dashboard-card">
                <div class="card-header bg-success text-white">
                    Scheduled Maintenance Tasks
                </div>
                <div class="card-body">
                    <div class="d-flex align-items-center">
                        <div class="display-4 me-3">${tasks.size()}</div>
                        <div>
                            <p class="mb-0">Maintenance tasks are currently scheduled.</p>
                            <a href="#" class="btn btn-sm btn-outline-success mt-2" onclick="window.print();">Print Schedule</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // JavaScript to enhance the maintenance tasks display
    document.addEventListener('DOMContentLoaded', function() {
        // Highlight rows with urgent maintenance issues
        const tableRows = document.querySelectorAll('tbody tr');
        tableRows.forEach(row => {
            const issueCell = row.querySelector('td:nth-child(4)');
            if (issueCell && issueCell.textContent.includes('URGENT')) {
                row.classList.add('table-danger', 'urgent-row');
            } else if (issueCell && issueCell.textContent.includes('HIGH PRIORITY')) {
                row.classList.add('table-warning', 'high-priority-row');
            }
        });
        
        // Add fading effect for success message if present
        const successMessage = document.querySelector('.success-message');
        if (successMessage) {
            setTimeout(function() {
                successMessage.style.transition = 'opacity 1s';
                successMessage.style.opacity = '0';
                setTimeout(function() {
                    successMessage.style.display = 'none';
                }, 1000);
            }, 5000);
        }
    });
</script>
</body>
</html>