<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Report</title>
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
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 20px;
        }
        .report-header {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .report-id {
            font-size: 14px;
            color: #7f8c8d;
        }
        .report-type {
            font-size: 18px;
            font-weight: bold;
            color: #3498db;
        }
        .report-timestamp {
            font-size: 14px;
            color: #7f8c8d;
        }
        .report-content {
            padding: 15px;
            border: 1px solid #eee;
            border-radius: 5px;
            white-space: pre-wrap;
            font-family: monospace;
            background-color: #f9f9f9;
        }
        .btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
            font-size: 14px;
        }
        .btn:hover {
            background-color: #2980b9;
        }
        .report-badge {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
            background-color: #3498db;
            color: white;
            margin-left: 10px;
        }
        .badge-fuel {
            background-color: #f39c12;
        }
        .badge-maintenance {
            background-color: #e74c3c;
        }
        .badge-performance {
            background-color: #2ecc71;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>View Report</h1>
        
        <div class="report-header">
            <div>
                <span class="report-id">Report ID: ${report.reportId}</span>
                <div class="report-type">
                    ${report.type}
                    <span class="report-badge ${report.type == 'Fuel Usage' ? 'badge-fuel' : report.type == 'Maintenance Cost' ? 'badge-maintenance' : 'badge-performance'}">${report.type}</span>
                </div>
            </div>
            <div class="report-timestamp">
                Generated: ${report.timestamp}
            </div>
        </div>
        
        <h2>Report Data</h2>
        <div class="report-content">
            ${report.data}
        </div>
        
        <a href="${pageContext.request.contextPath}/reports" class="btn">Back to Reports</a>
    </div>
</body>
</html>