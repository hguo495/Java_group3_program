package dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import transferobjects.CredentialsDTO;

/**
 * Singleton class to manage the database connection.
 * @author Jinze Li
 */
public class DataSource {
    private static DataSource instance;
    private Connection connection;
    private CredentialsDTO creds;

    private DataSource(CredentialsDTO creds) throws SQLException {
        this.creds = creds;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
        Properties props = loadProperties();
        String url = props.getProperty("jdbc.url");
        this.connection = DriverManager.getConnection(url, creds.getUsername(), creds.getPassword());
    }

    /**
     * Gets the singleton instance of the DataSource.
     * @param creds the credentials to connect to the database
     * @return the DataSource instance
     * @throws SQLException if a database error occurs
     */
    public static synchronized DataSource getInstance(CredentialsDTO creds) throws SQLException {
        if (instance == null) {
            instance = new DataSource(creds);
        }
        return instance;
    }

    /**
     * Gets the database connection.
     * @return the database connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Loads the database properties from database.properties.
     * @return the loaded properties
     * @throws SQLException if the properties file cannot be loaded
     */
    private Properties loadProperties() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new SQLException("Unable to find database.properties in resources");
            }
            props.load(input);
        } catch (IOException ex) {
            throw new SQLException("Error loading database.properties: " + ex.getMessage());
        }
        return props;
    }
}