/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controler;

import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author anhbu
 */
@WebServlet(name = "AddControl", urlPatterns = {"/add"})
public class AddControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String stock = request.getParameter("unitsInStock");
        String sold = request.getParameter("quantitySold");
        String volume = request.getParameter("volume");
        String color = request.getParameter("color");
        String supplier = request.getParameter("supplierID");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String status = request.getParameter("status");

        ProductDAO dao = new ProductDAO();
        // Gọi phương thức thêm sản phẩm và lưu kết quả
        dao.insert(name, image, price, stock, sold, volume, color, supplier, description, category, status);
        response.sendRedirect("manager");
    }
}
