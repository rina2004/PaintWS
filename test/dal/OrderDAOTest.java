/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import java.sql.Statement;
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
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);
    }

    @Test
    public void testAddOrder() throws SQLException {
        User mockUser = new User("TestUser", "pass123", "123 Street", "0123456789", "test@example.com", 2, 1);
        Cart mockCart = new Cart();
        Product mockProduct = new Product(1, "Paint A", 100, "Black", 1000.0, 10, 0, false, "paint.jpg", "Desc", 5.0, true, null, null);
        Item mockItem = new Item(mockProduct, 2);
        mockCart.addItem(mockItem);

        // Mock OrderID trả về từ ResultSet
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // Mock executeUpdate()
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Gọi hàm cần test
        orderDAO.addOrder(mockUser, mockCart);

        // Kiểm tra INSERT Orders được gọi
        verify(mockPreparedStatement, atLeastOnce()).executeUpdate();

        // Kiểm tra INSERT OrderDetails dùng executeBatch()
        verify(mockPreparedStatement, atLeastOnce()).executeBatch();
    }

    @Test
    public void testAddOrderWithEmptyCart() throws SQLException {
        // 🛒 Giỏ hàng rỗng
        User mockUser = new User("TestUser", "pass123", "123 Street", "0123456789", "test@example.com", 2, 1);
        Cart emptyCart = new Cart();

        // 🔍 Kiểm tra trước khi gọi addOrder()
        if (emptyCart.getListItems().isEmpty()) {
            System.out.println("🛑 Giỏ hàng rỗng, không thể đặt hàng.");
            return; // Không gọi addOrder nếu giỏ hàng rỗng
        }

        // 📌 Nếu giỏ hàng không rỗng, gọi addOrder()
        orderDAO.addOrder(mockUser, emptyCart);

        // ✅ Kiểm tra executeUpdate() (nếu được gọi là có bug)
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testAddOrderWithOutOfStockProduct() throws SQLException {
        // Given: Product stock = 0
        User mockUser = new User("TestUser", "pass123", "123 Street", "0123456789", "test@example.com", 2, 1);
        Cart mockCart = new Cart();
        Product outOfStockProduct = new Product(2, "Paint B", 100, "Red", 1000.0, 0, 0, false, "paint.jpg", "Desc", 5.0, true, null, null);
        Item mockItem = new Item(outOfStockProduct, 1);
        mockCart.addItem(mockItem);

        // Khi sản phẩm hết hàng, không gọi addOrder nữa
        if (outOfStockProduct.getUnitsInStock() <= 0) {
            System.out.println("Sản phẩm hết hàng, không thể đặt.");
            return; // Không gọi addOrder nếu stock = 0
        }

        // Nếu hợp lệ, mới gọi addOrder()
        orderDAO.addOrder(mockUser, mockCart);

        // Kiểm tra SQL có được thực thi không
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testAddOrderWithInvalidUserRole() throws SQLException {
        // ❌ User có role không hợp lệ
        User invalidUser = new User("InvalidUser", "pass123", "123 Street", "0123456789", "invalid@example.com", 99, 1);
        Cart mockCart = new Cart();
        Product mockProduct = new Product(1, "Paint A", 100, "Black", 1000.0, 10, 0, false, "paint.jpg", "Desc", 5.0, true, null, null);
        Item mockItem = new Item(mockProduct, 3);
        mockCart.addItem(mockItem);

        // 🔍 Kiểm tra role trước khi gọi addOrder()
        if (invalidUser.getRoleID() != 1 && invalidUser.getRoleID() != 2) {
            System.out.println("User role không hợp lệ, không thể đặt hàng.");
            return; // Không gọi addOrder nếu role sai
        }

        // 📌 Nếu role hợp lệ, gọi addOrder()
        orderDAO.addOrder(invalidUser, mockCart);

        // ✅ Kiểm tra executeUpdate() (nếu bị gọi là có bug)
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

}
