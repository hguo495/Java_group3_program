package businesslayer;

/**
 * Strategy for diesel/CNG energy efficiency.
 * @author Hongchen Guo
 */
public class DieselStrategy implements EnergyStrategy {
    /**
     * Checks if diesel/CNG usage is efficient.
     * @param amountUsed the amount used (gallons)
     * @param distanceTraveled the distance traveled (miles)
     * @return true if efficient (e.g., > 5 miles/gallon), false otherwise
     */
    @Override
    public boolean isEfficient(double amountUsed, double distanceTraveled) {
        double efficiency = distanceTraveled / amountUsed;
        return efficiency > 5.0; // Example threshold
    }
}