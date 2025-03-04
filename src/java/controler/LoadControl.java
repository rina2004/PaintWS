/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controler;

import dal.CategoryDAO;
import dal.ProductDAO;
import dal.SupplierDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;
import model.Supplier;

/**
 *
 * @author anhbu
 */
@WebServlet(name = "LoadControl", urlPatterns = {"/loadProduct"})
public class LoadControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("pid");
        ProductDAO dao = new ProductDAO();
        Product p = dao.getProductByID(id);

        CategoryDAO daoC = new CategoryDAO();
        List<Category> listC = daoC.getAll();

        SupplierDAO daoS = new SupplierDAO();
        List<Supplier> listS = daoS.getAll();

        request.setAttribute("detail", p);
        request.setAttribute("listC", listC);
        request.setAttribute("listS", listS);
        request.getRequestDispatcher("Edit.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
