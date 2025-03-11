/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import org.junit.Test;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Cart;
import model.Item;
import model.Product;
import model.User;
import org.junit.*;
import org.mockito.*;

/**
 *
 * @author tungn
 */
public class OrderDAOTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private OrderDAO orderDAO;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        orderDAO = new OrderDAO();
        orderDAO.connection = mockConnection; 

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); 
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }

    @Test
    public void testAddOrder() throws SQLException {
        User mockUser = new User("TestUser", "pass123", "123 Street", "0123456789", "test@example.com", 2, 1);
        Cart mockCart = new Cart();

        Product mockProduct = new Product(1, "Laptop", 100, "Black", 1000.0, 10, 0, false, "laptop.jpg", "Desc", 5.0, true, null, null);
        Item mockItem = new Item(mockProduct, 2);
        mockCart.addItem(mockItem);

        // Mock ResultSet trả về OrderID giả lập
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1001);

        // Mock executeUpdate()
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Gọi hàm cần test
        orderDAO.addOrder(mockUser, mockCart);

        // Kiểm tra xem executeUpdate() có được gọi không
        verify(mockPreparedStatement, atLeastOnce()).executeUpdate();
    }

}
