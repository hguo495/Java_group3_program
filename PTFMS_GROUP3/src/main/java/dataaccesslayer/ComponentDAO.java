package dataaccesslayer;

import entity.Component;
import java.sql.SQLException;
import java.util.List;

/**
 * Updated DAO interface for component operations.
 * @author Jinze Li (Updated with updateComponent method)
 */
public interface ComponentDAO {
    List<Component> getAllComponents() throws SQLException;
    void addComponent(Component component) throws SQLException;
    void updateComponent(Component component) throws SQLException;
    Component getComponentById(int componentId) throws SQLException;
}