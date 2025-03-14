/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import model.Supplier;
import org.junit.*;
import org.mockito.*;

/**
 *
 * @author tungn
 */
public class SupplierDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement stm;

    @Mock
    private ResultSet rs;

    SupplierDAO dao;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        dao = new SupplierDAO();
        dao.connection = connection;
    }

    @Test
    public void testGetAll() throws SQLException {
        String sql = "SELECT * FROM Suppliers";

        when(connection.prepareStatement(sql)).thenReturn(stm);
        when(stm.executeQuery()).thenReturn(rs);

        AtomicInteger count = new AtomicInteger(0);
        when(rs.next()).thenAnswer(invocation -> count.getAndIncrement() < 2);
        when(rs.getInt("SupplierID")).thenReturn(1, 2);
        when(rs.getString("CompanyName")).thenReturn("Paint A", "Paint B");
        when(rs.getString("ContactName")).thenReturn("Joe", "John");
        when(rs.getString("Country")).thenReturn("UK", "USAA");
        when(rs.getString("Phone")).thenReturn("12345678", "012345679");

        List<Supplier> sup = dao.getAll();
        assertNotNull(sup);
        assertEquals(2, sup.size());

        assertEquals(1, sup.get(0).getId());
        assertEquals(2, sup.get(1).getId());

        assertEquals("Paint A", sup.get(0).getCompanyName());
        assertEquals("Paint B", sup.get(1).getCompanyName());

        assertEquals("Joe", sup.get(0).getContactName());
        assertEquals("John", sup.get(1).getContactName());

    }

    @Test
    public void testGetAll_EmptyResultSet() throws SQLException {
        String sql = "SELECT * FROM Suppliers";

        when(connection.prepareStatement(sql)).thenReturn(stm);
        when(stm.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(false);

        List<Supplier> sup = dao.getAll();
        assertNotNull(sup);
        assertEquals(0, sup.size());
    }

    @Test
    public void testGetSupplierById() throws SQLException {
        String sql = "SELECT * FROM [dbo].[Suppliers] WHERE SupplierID = ?";

        when(connection.prepareStatement(sql)).thenReturn(stm);
        when(stm.executeQuery()).thenReturn(rs);

        // Mô phỏng kết quả cho từng lần gọi rs.next()
        when(rs.next()).thenReturn(true, true, false, false);

        // Mô phỏng dữ liệu cho 2 supplier hợp lệ
        when(rs.getInt("SupplierID")).thenReturn(1, 2);
        when(rs.getString("CompanyName")).thenReturn("FPT", "Google");
        when(rs.getString("ContactName")).thenReturn("KhanhNG", "JohnDoe");
        when(rs.getString("Country")).thenReturn("Japan", "USA");
        when(rs.getString("Phone")).thenReturn("0123456789", "0987654321");

        // Gọi phương thức cần test
        Supplier sp1 = dao.getSupplierById(1);
        Supplier sp2 = dao.getSupplierById(2);
        Supplier sp3 = dao.getSupplierById(999); // Không tồn tại
        Supplier sp4 = dao.getSupplierById(-1);  // ID không hợp lệ

        // Kiểm tra dữ liệu của Supplier 1
        assertNotNull(sp1);
        assertEquals(1, sp1.getId());
        assertEquals("FPT", sp1.getCompanyName());
        assertEquals("KhanhNG", sp1.getContactName());
        assertEquals("Japan", sp1.getCountry());
        assertEquals("0123456789", sp1.getPhone());

        // Kiểm tra dữ liệu của Supplier 2
        assertNotNull(sp2);
        assertEquals(2, sp2.getId());
        assertEquals("Google", sp2.getCompanyName());
        assertEquals("JohnDoe", sp2.getContactName());
        assertEquals("USA", sp2.getCountry());
        assertEquals("0987654321", sp2.getPhone());

        assertNull(sp3);
        assertNull(sp4);
    }

}
