/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.rina.Util;

import dal.ProductDAO;
import dal.CategoryDAO;
import dal.SupplierDAO;
import model.Product;
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
public class ProductDAOTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private CategoryDAO mockCategoryDAO;

    @Mock
    private SupplierDAO mockSupplierDAO;

    @InjectMocks
    private ProductDAO productDAO;
    
    @Before
    public void setup() throws Exception {
        productDAO = new ProductDAO();
        productDAO.connection = mockConnection;
        productDAO.cd = mockCategoryDAO;
        productDAO.sd = mockSupplierDAO;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }
    
    @Test
    public void testGetAllProducts_WithData() throws Exception {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false); 
        
        when(mockResultSet.getInt("ProductID")).thenReturn(1, 2);
        when(mockResultSet.getString("ProductName")).thenReturn("Paint A", "Paint B");
        when(mockResultSet.getDouble("Volume")).thenReturn(1.5, 2.0);
        when(mockResultSet.getString("Color")).thenReturn("Red", "Blue");
        when(mockResultSet.getDouble("UnitPrice")).thenReturn(10.5, 12.0);
        when(mockResultSet.getInt("UnitsInStock")).thenReturn(100, 50);
        when(mockResultSet.getInt("QuantitySold")).thenReturn(30, 20);
        when(mockResultSet.getBoolean("Discontinued")).thenReturn(false, true);
        when(mockResultSet.getString("Image")).thenReturn("imageA.jpg", "imageB.jpg");
        when(mockResultSet.getString("Description")).thenReturn("Good quality", "Premium quality");
        when(mockResultSet.getDouble("Discount")).thenReturn(0.1, 0.2);
        when(mockResultSet.getBoolean("Status")).thenReturn(true, false);
        lenient().when(mockResultSet.getInt("CATEGORY_ID")).thenReturn(1, 2);
        lenient().when(mockResultSet.getInt("SUPPLIER_ID")).thenReturn(1, 2);

        List<Product> products = productDAO.getAll();

        assertNotNull(products);
        assertEquals(2, products.size());

        assertEquals("Paint A", products.get(0).getProductName());
        assertEquals("Paint B", products.get(1).getProductName());
    }
}