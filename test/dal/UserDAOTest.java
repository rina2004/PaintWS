/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import model.User;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class UserDAOTest {

    private UserDAO userDAO; // Không để static
    private User testUser;

    @Before
    public void setUp() {
        userDAO = mock(UserDAO.class);
        testUser = new User("testUser", "testPass", "testAddress", "123456789", "test@example.com", 2, 0);

        // Giả lập cho phương thức `signUp()` nếu nó là void
        doNothing().when(userDAO).signUp(anyString(), anyString(), anyString(), anyString(), anyString());

        // Giả lập cho phương thức `checkExistingUser()`
        when(userDAO.checkExistingUser("testUser", "test@example.com", "123456789"))
                .thenReturn(testUser);
    }

    @Test
    public void testSignUp() {
        // Gọi phương thức signUp()
        userDAO.signUp(
                testUser.getUserName(),
                testUser.getPassword(),
                testUser.getEmail(),
                testUser.getPhone(),
                testUser.getAddress()
        );

        // Kiểm tra xem signUp có được gọi với đúng tham số hay không
        verify(userDAO, times(1)).signUp(
                testUser.getUserName(),
                testUser.getPassword(),
                testUser.getEmail(),
                testUser.getPhone(),
                testUser.getAddress()
        );

        // Kiểm tra người dùng tồn tại sau khi sign up
        User existingUser = userDAO.checkExistingUser("testUser", "test@example.com", "123456789");
        assertNotNull("User should exist after sign up", existingUser);
        assertEquals("Username should match", "testUser", existingUser.getUserName());
    }

    @Test
    public void testGetAllUsers() {
        List<User> mockUsers = Arrays.asList(
                testUser,
                new User("user2", "pass2", "addr2", "987654321", "user2@example.com", 1, 0)
        );
        when(userDAO.getAllUsers()).thenReturn(mockUsers);

        List<User> users = userDAO.getAllUsers();
        assertNotNull("User list should not be null", users);
        assertEquals("User list should have 2 users", 2, users.size());
    }

    @Test
    public void testUpdateUser() {
        when(userDAO.updateUser(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true);

        boolean updated = userDAO.updateUser("1", "updatedUser", "newPass", "newAddress", "987654321", "new@example.com", "2");
        assertEquals("User update should be successful", true, updated);
    }

    @Test
    public void testDeleteUser() {
        when(userDAO.deleteUser(anyInt())).thenReturn(true);

        boolean deleted = userDAO.deleteUser(1);
        assertEquals("User should be deleted successfully", true, deleted);
    }
}
