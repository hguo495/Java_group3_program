package dataaccesslayer;

import entity.User;
import java.sql.SQLException;
import java.util.List;

/**
 * DAO interface for user operations.
 * @author Jinze Li
 */
public interface UserDAO {
    List<User> getAllUsers() throws SQLException;
    User getUserById(int userId) throws SQLException;
    User getUserByEmail(String email) throws SQLException;
    void addUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(int userId) throws SQLException;
}