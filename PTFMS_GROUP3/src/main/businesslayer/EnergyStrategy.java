package businesslayer;

/**
 * Strategy interface for energy efficiency calculations.
 * @author Hongchen Guo
 */
public interface EnergyStrategy {
    /**
     * Checks if energy usage is efficient.
     * @param amountUsed the amount used
     * @param distanceTraveled the distance traveled
     * @return true if efficient, false otherwise
     */
    boolean isEfficient(double amountUsed, double distanceTraveled);
}