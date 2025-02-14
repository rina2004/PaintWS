/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controler;

import dal.OrderDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import model.Cart;
import model.Item;
import model.Product;
import model.User;

/**
 *
 * @author anhbu
 */
@WebServlet(name = "CheckOutControl", urlPatterns = {"/check"})
public class CheckOutControl extends HttpServlet {

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
            out.println("<title>Servlet CheckOutControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckOutControl at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        User account = (User) session.getAttribute("acc");

        // Kiểm tra xem cart có null không
        if (cart == null) {
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
            return; // Kết thúc phương thức
        }

        // Kiểm tra số lượng trong kho trước khi thêm đơn hàng
        for (Item item : cart.getListItems()) {
            Product product = item.getProduct();
            int requestedQuantity = item.getQuantity();
            if (product.getUnitsInStock() < requestedQuantity) {
                session.setAttribute("errorMessage", "Sản phẩm " + product.getProductName() + " không đủ số lượng trong kho.");
                response.sendRedirect("Cart.jsp");
                return; // Ngừng thực hiện nếu không đủ hàng
            }
        }

        // Thêm đơn hàng vào cơ sở dữ liệu
        OrderDAO orderDAO = new OrderDAO();
        orderDAO.addOrder(account, cart);

        // Xóa giỏ hàng sau khi thanh toán
        session.removeAttribute("cart");
        session.setAttribute("size", 0);

        // Thiết lập thông tin hóa đơn để hiển thị trong Bill.jsp
        request.setAttribute("currentDate", java.sql.Date.valueOf(LocalDate.now()));
        request.setAttribute("user", account);
        request.setAttribute("cart", cart);
        request.setAttribute("totalAmount", cart.getTotalPrice());

        // Chuyển tiếp tới trang Bill.jsp để hiển thị thông tin hóa đơn
        request.getRequestDispatcher("Bill.jsp").forward(request, response);
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
