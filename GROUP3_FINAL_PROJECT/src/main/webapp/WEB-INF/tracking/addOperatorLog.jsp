<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Operator Log</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
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
        .form-group {
            margin-bottom: 15px;
        }
        label {
            font-weight: bold;
            display: block;
            margin-bottom: 6px;
            color: #2c3e50;
        }
        input[type="text"],
        input[type="number"],
        input[type="datetime-local"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        .input-hint {
            font-size: 12px;
            color: #7f8c8d;
            margin-top: 4px;
        }
        .btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 16px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
            cursor: pointer;
            margin-right: 10px;
        }
        .btn:hover {
            background-color: #2980b9;
        }
        .btn.cancel {
            background-color: #7f8c8d;
        }
        .btn.cancel:hover {
            background-color: #6c7a89;
        }
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Add Operator Log</h1>
    
    <c:if test="${not empty error}">
        <div class="error-message">
            ${error}
        </div>
    </c:if>
    
    <form action="${pageContext.request.contextPath}/tracking" method="post">
        <input type="hidden" name="action" value="addOperatorLog" />

        <div class="form-group">
            <label for="vehicleId">Vehicle ID</label>
            <input type="text" id="vehicleId" name="vehicleId" required />
        </div>

        <div class="form-group">
            <label for="operatorId">Operator ID</label>
            <input type="number" id="operatorId" name="operatorId" required />
        </div>

        <div class="form-group">
            <label for="eventType">Event Type</label>
            <select id="eventType" name="eventType" required>
                <option value="">Select event type</option>
                <option value="BREAK">Break</option>
             </select>
        </div>

        <div class="form-group">
            <label for="startTime">Start Time</label>
            <input type="datetime-local" id="startTime" name="startTime" required />
            <div class="input-hint">When the event started</div>
        </div>

        <div class="form-group">
            <label for="endTime">End Time</label>
            <input type="datetime-local" id="endTime" name="endTime" />
            <div class="input-hint">Leave blank if the event is ongoing</div>
        </div>

        <div class="form-group">
            <label for="notes">Notes (Optional)</label>
            <input type="text" id="notes" name="notes" />
            <div class="input-hint">Any additional information about this event</div>
        </div>

        <button type="submit" class="btn">Submit</button>
        <a href="${pageContext.request.contextPath}/tracking?action=operator" class="btn cancel">Cancel</a>
    </form>
</div>

<script>
    // Set default start time to current time
    document.addEventListener('DOMContentLoaded', function() {
        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        
        const currentDateTime = `${year}-${month}-${day}T${hours}:${minutes}`;
        document.getElementById('startTime').value = currentDateTime;
    });
</script>
</body>
</html>