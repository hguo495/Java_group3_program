package businesslayer;

/**
 * Strategy for formatting TimeTrack reports.
 * @author Hongchen Guo
 */
public class TimeTrackReportStrategy implements ReportStrategy {
    /**
     * Formats the TimeTrack report data.
     * @param data the raw data
     * @return the formatted data
     */
    @Override
    public String formatData(String data) {
        return "TimeTrack Report: " + data; // Simplified example
    }
}