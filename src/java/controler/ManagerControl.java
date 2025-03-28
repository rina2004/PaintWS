/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controler;
import dal.*;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.List;
import model.*;
/**
 *
 * @author lytu
 */
@WebServlet(name = "ManagerControl", urlPatterns = {"/manager"})
public class ManagerControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ProductDAO dao = new ProductDAO();
        List<Product> list = dao.getAll();
        CategoryDAO daoC = new CategoryDAO();
        List<Category> listC = daoC.getAll();
        SupplierDAO daoS = new SupplierDAO();
        List<Supplier> listS = daoS.getAll();

        request.setAttribute("listP", list);
        request.setAttribute("listC", listC);
        request.setAttribute("listS", listS);
        request.getRequestDispatcher("ManagerProduct.jsp").forward(request, response);
    }
}
