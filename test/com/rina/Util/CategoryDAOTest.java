/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.rina.Util;

import dal.CategoryDAO;
import model.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private CategoryDAO categoryDAO;

    @Before
    public void setUp() throws Exception {
        categoryDAO = new CategoryDAO();
        categoryDAO.connection = mockConnection;
    }

    @Test
    public void testGetAll() throws Exception {

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("CategoryID")).thenReturn(1, 2);
        when(mockResultSet.getString("CategoryName")).thenReturn("Electronics", "Clothing");
        when(mockResultSet.getString("Description")).thenReturn("Electronic Items", "Fashion Wear");

        List<Category> categories = categoryDAO.getAll();
        
        assertEquals(2, categories.size());

        assertEquals(1, categories.get(0).getId());
        assertEquals("Electronics", categories.get(0).getName());
        assertEquals("Electronic Items", categories.get(0).getDescribe());

        verify(mockPreparedStatement, times(1)).executeQuery();
    }
}