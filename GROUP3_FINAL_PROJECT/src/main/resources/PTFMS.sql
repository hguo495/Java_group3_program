-- Create the database
DROP DATABASE IF EXISTS ptfms;
CREATE DATABASE ptfms;
USE ptfms;

-- Table for Users (FR-01: User Registration & Authentication)
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL, -- In production, this should be hashed
    type ENUM('Manager', 'Operator') NOT NULL
);

-- Table for Vehicles (FR-02: Vehicle Management)
CREATE TABLE vehicles (
    vehicle_id VARCHAR(50) PRIMARY KEY,
    type ENUM('Diesel Bus', 'Electric Light Rail', 'Diesel-Electric Train') NOT NULL,
    number VARCHAR(50) UNIQUE NOT NULL,
    fuel_type VARCHAR(50) NOT NULL,
    consumption_rate DOUBLE NOT NULL,
    max_passengers INT NOT NULL,
    route VARCHAR(100) NOT NULL,
    status ENUM('Active', 'Inactive', 'Maintenance') DEFAULT 'Active'
);

-- Table for GPS Tracking (FR-03: GPS Tracking)
CREATE TABLE gps_tracking (
    tracking_id INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id VARCHAR(50) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    timestamp DATETIME NOT NULL,
    station_id VARCHAR(50),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

CREATE TABLE stations (
    station_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255),
    route_id VARCHAR(50) NOT NULL
);

CREATE TABLE station_events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id VARCHAR(50) NOT NULL,
    station_id VARCHAR(50) NOT NULL,
    event_type ENUM('ARRIVAL', 'DEPARTURE') NOT NULL,
    timestamp VARCHAR(50) NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),
    FOREIGN KEY (station_id) REFERENCES stations(station_id)
);

-- Table for Operator Logs (FR-03: Operators log breaks/out-of-service)
CREATE TABLE operator_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id VARCHAR(50) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    event_type ENUM('Break', 'OutOfService') NOT NULL,
    operator_id INT NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),
    FOREIGN KEY (operator_id) REFERENCES users(user_id)
);

