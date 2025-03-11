/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.rina.dao.test.core;

import context.DBContext;
import dal.CategoryDAO;
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
    // getAll Tests
    //--------------------
    @Test
    public void testGetAll_ExistedRecords() throws SQLException {
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
        assertNotNull(categories);
        assertEquals(2, categories.size());

        assertEquals(1, categories.get(0).getId());
        assertEquals("Acrylic Paint", categories.get(0).getName());
        assertEquals("Acrylic description", categories.get(0).getDescribe());

        assertEquals(2, categories.get(1).getId());
        assertEquals("Oil Paint", categories.get(1).getName());
        assertEquals("Oil description", categories.get(1).getDescribe());
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

    @Test
    public void testGetAll_NullValues() throws SQLException {
        // Configure mocks
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Simulate record with null values in the ResultSet
        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getInt("CategoryID")).thenReturn(1);
        when(mockResultSet.getString("CategoryName")).thenReturn(null);
        when(mockResultSet.getString("Description")).thenReturn(null);

        // Execute the method to test
        List<Category> categories = categoryDAO.getAll();

        // Verifications
        assertNotNull(categories);
        assertEquals(1, categories.size());

        assertEquals(1, categories.get(0).getId());
        assertNull(categories.get(0).getName());
        assertNull(categories.get(0).getDescribe());
    }

    //--------------------
    // getCategoryById Tests
    //--------------------
    @Test
    public void testGetCategoryById_Found() throws SQLException {
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
    public void testGetCategoryById_NotFound() throws SQLException {
        // Configure mocks
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Execute the method to test
        Category category = categoryDAO.getCategoryById(999);

        // Verifications
        assertNull(category);
    }

    //--------------------
    // create Tests
    //--------------------
    @Test
    public void testCreate_NormalCase() throws SQLException {
        // Prepare test data
        Category category = new Category();
        category.setName("New Category");
        category.setDescribe("New category description");

        // Execute the method to test
        categoryDAO.create(category);

        // Verifications
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(1, "New Category");
        verify(mockPreparedStatement).setString(2, "New category description");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testCreate_WithNullValues() throws SQLException {
        // Prepare test data with null values
        Category category = new Category();
        category.setName(null);
        category.setDescribe(null);

        // Execute the method to test
        categoryDAO.create(category);

        // Verifications
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(1, null);
        verify(mockPreparedStatement).setString(2, null);
        verify(mockPreparedStatement).executeUpdate();
    }

    //--------------------
    // update Tests
    //--------------------
    @Test
    public void testUpdate_Found() throws SQLException {
        // Prepare test data
        Category category = new Category();
        category.setId(1);
        category.setName("Updated Category");
        category.setDescribe("Updated description");

        // Execute the method to test
        categoryDAO.update(category);

        // Verifications
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(1, "Updated Category");
        verify(mockPreparedStatement).setString(2, "Updated description");
        verify(mockPreparedStatement).setInt(3, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdate_WithNullValues() throws SQLException {
        // Prepare test data with null values
        Category category = new Category();
        category.setId(1);
        category.setName(null);
        category.setDescribe(null);

        // Execute the method to test
        categoryDAO.update(category);

        // Verifications
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(1, null);
        verify(mockPreparedStatement).setString(2, null);
        verify(mockPreparedStatement).setInt(3, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testUpdate_WithNonExistentId() throws SQLException {
        // Configure mock to simulate no rows affected
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        // Prepare test data with ID that doesn't exist
        Category category = new Category();
        category.setId(999);
        category.setName("Updated Category");
        category.setDescribe("Updated description");

        // Execute the method to test
        categoryDAO.update(category);

        // Verifications
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setString(1, "Updated Category");
        verify(mockPreparedStatement).setString(2, "Updated description");
        verify(mockPreparedStatement).setInt(3, 999);
        verify(mockPreparedStatement).executeUpdate();
    }

    //--------------------
    // delete Tests
    //--------------------
    @Test
    public void testDelete() throws SQLException {
        // Prepare test data
        Category category = new Category();
        category.setId(1);

        // Execute the method to test
        categoryDAO.delete(category);

        // Verifications
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 1);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testDelete_WithNonExistentCategory() throws SQLException {
        // Configure mock to simulate no rows affected
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        // Prepare test data
        Category category = new Category();
        category.setId(999); // ID that doesn't exist

        // Execute the method to test
        categoryDAO.delete(category);

        // Verifications
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).setInt(1, 999);
        verify(mockPreparedStatement).executeUpdate();
    }
}
