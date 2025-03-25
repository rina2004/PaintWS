package controler;

import dal.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 *
 * @author lytu
 */
@WebServlet(name = "AddControl", urlPatterns = {"/add"})
public class AddControl extends HttpServlet {

    private static final Pattern LETTERS_ONLY = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$"
    );

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

        // Product Name Validation
        if (name.trim().isEmpty()) {
            request.getSession().setAttribute("message", "Product name cannot be empty!");
            response.sendRedirect("manager");
            return;
        }
        if (!name.contains("Sơn")) {
            request.getSession().setAttribute("message", "Product name must contain the word 'Sơn'!");
            response.sendRedirect("manager");
            return;
        }
        if (name.length() < 10) {
            request.getSession().setAttribute("message", "Product name must be at least 10 characters long!");
            response.sendRedirect("manager");
            return;
        }
        if (!name.matches("[\\p{L} ]+")) {
            request.getSession().setAttribute("message", "Product name can only contain letters.");
            response.sendRedirect("manager");
            return;
        }
        if (name.length() > 100) {
            request.getSession().setAttribute("message", "Product name cannot exceed 100 characters!");
            response.sendRedirect("manager");
            return;
        }

        // Image Validation
        if (image == null || image.trim().isEmpty()) {
            request.getSession().setAttribute("message", "Image link cannot be empty.");
            response.sendRedirect("manager");
            return;
        }
        if (!URL_PATTERN.matcher(image).matches()) {
            request.getSession().setAttribute("message", "Image link must be a valid URL.");
            response.sendRedirect("manager");
            return;
        }

        // Price Validation
        if (price == null || price.trim().isEmpty()) {
            request.getSession().setAttribute("message", "Unit price cannot be empty.");
            response.sendRedirect("manager");
            return;
        }
        double priceValue = Double.parseDouble(price);

        if (priceValue <= 0) {
            request.getSession().setAttribute("message", "Unit price must be greater than zero.");
            response.sendRedirect("manager");
            return;
        }
        if (priceValue > 100_000_000) {
            request.getSession().setAttribute("message", "Unit Price cannot exceed 100,000,000.");
            response.sendRedirect("manager");
            return;
        }

        // Stock Validation
        if (stock == null || stock.trim().isEmpty()) {
            request.getSession().setAttribute("message", "Units in stock cannot be empty.");
            response.sendRedirect("manager");
            return;
        }
        int stockValue = Integer.parseInt(stock);

        if (stockValue < 0) {
            request.getSession().setAttribute("message", "Units in stock cannot be negative.");
            response.sendRedirect("manager");
            return;
        }
        if (stockValue > 1_000) {
            request.getSession().setAttribute("message", "Units In Stock cannot exceed 1,000.");
            response.sendRedirect("manager");
            return;
        }

        // Sold Validation
        if (sold == null || sold.trim().isEmpty()) {
            request.getSession().setAttribute("message", "Quantity sold cannot be empty.");
            response.sendRedirect("manager");
            return;
        }
        int soldValue = Integer.parseInt(sold);

        if (soldValue < 0) {
            request.getSession().setAttribute("message", "Quantity sold cannot be negative.");
            response.sendRedirect("manager");
            return;
        }
        if (soldValue > 1_000) {
            request.getSession().setAttribute("message", "Quantity Sold cannot exceed 1,000.");
            response.sendRedirect("manager");
            return;
        }

        // Volume Validation
        if (volume == null || volume.trim().isEmpty()) {
            request.getSession().setAttribute("message", "Volume cannot be empty.");
            response.sendRedirect("manager");
            return;
        }
        double volumeValue = Double.parseDouble(volume);
        if (volumeValue <= 0) {
            request.getSession().setAttribute("message", "Volume must be greater than zero.");
            response.sendRedirect("manager");
            return;
        }
        if (volumeValue > 100) {
            request.getSession().setAttribute("message", "Volume cannot exceed 100.");
            response.sendRedirect("manager");
            return;
        }

        // Color Validation
        if (color == null || color.trim().isEmpty()) {
            request.getSession().setAttribute("message", "Color cannot be empty.");
            response.sendRedirect("manager");
            return;
        }
        if (!LETTERS_ONLY.matcher(color).matches()) {
            request.getSession().setAttribute("message", "Color can only contain letters.");
            response.sendRedirect("manager");
            return;
        }
        if (color.length() > 50) {
            request.getSession().setAttribute("message", "Color cannot exceed 50 characters.");
            response.sendRedirect("manager");
            return;
        }
        if (dao.insert(name, image, price, stock, sold, volume, color, supplier, description, category, status)) {
            request.getSession().setAttribute("message", "Add successfully!");
            response.sendRedirect("manager");
        } else {
            response.sendRedirect("manager");
        }
    }
}
