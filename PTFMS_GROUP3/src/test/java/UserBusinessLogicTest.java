/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 86139
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
            // 连接数据库
            CredentialsDTO creds = new CredentialsDTO();
            creds.setUsername("root");
            creds.setPassword("1234");

            // 创建业务逻辑对象
            UserBusinessLogic logic = new UserBusinessLogic(creds);

            // 调用登录方法
            User user = logic.authenticateUser("john.doe@transit.com", "pass123");

            // 验证返回结果
            assertNotNull(user, "User should not be null");
            assertEquals(1, user.getUserId());
            assertEquals("Manager", user.getType());

        } catch (SQLException e) {
            fail("SQLException occurred: " + e.getMessage());
        }
    }
}

