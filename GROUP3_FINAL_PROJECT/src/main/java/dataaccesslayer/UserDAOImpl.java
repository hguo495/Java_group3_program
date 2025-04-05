package dataaccesslayer;

import entity.User;
import transferobjects.CredentialsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for user operations.
 * @author Jinze Li
 */
public class UserDAOImpl implements UserDAO {
    private final Connection connection;

    /**
     * Constructor that initializes the database connection.
     * @param creds the credentials to connect to the database
     * @throws SQLException if a database error occurs
     */
    public UserDAOImpl(CredentialsDTO creds) throws SQLException {
        DataSource dataSource = DataSource.getInstance(creds);
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setType(rs.getString("type"));
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public User getUserById(int userId) throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setType(rs.getString("type"));
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setType(rs.getString("type"));
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (name, email, password, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getType());
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET name = ?, email = ?, password = ?, type = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getType());
            stmt.setInt(5, user.getUserId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("User with ID " + user.getUserId() + " not found.");
            }
        }
    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("User with ID " + userId + " not found.");
            }
        }
    }
}