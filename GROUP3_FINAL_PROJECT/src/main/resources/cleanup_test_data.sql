USE ptfms;

-- Delete test alerts and components for BUS001
DELETE FROM alerts WHERE vehicle_id = 'BUS001';
DELETE FROM components WHERE vehicle_id = 'BUS001';
DELETE FROM energy_usage WHERE vehicle_id = 'BUS001';

-- Delete test vehicles created by test code
DELETE FROM vehicles WHERE vehicle_id IN ('BUS998', 'BUS999', '1234rt', 'B999')
   OR vehicle_id LIKE 'TEST_%';