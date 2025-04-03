package dataaccesslayer;

import entity.Component;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for component operations.
 * @author Jinze Li
 */
public interface ComponentDAO {
    List<Component> getAllComponents() throws SQLException;
    void addComponent(Component component) throws SQLException;
}