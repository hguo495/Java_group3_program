package transferobjects;

/**
 * DTO for transferring database login credentials between layers.
 * @author Hongchen Guo
 */
public class CredentialsDTO {
    private String username;
    private String password;

    // 新增：带参数构造函数
    public CredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 保留默认构造函数
    public CredentialsDTO() {
    }

    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
