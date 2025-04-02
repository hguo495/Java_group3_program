package businesslayer;

import dataaccesslayer.UserDAO;
import dataaccesslayer.UserDAOImpl;
import entity.User;
import transferobjects.CredentialsDTO;

import java.sql.SQLException;

/**
 * Business logic class for user operations.
 * @author Hongchen Guo
 * 
 */
public class UserBusinessLogic {
    private UserDAO userDAO;

    /**
     * Constructor initializing the DAO with database credentials.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public UserBusinessLogic(CredentialsDTO creds) throws SQLException {
        this.userDAO = new UserDAOImpl(creds);
    }

    /**
     * Registers a new user.
     * @param name     the user's name
     * @param email    the user's email
     * @param password the user's password
     * @param type     the user's type (Manager or Operator)
     * @throws SQLException if a database error occurs
     */
    public void registerUser(String name, String email, String password, String type) throws SQLException {
        User existingUser = userDAO.getUserByEmail(email);
        if (existingUser != null) {
            throw new SQLException("User with email " + email + " already exists.");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password); // In production, hash the password
        user.setType(type);

        userDAO.addUser(user);
    }

    /**
     * Authenticates a user based on email and password.
     * @param email    the user's email
     * @param password the user's password
     * @return the authenticated User entity, or null if authentication fails
     * @throws SQLException if a database error occurs
     */
    public User authenticateUser(String email, String password) throws SQLException {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) { // In production, compare hashed passwords
            return user;
        }
        return null;
    }

    /**
     * Retrieves all users.
     * @return a list of all users
     * @throws SQLException if a database error occurs
     */
    public java.util.List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }
}