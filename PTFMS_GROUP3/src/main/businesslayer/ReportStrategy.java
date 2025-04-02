package businesslayer;

/**
 * Strategy interface for report formatting.
 * @author Hongchen Guo
 */
public interface ReportStrategy {
    /**
     * Formats the report data.
     * @param data the raw data
     * @return the formatted data
     */
    String formatData(String data);
}