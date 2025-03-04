/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controler;

import dal.ChartDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import model.*;

/**
 * @author lytu
 */

@WebServlet(name = "ChartControl", urlPatterns = {"/Chart"})
public class ChartControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ChartDAO dao = new ChartDAO();
        
        // Get parameter for top products limit (default to 10 if not specified)
        int topLimit = 10;
        String limitParam = request.getParameter("limit");
        if (limitParam != null && !limitParam.isEmpty()) {
            try {
                topLimit = Integer.parseInt(limitParam);
            } catch (NumberFormatException e) {
                // Keep default if parameter is invalid
            }
        }
        
        // Get comparison months (default to 1 and 2 if not specified)
        int month1 = 1;
        int month2 = 2;
        String month1Param = request.getParameter("month1");
        String month2Param = request.getParameter("month2");
        
        if (month1Param != null && !month1Param.isEmpty()) {
            try {
                month1 = Integer.parseInt(month1Param);
            } catch (NumberFormatException e) {
                // Keep default if parameter is invalid
            }
        }
        
        if (month2Param != null && !month2Param.isEmpty()) {
            try {
                month2 = Integer.parseInt(month2Param);
            } catch (NumberFormatException e) {
                // Keep default if parameter is invalid
            }
        }
        
        // Get data for all charts
        List<BestSellingProduct> bestSellers = dao.getBestSellingProducts();
        List<BestSellingProduct> topSellers = dao.getTopSellingProducts(topLimit);
        List<CategorySales> categorySales = dao.getSalesByCategory();
//        List<MonthlySales> monthlySales = dao.getMonthlySales();
        Map<String, Object> comparison = dao.compareMonthlyRevenue(month1, month2);
        
        // Set attributes for JSP
        request.setAttribute("bestSellers", bestSellers);
        request.setAttribute("topSellers", topSellers);
        request.setAttribute("categorySales", categorySales);
//        request.setAttribute("monthlySales", monthlySales);
        request.setAttribute("comparison", comparison);
        
        // Forward to JSP
        request.getRequestDispatcher("Chart.jsp").forward(request, response);
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
    
    // For AJAX requests to get specific data
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        ChartDAO dao = new ChartDAO();
        String action = request.getParameter("action");
        
        if ("compareMonths".equals(action)) {
            try {
                int month1 = Integer.parseInt(request.getParameter("month1"));
                int month2 = Integer.parseInt(request.getParameter("month2"));
                Map<String, Object> comparison = dao.compareMonthlyRevenue(month1, month2);
                
                // Convert to JSON and write to response
                String jsonResponse = "{\"month1\":" + comparison.get("month1") + 
                        ",\"month2\":" + comparison.get("month2") + 
                        ",\"revenue1\":" + comparison.get("revenue1") + 
                        ",\"revenue2\":" + comparison.get("revenue2") + 
                        ",\"percentageChange\":" + comparison.get("percentageChange") + "}";
                
                response.getWriter().write(jsonResponse);
            } catch (Exception e) {
                response.getWriter().write("{\"error\":\"Invalid parameters\"}");
            }
        } else {
            response.getWriter().write("{\"error\":\"Unknown action\"}");
        }
    }
}