-- Table for Energy Usage (FR-04: Monitoring Energy/Fuel Consumption)
CREATE TABLE energy_usage (
    usage_id INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id VARCHAR(50) NOT NULL,
    fuel_energy_type VARCHAR(50) NOT NULL,
    amount_used DOUBLE NOT NULL,
    distance_traveled DOUBLE NOT NULL,
    timestamp DATETIME NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

-- Table for Components (FR-05: Alerts for Predictive Maintenance)
CREATE TABLE components (
    component_id INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL, -- e.g., brakes, wheels, pantograph
    hours_used DOUBLE NOT NULL,
    wear_percentage DOUBLE NOT NULL,
    diagnostic_status ENUM('Normal', 'Warning', 'Critical') DEFAULT 'Normal',
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

-- Table for Alerts (FR-05: Alerts for Predictive Maintenance)
CREATE TABLE alerts (
    alert_id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(50) NOT NULL, -- e.g., Maintenance, Fuel
    vehicle_id VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    timestamp DATETIME NOT NULL,
    status ENUM('Pending', 'Resolved') DEFAULT 'Pending',
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

-- Table for Maintenance Tasks (FR-05: Schedule maintenance tasks)
CREATE TABLE maintenance_tasks (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    component_id INT NOT NULL,
    vehicle_id VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    scheduled_date DATE NOT NULL,
    status ENUM('Scheduled', 'In Progress', 'Completed') DEFAULT 'Scheduled',
    technician_id INT,
    FOREIGN KEY (component_id) REFERENCES components(component_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id),
    FOREIGN KEY (technician_id) REFERENCES users(user_id)
);

-- Table for Reports (FR-06: Reporting & Analytics)
CREATE TABLE reports (
    report_id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(50) NOT NULL, -- e.g., Fuel Usage, Maintenance Cost
    data TEXT NOT NULL, -- JSON or formatted text
    timestamp DATETIME NOT NULL
);

-- Add new table for operator trips and performance tracking
CREATE TABLE operator_trips (
    trip_id INT PRIMARY KEY AUTO_INCREMENT,
    operator_id INT NOT NULL,
    vehicle_id VARCHAR(50) NOT NULL,
    route VARCHAR(100) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME,
    scheduled_start_time DATETIME NOT NULL,
    actual_start_time DATETIME NOT NULL,
    is_on_time BOOLEAN DEFAULT FALSE,
    distance_traveled DOUBLE NOT NULL,
    trip_duration INT NOT NULL, -- in minutes
    idle_time INT DEFAULT 0, -- in minutes
    fuel_consumption DOUBLE,
    passenger_count INT,
    trip_date DATE NOT NULL,
    FOREIGN KEY (operator_id) REFERENCES users(user_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

-- Insert sample data for Users
INSERT INTO users (name, email, password, type) VALUES
('John Doe', 'john.doe@transit.com', 'pass123', 'Manager'),
('Jane Smith', 'jane.smith@transit.com', 'pass456', 'Operator'),
('Alice Brown', 'alice.brown@transit.com', 'pass789', 'Operator');

-- Insert sample data for Vehicles
INSERT INTO vehicles (vehicle_id, type, number, fuel_type, consumption_rate, max_passengers, route, status) VALUES
('BUS001', 'Diesel Bus', 'B123', 'Diesel', 0.5, 50, 'Route A', 'Active'),
('RAIL001', 'Electric Light Rail', 'R456', 'Electricity', 2.0, 100, 'Route B', 'Active'),
('TRAIN001', 'Diesel-Electric Train', 'T789', 'Diesel', 1.5, 200, 'Route C', 'Maintenance');

-- Insert sample data for GPSTracking
INSERT INTO gps_tracking (vehicle_id, latitude, longitude, timestamp, station_id) VALUES
('BUS001', 45.4215, -75.6972, '2025-03-27 08:00:00', 'Station1'),
('RAIL001', 45.4250, -75.6900, '2025-03-27 09:00:00', 'Station2'),
('TRAIN001', 45.4300, -75.6800, '2025-03-27 10:00:00', 'Station3');

-- Insert sample data for OperatorLogs
INSERT INTO operator_logs (vehicle_id, start_time, end_time, event_type, operator_id) VALUES
('BUS001', '2025-03-27 12:00:00', '2025-03-27 12:30:00', 'Break', 2),
('RAIL001', '2025-03-27 13:00:00', NULL, 'OutOfService', 3);
-- Insert some sample data for operator trips
INSERT INTO operator_trips (
    operator_id, vehicle_id, route, start_time, end_time, 
    scheduled_start_time, actual_start_time, is_on_time, 
    distance_traveled, trip_duration, idle_time, 
    fuel_consumption, passenger_count, trip_date
) VALUES 
(2, 'BUS001', 'Route A', '2025-03-27 07:55:00', '2025-03-27 08:55:00', 
 '2025-03-27 08:00:00', '2025-03-27 07:55:00', TRUE, 
 50.5, 60, 5, 12.5, 40, '2025-03-27'),
(3, 'RAIL001', 'Route B', '2025-03-27 08:10:00', '2025-03-27 09:20:00', 
 '2025-03-27 08:00:00', '2025-03-27 08:10:00', FALSE, 
 45.0, 70, 10, 55.0, 65, '2025-03-27'),
(2, 'BUS001', 'Route A', '2025-03-28 07:50:00', '2025-03-28 08:50:00', 
 '2025-03-28 08:00:00', '2025-03-28 07:50:00', TRUE, 
 52.0, 60, 3, 11.8, 45, '2025-03-28');


-- Insert sample data for EnergyUsage
INSERT INTO energy_usage (vehicle_id, fuel_energy_type, amount_used, distance_traveled, timestamp) VALUES
('BUS001', 'Diesel', 10.5, 20.0, '2025-03-27 08:30:00'),
('RAIL001', 'Electricity', 50.0, 25.0, '2025-03-27 09:30:00'),
('TRAIN001', 'Diesel', 30.0, 40.0, '2025-03-27 10:30:00');

-- Insert sample data for Components
INSERT INTO components (vehicle_id, type, hours_used, wear_percentage, diagnostic_status) VALUES
('BUS001', 'Brakes', 500.0, 60.0, 'Warning'),
('RAIL001', 'Pantograph', 300.0, 40.0, 'Normal'),
('TRAIN001', 'Engine', 1000.0, 80.0, 'Critical');

-- Insert sample data for Alerts
INSERT INTO alerts (type, vehicle_id, message, timestamp, status) VALUES
('Maintenance', 'BUS001', 'Brakes need inspection', '2025-03-27 09:00:00', 'Pending'),
('Fuel', 'TRAIN001', 'Excessive fuel consumption detected', '2025-03-27 10:00:00', 'Pending');

-- Insert sample data for MaintenanceTasks
INSERT INTO maintenance_tasks (component_id, vehicle_id, description, scheduled_date, status, technician_id) VALUES
(1, 'BUS001', 'Inspect and replace brakes', '2025-03-28', 'Scheduled', 1),
(3, 'TRAIN001', 'Engine overhaul', '2025-03-29', 'Scheduled', NULL);

-- Insert sample data for Reports
INSERT INTO reports (type, data, timestamp) VALUES
('Fuel Usage', 'BUS001: 10.5L, RAIL001: 50kWh', '2025-03-27 11:00:00'),
('Maintenance Cost', 'Estimated cost for BUS001: $500', '2025-03-27 12:00:00');