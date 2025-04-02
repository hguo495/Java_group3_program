package businesslayer;

/**
 * Strategy for formatting Consumption reports.
 * @author Hongchen Guo
 */
public class ConsumptionReportStrategy implements ReportStrategy {
    /**
     * Formats the Consumption report data.
     * @param data the raw data
     * @return the formatted data
     */
    @Override
    public String formatData(String data) {
        return "Consumption Report: " + data; // Simplified example
    }
}