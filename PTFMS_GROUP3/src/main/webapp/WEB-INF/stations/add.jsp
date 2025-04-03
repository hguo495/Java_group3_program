<!--updated on March 31-->
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Station</title>
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
        }
        input[type="text"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #eee;
            border-radius: 4px;
            box-sizing: border-box;
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
    </style>
</head>
<body>
<div class="container">
    <h1>Add New Station</h1>
    <form action="${pageContext.request.contextPath}/stations" method="post">
        <input type="hidden" name="action" value="add" />

        <div class="form-group">
            <label for="stationId">Station ID</label>
            <input type="text" id="stationId" name="stationId" required />
        </div>

        <div class="form-group">
            <label for="name">Station Name</label>
            <input type="text" id="name" name="name" required />
        </div>

        <div class="form-group">
            <label for="location">Location</label>
            <input type="text" id="location" name="location" />
        </div>

        <div class="form-group">
            <label for="routeId">Route ID</label>
            <input type="text" id="routeId" name="routeId" required />
        </div>

        <button type="submit" class="btn">Submit</button>
        <a href="${pageContext.request.contextPath}/stations" class="btn cancel">Cancel</a>
    </form>
</div>
</body>
</html>
