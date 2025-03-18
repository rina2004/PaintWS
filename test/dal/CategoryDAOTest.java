/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dal;

import context.DBContext;
import model.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryDAOTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private CategoryDAO categoryDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Create mocks
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Configure mock connection
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Create DAO instance
        categoryDAO = new CategoryDAO();

        // Use reflection to set protected connection field
        setConnectionField(categoryDAO, mockConnection);
    }

    // Helper method to use reflection to set the protected connection field
    private void setConnectionField(DBContext dao, Connection conn) throws Exception {
        Field connectionField = DBContext.class.getDeclaredField("connection");
        connectionField.setAccessible(true);
        connectionField.set(dao, conn);
    }

    //--------------------
    // create Tests
    //--------------------
    @Test
    public void testCreate_ValidCategory() throws SQLException {
        // Prepare test data
        Category category = new Category();
        category.setName("New Category");
        category.setDescribe("New category description");

        // Execute the method to test
        categoryDAO.create(category);

        // Verifications
        verify(mockPreparedStatement, times(1)).executeUpdate();
        if (mockResultSet.next()) {
            assertEquals(1, mockResultSet.getInt("CategoryID"));
            assertEquals("New Category", mockResultSet.getInt("CategoryName"));
            assertEquals("New category description", mockResultSet.getInt("Description"));
        }
    }

    @Test
    public void testCreate_WithNullModel() throws SQLException {
        // Prepare test data with null values
        Category category = null;

        NullPointerException e = assertThrows(NullPointerException.class, () -> categoryDAO.create(category));
        assertEquals("Cannot invoke \"model.Category.getName()\" because \"c\" is null", e.getMessage());
    }
    
    @Test
    public void testCreate_ExistedCategory() throws SQLException {
        // Prepare test data
        Category category = new Category();
        category.setName("New Category");
        category.setDescribe("New category description");

        // Execute the method to test
        categoryDAO.create(category);

        // Configure mocks
        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        
        SQLException ex = assertThrows(SQLException.class, () -> categoryDAO.create(category));
        assertEquals(null, ex.getMessage());
    }
    
    @Test
    public void testCreate_NameBoundary() throws SQLException {
        Category category = new Category();
        category.setName("A".repeat(100));
        category.setDescribe("New category description");
        // Execute the method to test
        categoryDAO.create(category);

        // Configure mocks
        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        
        SQLException ex = assertThrows(SQLException.class, () -> categoryDAO.create(category));
        assertEquals(null, ex.getMessage());
    }
    
    @Test
    public void testCreate_DescriptionBoundary() throws SQLException {
        Category category = new Category();
        category.setName("New Category");
        category.setDescribe("D".repeat(1000));
        // Execute the method to test
        categoryDAO.create(category);

        // Configure mocks
        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        
        SQLException ex = assertThrows(SQLException.class, () -> categoryDAO.create(category));
        assertEquals(null, ex.getMessage());
    }

    //--------------------
    // getAll Tests
    //--------------------
    @Test
    public void testGetAll_MultipleRecords() throws SQLException {
        // Configure mocks
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Simulate 2 records in the ResultSet
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("CategoryID")).thenReturn(1, 2);
        when(mockResultSet.getString("CategoryName")).thenReturn("Acrylic Paint", "Oil Paint");
        when(mockResultSet.getString("Description")).thenReturn("Acrylic description", "Oil description");

        // Execute the method to test
        List<Category> categories = categoryDAO.getAll();

        // Verifications
        assertEquals(2, categories.size());

        assertEquals(1, categories.get(0).getId());
        assertEquals("Acrylic Paint", categories.get(0).getName());
        assertEquals("Acrylic description", categories.get(0).getDescribe());
    }

    @Test
    public void testGetAll_EmptyResultSet() throws SQLException {
        // Configure mocks to return empty result set
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Execute the method to test
        List<Category> categories = categoryDAO.getAll();

        // Verifications
        assertTrue(categories.isEmpty());
    }

    //--------------------
    // getCategoryById Tests
    //--------------------
    @Test
    public void testGetCategoryById_FoundARecords() throws SQLException {
        // Configure mocks
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("CategoryID")).thenReturn(1);
        when(mockResultSet.getString("CategoryName")).thenReturn("Acrylic Paint");
        when(mockResultSet.getString("Description")).thenReturn("Acrylic description");

        // Execute the method to test
        Category category = categoryDAO.getCategoryById(1);

        // Verifications
        assertNotNull(category);
        assertEquals(1, category.getId());
        assertEquals("Acrylic Paint", category.getName());
        assertEquals("Acrylic description", category.getDescribe());
    }

    @Test
    public void testGetCategoryById_NotFoundAnyRecord() throws SQLException {
        // Configure mocks
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Execute the method to test
        Category category = categoryDAO.getCategoryById(1);

        // Verifications
        assertNull(category);
    }

    //--------------------
    // update Tests
    //--------------------
    @Test
    public void testUpdate_Found() throws SQLException {
        // Prepare test data
        Category temp = new Category();
        temp.setName("New Category");
        temp.setDescribe("New description");
        categoryDAO.create(temp);

        Category category = new Category();
        category.setId(1);
        category.setName("Updated Category");
        category.setDescribe("Updated description");

        // Execute the method to test
        categoryDAO.update(category);

        // Verifications
        verify(mockPreparedStatement, times(2)).executeUpdate();
        if (mockResultSet.next()) {
            assertEquals(1, mockResultSet.getInt("CategoryID"));
            assertEquals("Updated Category", mockResultSet.getInt("CategoryName"));
            assertEquals("Updated description", mockResultSet.getInt("Description"));
        }
    }

    @Test
    public void testUpdate_WithNonExistentId() throws SQLException {
        Category temp = new Category();
        temp.setName("New Category");
        temp.setDescribe("New description");

        categoryDAO.create(temp);

        // Prepare test data with ID that doesn't exist
        Category category = new Category();
        category.setId(999);
        category.setName("Updated Category");
        category.setDescribe("Updated description");

        // Execute the method to test
        categoryDAO.update(category);

        // Verifications
        verify(mockPreparedStatement, times(2)).executeUpdate();
        if (mockResultSet.next()) {
            assertEquals(1, mockResultSet.getInt("CategoryID"));
            assertEquals("New Category", mockResultSet.getInt("CategoryName"));
            assertEquals("New description", mockResultSet.getInt("Description"));
        }
    }

    //--------------------
    // delete Tests
    //--------------------
    @Test
    public void testDelete() throws SQLException {
        // Prepare test data
        Category temp = new Category();
        temp.setId(1);
        temp.setDeleted(false);
        categoryDAO.create(temp);

        Category category = new Category();
        category.setId(1);

        // Execute the method to test
        categoryDAO.delete(category);

        // Verifications
        verify(mockPreparedStatement, times(2)).executeUpdate();
        if (mockResultSet.next()) {
            assertEquals(true, mockResultSet.getBoolean("isDeleted"));
        }
    }

    @Test
    public void testDelete_WithNonExistentCategory() throws SQLException {
        // Prepare test data
        Category temp = new Category();
        temp.setId(1);
        temp.setDeleted(false);
        categoryDAO.create(temp);

        Category category = new Category();
        category.setId(2);

        // Execute the method to test
        categoryDAO.delete(category);

        // Verifications
        verify(mockPreparedStatement, times(2)).executeUpdate();
        if (mockResultSet.next()) {
            assertEquals(false, mockResultSet.getBoolean("isDeleted"));
        }
    }
}