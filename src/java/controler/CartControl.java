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
import model.Cart;
import model.Item;
import model.Product;

/**
 *
 * @author anhbu
 */
@WebServlet(name = "CartControl", urlPatterns = {"/cart"})
public class CartControl extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CartControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //processRequest(request, response);
        HttpSession session = request.getSession(true);
        Cart cart;
        Object o = session.getAttribute("cart");
        String tnum = request.getParameter("num");
        String tid = request.getParameter("pid");
        ProductDAO dao = new ProductDAO();

        // Kiểm tra nếu giỏ hàng chưa tồn tại, tạo mới giỏ hàng
        if (o != null) {
            cart = (Cart) o;
        } else {
            cart = new Cart();
        }

        // Kiểm tra nếu không có pid hoặc num thì chuyển hướng lại trang giỏ hàng
        if (tid == null || tnum == null) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        int num = Integer.parseInt(tnum);
        int id = Integer.parseInt(tid);
        Product p = dao.getProductByID(tid);

        // Nếu sản phẩm tồn tại trong database
        if (p != null) {
            // Thêm sản phẩm hoặc tăng số lượng nếu num > 0
            if (num > 0) {
                cart.addItem(new Item(p, num));
            } else if (num == -1) { // Giảm số lượng khi num == -1
                cart.decreaseItem(id);
                // Nếu số lượng sản phẩm <= 0, xóa khỏi giỏ hàng
                if (cart.getQuantityByID(id) <= 0) {
                    cart.removeItem(id);
                }
            } else if (num == 0) { // Xóa sản phẩm khi num == 0
                cart.removeItem(id);
            }
        }

        // Cập nhật giỏ hàng vào session
        session.setAttribute("cart", cart);
        session.setAttribute("size", cart.getListItems().size()); // Cập nhật số lượng items

        // Chuyển hướng về trang giỏ hàng
        response.sendRedirect("Cart.jsp");
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
//        processRequest(request, response);
//        HttpSession session = request.getSession(true);
//        Cart cart;
//        Object o = session.getAttribute("cart");
//
//        // Kiểm tra nếu giỏ hàng chưa tồn tại, tạo mới giỏ hàng
//        if (o != null) {
//            cart = (Cart) o;
//        } else {
//            cart = new Cart();
//        }
//        ProductDAO dao = new ProductDAO();
//
//        // Lấy ID sản phẩm và xóa sản phẩm khỏi giỏ
//        int id = Integer.parseInt(request.getParameter("pid"));
//        cart.removeItem(id);
//
//        // Cập nhật lại giỏ hàng vào session
//        session.setAttribute("cart", cart);
//        session.setAttribute("size", cart.getListItems().size());  // Cập nhật lại số lượng items
//
//        request.getRequestDispatcher("Cart.jsp").forward(request, response);
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
