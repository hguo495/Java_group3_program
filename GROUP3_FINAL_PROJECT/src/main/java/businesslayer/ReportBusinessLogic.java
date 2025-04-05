package businesslayer;

import dataaccesslayer.ReportDAO;
import dataaccesslayer.ReportDAOImpl;
import entity.Report;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Business logic for report operations using Strategy pattern.
 * @author Hongchen Guo
 */
public class ReportBusinessLogic {
    private ReportDAO reportDAO;
    private ReportStrategy reportStrategy;

    /**
     * Constructor that initializes the DAO.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public ReportBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.reportDAO = new ReportDAOImpl(creds);
    }

    /**
     * Gets all reports.
     * @return a list of all reports
     * @throws SQLException if a database error occurs
     */
    public List<Report> getAllReports() throws SQLException {
        return reportDAO.getAllReports();
    }

    /**
     * Generates and adds a new report.
     * @param type the type of report
     * @param data the report data
     * @throws SQLException if a database error occurs
     */
    public void generateReport(String type, String data) throws SQLException {
        setStrategy(type);
        Report report = new Report();
        report.setType(type);
        report.setData(reportStrategy.formatData(data));
        report.setTimestamp(String.valueOf(System.currentTimeMillis()));
        reportDAO.addReport(report);
    }

    /**
     * Sets the report strategy based on type.
     * @param type the report type
     */
    private void setStrategy(String type) {
        switch (type) {
            case "TimeTrack": this.reportStrategy = new TimeTrackReportStrategy(); break;
            case "Consumption": this.reportStrategy = new ConsumptionReportStrategy(); break;
            case "Cost": this.reportStrategy = new CostReportStrategy(); break;
        }
    }
}