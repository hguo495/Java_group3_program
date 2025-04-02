/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;

import businesslayer.EnergyStrategy;

/**
 * Strategy for electric energy efficiency.
 * @author Hongchen Guo
 */
public class ElectricStrategy implements EnergyStrategy {
    /**
     * Checks if electric usage is efficient.
     * @param amountUsed the amount used (kWh)
     * @param distanceTraveled the distance traveled (miles)
     * @return true if efficient (e.g., < 2 kWh/mile), false otherwise
     */
    @Override
    public boolean isEfficient(double amountUsed, double distanceTraveled) {
        double efficiency = amountUsed / distanceTraveled;
        return efficiency < 2.0; // Example threshold
    }
}