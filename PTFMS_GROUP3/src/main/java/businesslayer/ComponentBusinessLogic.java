package businesslayer;

import dataaccesslayer.ComponentDAO;
import dataaccesslayer.ComponentDAOImpl;
import entity.Component;
import transferobjects.CredentialsDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Business logic for component operations.
 * @author Hongchen Guo
 */
public class ComponentBusinessLogic {
    private ComponentDAO componentDAO;

    /**
     * Constructor that initializes the DAO.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public ComponentBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.componentDAO = new ComponentDAOImpl(creds);
    }

    /**
     * Gets all components.
     * @return a list of all components
     * @throws SQLException if a database error occurs
     */
    public List<Component> getAllComponents() throws SQLException {
        return componentDAO.getAllComponents();
    }

    /**
     * Adds a new component.
     * @param component the component to add
     * @throws SQLException if a database error occurs
     */
    public void addComponent(Component component) throws SQLException {
        componentDAO.addComponent(component);
    }
}