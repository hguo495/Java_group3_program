package dataaccesslayer;

import entity.Report;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for report operations.
 * @author Jinze Li
 */
public interface ReportDAO {
    List<Report> getAllReports() throws SQLException;
    void addReport(Report report) throws SQLException;
}