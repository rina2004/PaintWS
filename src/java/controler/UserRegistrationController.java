/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controler;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import model.User;

/**
 *
 * @author Rinaaaa
 */
@WebServlet(name = "UserRegistrationController", urlPatterns = {"/register"})
public class UserRegistrationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        String phoneNumber = request.getParameter("phoneNumber");

        String countryCode = getCountryCode(country);
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            // Remove any existing + sign or spaces
            phoneNumber = phoneNumber.replaceAll("[+\\s]", "");

            // Add the country code if it's not already included
            if (!phoneNumber.startsWith(countryCode.replace("+", ""))) {
                phoneNumber = countryCode + phoneNumber;
            }
        }

        boolean acceptTerms = "on".equals(request.getParameter("acceptTerms"));
        boolean acceptMarketing = "on".equals(request.getParameter("acceptMarketing"));

        // Validate input
        Map<String, String> errors = validateInput(username, password, confirmPassword,
                fullname, email, phoneNumber, country, acceptTerms);

        if (!errors.isEmpty()) {
            // If there are validation errors, return to the form with error messages
            request.setAttribute("errors", errors);
            request.setAttribute("user", new User(username, "", fullname, email, phoneNumber,
                    country, acceptTerms, acceptMarketing));
            request.getRequestDispatcher("/Registration.jsp").forward(request, response);
            return;
        }

        // Create user object
        User user = new User(username, password, fullname, email, phoneNumber,
                country, acceptTerms, acceptMarketing);

        // Register the user
        boolean isRegistered = userDAO.registerUser(user);

        if (isRegistered) {
            // Registration successful
            request.setAttribute("successMessage", "Registration successful! You can now login.");
            request.getRequestDispatcher("/Login.jsp").forward(request, response);
        } else {
            // Registration failed
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/Registration.jsp").forward(request, response);
        }
    }

    private Map<String, String> validateInput(String username, String password, String confirmPassword,
            String fullname, String email, String phoneNumber, String country, boolean acceptTerms) {
        Map<String, String> errors = new HashMap<>();

        // Validate username
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "Username is required.");
        } else if (username.length() < 5) {
            errors.put("username", "Username must be at least 5 characters long.");
        } else if (userDAO.isUsernameExists(username)) {
            errors.put("username", "Username is already taken.");
        } else if (!username.matches("^[a-zA-Z0-9_]*$")) {
            errors.put("username", "Username can only contain letters, numbers, and underscores.");
        }

        // Validate password
        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "Password is required.");
        } else if (password.length() < 8) {
            errors.put("password", "Password must be at least 8 characters long.");
        } else if (!password.matches(".*[a-zA-Z].*")) {
            errors.put("password", "Password must contain at least one letter.");
        } else if (!password.matches(".*\\d.*")) {
            errors.put("password", "Password must contain at least one number.");
        }

        // Validate password confirmation
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            errors.put("confirmPassword", "Confirm password is required.");
        } else if (!password.equals(confirmPassword)) {
            errors.put("confirmPassword", "Confirm password do not match.");
        }

        // Validate full name
        if (fullname == null || fullname.trim().isEmpty()) {
            errors.put("fullname", "Full name is required.");
        } else if (!fullname.matches("^[a-zA-Z\\s]+$")) {
            errors.put("fullname", "Full name should only contain letters.");
        } else if (fullname.trim().split("\\s+").length < 2) {
            errors.put("fullname", "Please enter your complete full name.");
        }

        // Validate email
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email address is required.");
        } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errors.put("email", "Please enter a valid email address.");
        } else if (userDAO.isEmailExists(email)) {
            errors.put("email", "Email address is already registered.");
        }

        // Validate phone number (optional)
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            errors.put("phoneNumber", "Phone number is required.");
        } else if (!phoneNumber.matches("^\\+?[0-9]{10,15}$")) {
            errors.put("phoneNumber", "Please enter a valid phone number for " + country + ".");
        } else if (userDAO.isPhoneExists(phoneNumber)) {
            errors.put("phoneNumber", "Phone number is already registered.");
        }

        // Validate terms acceptance
        if (!acceptTerms) {
            errors.put("acceptTerms", "You must agree to the terms of service to create an account.");
        }

        return errors;
    }

    private String getCountryCode(String countryCode) {
        // Map of country codes to calling codes
        Map<String, String> callingCodes = new HashMap<>();

        // Populate the map with country codes and their calling codes
        callingCodes.put("Afghanistan", "+93");
        callingCodes.put("Albania", "+355");
        callingCodes.put("Algeria", "+213");
        callingCodes.put("Andorra", "+376");
        callingCodes.put("Angola", "+244");
        callingCodes.put("Argentina", "+54");
        callingCodes.put("Armenia", "+374");
        callingCodes.put("Australia", "+61");
        callingCodes.put("Austria", "+43");
        callingCodes.put("Azerbaijan", "+994");
        callingCodes.put("Bahamas", "+1");
        callingCodes.put("Bahrain", "+973");
        callingCodes.put("Bangladesh", "+880");
        callingCodes.put("Belarus", "+375");
        callingCodes.put("Belgium", "+32");
        callingCodes.put("Belize", "+501");
        callingCodes.put("Benin", "+229");
        callingCodes.put("Bhutan", "+975");
        callingCodes.put("Bolivia", "+591");
        callingCodes.put("Bosnia and Herzegovina", "+387");
        callingCodes.put("Botswana", "+267");
        callingCodes.put("Brazil", "+55");
        callingCodes.put("Brunei", "+673");
        callingCodes.put("Bulgaria", "+359");
        callingCodes.put("Burkina Faso", "+226");
        callingCodes.put("Burundi", "+257");
        callingCodes.put("Cambodia", "+855");
        callingCodes.put("Cameroon", "+237");
        callingCodes.put("Canada", "+1");
        callingCodes.put("Cape Verde", "+238");
        callingCodes.put("Central African Republic", "+236");
        callingCodes.put("Chad", "+235");
        callingCodes.put("Chile", "+56");
        callingCodes.put("China", "+86");
        callingCodes.put("Colombia", "+57");
        callingCodes.put("Comoros", "+269");
        callingCodes.put("Congo", "+242");
        callingCodes.put("Costa Rica", "+506");
        callingCodes.put("Croatia", "+385");
        callingCodes.put("Cuba", "+53");
        callingCodes.put("Cyprus", "+357");
        callingCodes.put("Czech Republic", "+420");
        callingCodes.put("Denmark", "+45");
        callingCodes.put("Djibouti", "+253");
        callingCodes.put("Dominican Republic", "+1");
        callingCodes.put("Ecuador", "+593");
        callingCodes.put("Egypt", "+20");
        callingCodes.put("El Salvador", "+503");
        callingCodes.put("Equatorial Guinea", "+240");
        callingCodes.put("Eritrea", "+291");
        callingCodes.put("Estonia", "+372");
        callingCodes.put("Ethiopia", "+251");
        callingCodes.put("Fiji", "+679");
        callingCodes.put("Finland", "+358");
        callingCodes.put("France", "+33");
        callingCodes.put("Gabon", "+241");
        callingCodes.put("Gambia", "+220");
        callingCodes.put("Georgia", "+995");
        callingCodes.put("Germany", "+49");
        callingCodes.put("Ghana", "+233");
        callingCodes.put("Greece", "+30");
        callingCodes.put("Guatemala", "+502");
        callingCodes.put("Guinea", "+224");
        callingCodes.put("Guinea-Bissau", "+245");
        callingCodes.put("Haiti", "+509");
        callingCodes.put("Honduras", "+504");
        callingCodes.put("Hong Kong", "+852");
        callingCodes.put("Hungary", "+36");
        callingCodes.put("Iceland", "+354");
        callingCodes.put("India", "+91");
        callingCodes.put("Indonesia", "+62");
        callingCodes.put("Iran", "+98");
        callingCodes.put("Iraq", "+964");
        callingCodes.put("Ireland", "+353");
        callingCodes.put("Israel", "+972");
        callingCodes.put("Italy", "+39");
        callingCodes.put("Jamaica", "+1");
        callingCodes.put("Japan", "+81");
        callingCodes.put("Jordan", "+962");
        callingCodes.put("Kazakhstan", "+7");
        callingCodes.put("Kenya", "+254");
        callingCodes.put("Kiribati", "+686");
        callingCodes.put("North Korea", "+850");
        callingCodes.put("South Korea", "+82");
        callingCodes.put("Kuwait", "+965");
        callingCodes.put("Kyrgyzstan", "+996");
        callingCodes.put("Laos", "+856");
        callingCodes.put("Latvia", "+371");
        callingCodes.put("Lebanon", "+961");
        callingCodes.put("Lesotho", "+266");
        callingCodes.put("Liberia", "+231");
        callingCodes.put("Libya", "+218");
        callingCodes.put("Liechtenstein", "+423");
        callingCodes.put("Lithuania", "+370");
        callingCodes.put("Luxembourg", "+352");
        callingCodes.put("North Macedonia", "+389");
        callingCodes.put("Madagascar", "+261");
        callingCodes.put("Malawi", "+265");
        callingCodes.put("Malaysia", "+60");
        callingCodes.put("Maldives", "+960");
        callingCodes.put("Mali", "+223");
        callingCodes.put("Malta", "+356");
        callingCodes.put("Marshall Islands", "+692");
        callingCodes.put("Mauritania", "+222");
        callingCodes.put("Mauritius", "+230");
        callingCodes.put("Mexico", "+52");
        callingCodes.put("Micronesia", "+691");
        callingCodes.put("Moldova", "+373");
        callingCodes.put("Monaco", "+377");
        callingCodes.put("Mongolia", "+976");
        callingCodes.put("Montenegro", "+382");
        callingCodes.put("Morocco", "+212");
        callingCodes.put("Mozambique", "+258");
        callingCodes.put("Myanmar", "+95");
        callingCodes.put("Namibia", "+264");
        callingCodes.put("Nauru", "+674");
        callingCodes.put("Nepal", "+977");
        callingCodes.put("Netherlands", "+31");
        callingCodes.put("New Zealand", "+64");
        callingCodes.put("Nicaragua", "+505");
        callingCodes.put("Niger", "+227");
        callingCodes.put("Nigeria", "+234");
        callingCodes.put("Norway", "+47");
        callingCodes.put("Oman", "+968");
        callingCodes.put("Pakistan", "+92");
        callingCodes.put("Palau", "+680");
        callingCodes.put("Palestine", "+970");
        callingCodes.put("Panama", "+507");
        callingCodes.put("Papua New Guinea", "+675");
        callingCodes.put("Paraguay", "+595");
        callingCodes.put("Peru", "+51");
        callingCodes.put("Philippines", "+63");
        callingCodes.put("Poland", "+48");
        callingCodes.put("Portugal", "+351");
        callingCodes.put("Qatar", "+974");
        callingCodes.put("Romania", "+40");
        callingCodes.put("Russia", "+7");
        callingCodes.put("Rwanda", "+250");
        callingCodes.put("Samoa", "+685");
        callingCodes.put("San Marino", "+378");
        callingCodes.put("Sao Tome and Principe", "+239");
        callingCodes.put("Saudi Arabia", "+966");
        callingCodes.put("Senegal", "+221");
        callingCodes.put("Serbia", "+381");
        callingCodes.put("Seychelles", "+248");
        callingCodes.put("Sierra Leone", "+232");
        callingCodes.put("Singapore", "+65");
        callingCodes.put("Slovakia", "+421");
        callingCodes.put("Slovenia", "+386");
        callingCodes.put("Solomon Islands", "+677");
        callingCodes.put("Somalia", "+252");
        callingCodes.put("South Africa", "+27");
        callingCodes.put("South Sudan", "+211");
        callingCodes.put("Spain", "+34");
        callingCodes.put("Sri Lanka", "+94");
        callingCodes.put("Sudan", "+249");
        callingCodes.put("Suriname", "+597");
        callingCodes.put("Eswatini", "+268");
        callingCodes.put("Sweden", "+46");
        callingCodes.put("Switzerland", "+41");
        callingCodes.put("Syria", "+963");
        callingCodes.put("Taiwan", "+886");
        callingCodes.put("Tajikistan", "+992");
        callingCodes.put("Tanzania", "+255");
        callingCodes.put("Thailand", "+66");
        callingCodes.put("Timor-Leste", "+670");
        callingCodes.put("Togo", "+228");
        callingCodes.put("Tonga", "+676");
        callingCodes.put("Trinidad and Tobago", "+1");
        callingCodes.put("Tunisia", "+216");
        callingCodes.put("Turkey", "+90");
        callingCodes.put("Turkmenistan", "+993");
        callingCodes.put("Tuvalu", "+688");
        callingCodes.put("Uganda", "+256");
        callingCodes.put("Ukraine", "+380");
        callingCodes.put("United Arab Emirates", "+971");
        callingCodes.put("United Kingdom", "+44");
        callingCodes.put("United States", "+1");
        callingCodes.put("Uruguay", "+598");
        callingCodes.put("Uzbekistan", "+998");
        callingCodes.put("Vanuatu", "+678");
        callingCodes.put("Vatican City", "+39");
        callingCodes.put("Venezuela", "+58");
        callingCodes.put("Vietnam", "+84");
        callingCodes.put("Yemen", "+967");
        callingCodes.put("Zambia", "+260");
        callingCodes.put("Zimbabwe", "+263");

        // Return the calling code for the given country code, or an empty string if not found
        return callingCodes.getOrDefault(countryCode, "");
    }
}
