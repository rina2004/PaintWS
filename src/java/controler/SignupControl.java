/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controler;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author anhbu
 */
@WebServlet(name = "SignupControl", urlPatterns = {"/signup"})
public class SignupControl extends HttpServlet {

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
            out.println("<title>Servlet SignupControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignupControl at " + request.getContextPath() + "</h1>");
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
        // Lấy dữ liệu từ form đăng ký
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String repass = request.getParameter("repass");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Kiểm tra mật khẩu có khớp không
        if (!password.equals(repass)) {
            request.setAttribute("mess", "Mật khẩu không khớp! Vui lòng thử lại !!!");
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
        } else {
            // Kiểm tra xem username, email hoặc phone có bị trùng không
            UserDAO dao = new UserDAO();
            User existingUser = dao.checkExistingUser(username, email, phone);

            if (existingUser == null) {
                // Đăng ký người dùng mới nếu không bị trùng
                dao.signUp(username, password, email, phone, address);
                response.sendRedirect("Login.jsp"); // Chuyển đến trang chủ nếu thành công
            } else {
                // Nếu có thông tin bị trùng, hiển thị thông báo lỗi
                request.setAttribute("mess", "Tên đăng nhập, email hoặc số điện thoại đã tồn tại! Vui lòng thử lại.");
                request.getRequestDispatcher("Signup.jsp").forward(request, response);
            }
        }

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
