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
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Cart;
import model.Item;
import model.Product;

/**
 *
 * @author anhbu
 */
@WebServlet(name = "BuyControl", urlPatterns = {"/buy"})
public class BuyControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        ProductDAO dao = new ProductDAO();
        String tnum = request.getParameter("num");
        String id = request.getParameter("pid");

        Product p = dao.getProductByID(id); // Lấy Product từ DAO
        int num = Integer.parseInt(tnum);

        // Kiểm tra số lượng sản phẩm có trong kho
        if (p.getUnitsInStock() < num) {
            // Nếu số lượng sản phẩm yêu cầu lớn hơn số lượng trong kho
            session.setAttribute("errorMessage", "Sản phẩm không đủ số lượng trong kho.");
            String redirectURL = request.getParameter("redirectURL");
            if (redirectURL != null && !redirectURL.isEmpty()) {
                request.getRequestDispatcher(redirectURL).forward(request, response);
            } else {
                response.sendRedirect("home");
            }
            return; // Dừng lại và không thêm sản phẩm vào giỏ hàng
        }

        // Tạo đối tượng Item mới với Product và số lượng
        Item t = new Item(p, num);

        // Thêm sản phẩm vào giỏ hàng
        cart.addItem(t);

        // Cập nhật lại giỏ hàng trong session
        session.setAttribute("cart", cart);
        session.setAttribute("size", cart.getListItems().size());


        // Thêm thông báo thành công vào session
        session.setAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng thành công!");

        // Lấy URL chuyển hướng
        String redirectURL = request.getParameter("redirectURL");
        if (redirectURL != null && !redirectURL.isEmpty()) {
            request.getRequestDispatcher(redirectURL).forward(request, response);
        } else {
            response.sendRedirect("home");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
