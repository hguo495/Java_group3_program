package dataaccesslayer;

import entity.OperatorLog;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for operator log operations.
 * @author Jinze Li
 */
public interface OperatorLogDAO {
    List<OperatorLog> getAllOperatorLogs() throws SQLException;
    void addOperatorLog(OperatorLog log) throws SQLException;
}