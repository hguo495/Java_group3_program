/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * This class tests the user authentication logic.
 * It verifies that valid credentials return the correct user object.
 * 
 * Author: Jinze Li
 */

import businesslayer.UserBusinessLogic;
import entity.User;
import org.junit.jupiter.api.Test;
import transferobjects.CredentialsDTO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class UserBusinessLogicTest {

    @Test
    public void testAuthenticateUser_ValidCredentials_ReturnsUser() {
        try {
            // Set up database credentials
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("cst8288");
            creds.setPassword("cst8288");

            // Create business logic instance
            UserBusinessLogic logic = new UserBusinessLogic(creds);

            // Call authentication method
            User user = logic.authenticateUser("john.doe@transit.com", "pass123");

            // Assert the user details are correct
            assertNotNull(user, "User should not be null");
            assertEquals(1, user.getUserId());
            assertEquals("Manager", user.getType());

        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }
}
