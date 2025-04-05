<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Performance for Operator</title>
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
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .filter-section {
            display: flex;
            gap: 15px;
            margin-bottom: 20px;
        }
        .filter-section > * {
            flex: 1;
        }
        .btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
        }
        .performance-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 15px;
            text-align: center;
        }
        .performance-card {
            background-color: #f9f9f9;
            border-radius: 5px;
            padding: 15px;
        }
        .performance-card h3 {
            margin-bottom: 10px;
            color: #2c3e50;
            font-size: 16px;
        }
        .performance-card .value {
            font-size: 24px;
            font-weight: bold;
            color: #3498db;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Performance for Operator</h1>

    <div class="filter-section">
        <select id="operatorId" name="operatorId">
            <option value