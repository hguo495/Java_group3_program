package entity;

/**
 * Entity class for the reports table.
 * @author Hongchen Guo
 */
public class Report {
    private int reportId;
    private String type;
    private String data;
    private String timestamp;

    /**
     * Gets the report ID.
     * @return the report ID
     */
    public int getReportId() {
        return reportId;
    }

    /**
     * Sets the report ID.
     * @param reportId the report ID
     */
    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    /**
     * Gets the type.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the data.
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data.
     * @param data the data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Gets the timestamp.
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp.
     * @param timestamp the timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}