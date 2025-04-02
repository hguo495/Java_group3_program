package businesslayer;
import java.sql.SQLException;
/**
 * Command interface for maintenance tasks.
 * @author Hongchen Guo
 */
public interface Command {
    /**
     * Executes the command.
     * @throws SQLException if a database error occurs
     */
    void execute() throws SQLException;
}