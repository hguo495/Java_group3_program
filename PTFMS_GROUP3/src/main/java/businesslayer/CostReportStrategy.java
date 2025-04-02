package businesslayer;

/**
 * Strategy for formatting Cost reports.
 * @author Hongchen Guo
 */
public class CostReportStrategy implements ReportStrategy {
    /**
     * Formats the Cost report data.
     * @param data the raw data
     * @return the formatted data
     */
    @Override
    public String formatData(String data) {
        return "Cost Report: " + data; // Simplified example
    }
}