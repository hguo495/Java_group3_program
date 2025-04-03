package dataaccesslayer;

import entity.Report;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for report operations.
 * @author Jinze Li
 */
public class ReportDAOImpl implements ReportDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public ReportDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<Report> getAllReports() throws SQLException {
        List<Report> reports = new ArrayList<>();
        String query = "SELECT * FROM reports";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Report report = new Report();
                report.setReportId(rs.getInt("report_id"));
                report.setType(rs.getString("type"));
                report.setData(rs.getString("data"));
                report.setTimestamp(rs.getString("timestamp"));
                reports.add(report);
            }
        }
        return reports;
    }

    @Override
    public void addReport(Report report) throws SQLException {
        String query = "INSERT INTO reports (type, data, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, report.getType());
            stmt.setString(2, report.getData());
            stmt.setString(3, report.getTimestamp());
            stmt.executeUpdate();
        }
    }
}