/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controler;

import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author anhbu
 */
@WebServlet(name = "AddCategoryControl", urlPatterns = {"/addCategory"})
public class AddCategoryControl extends HttpServlet {

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
            out.println("<title>Servlet AddCategoryControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCategoryControl at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
        String name = request.getParameter("name");
        String description = request.getParameter("describe");

        CategoryDAO dao = new CategoryDAO();
        HttpSession session = request.getSession();

        // Kiểm tra rỗng
        if (name == null || name.trim().isEmpty()) {
            session.setAttribute("errorC", "Category name cannot be empty");
            response.sendRedirect("loadCategory");
            return;
        }

        // Kiểm tra độ dài
        if (name.length() > 50) {
            session.setAttribute("errorC", "Category names cannot exceed 50 characters");
            response.sendRedirect("loadCategory");
            return;
        }

        // Kiểm tra trùng
        if (dao.isCategoryExist(name)) {
            session.setAttribute("errorC", "Category names cannot be duplicated.");
            response.sendRedirect("loadCategory");
            return;
        }

        // Nếu không có lỗi
        dao.addCategory(name, description);
        session.setAttribute("successC", "Category added successfully!");
        response.sendRedirect("loadCategory");
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
