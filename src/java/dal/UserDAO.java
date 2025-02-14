package dal;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author anhbu
 */
public class UserDAO extends DBContext {

    public User login(String username, String password) {
        String sql = "SELECT * FROM [dbo].[Users] WHERE UserName = ? and Password = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User u = new User(rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("roleID"),
                        rs.getInt("userID")); // Lấy userID từ kết quả truy vấn
                return u;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public User checkExistingUser(String username, String email, String phone) {
        String sql = "SELECT * FROM [dbo].[Users] WHERE UserName = ? OR Email = ? OR Phone = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, email);
            st.setString(3, phone);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("roleID"),
                        rs.getInt("userID")); // Lấy userID từ kết quả truy vấn
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void signUp(String username, String password, String email, String phone, String address) {
        String sql = "INSERT INTO [dbo].[Users] (UserName, Password, Email, Phone, Address, RoleID) VALUES (?, ?, ?, ?, ?, 2)"; // roleID 2 giả định là người dùng thường
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, email);
            st.setString(4, phone);
            st.setString(5, address);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[Users]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User u = new User(rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("roleID"),
                        rs.getInt("userID")); // Lấy userID từ kết quả truy vấn
                userList.add(u); // Thêm vào danh sách người dùng
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return userList; // Trả về danh sách người dùng
    }

    public boolean deleteUser(int userID) {
        // Kiểm tra xem người dùng có RoleID = 2 không
        String checkRoleSql = "SELECT RoleID FROM [dbo].[Users] WHERE UserID = ?";
        try {
            PreparedStatement checkSt = connection.prepareStatement(checkRoleSql);
            checkSt.setInt(1, userID);
            ResultSet checkRs = checkSt.executeQuery();

            if (checkRs.next()) {
                int roleID = checkRs.getInt("RoleID");
                // Chỉ cho phép xóa người dùng nếu RoleID = 2
                if (roleID == 2) {
                    // Xóa tất cả các đơn hàng liên quan
                    String deleteOrdersSql = "DELETE FROM [dbo].[Orders] WHERE UserID = ?";
                    PreparedStatement deleteOrdersSt = connection.prepareStatement(deleteOrdersSql);
                    deleteOrdersSt.setInt(1, userID);
                    deleteOrdersSt.executeUpdate(); // Xóa đơn hàng liên quan

                    // Xóa người dùng
                    String deleteSql = "DELETE FROM [dbo].[Users] WHERE UserID = ?";
                    PreparedStatement deleteSt = connection.prepareStatement(deleteSql);
                    deleteSt.setInt(1, userID);
                    deleteSt.executeUpdate(); // Xóa người dùng
                    return true; // Trả về true nếu xóa thành công
                } else {
                    System.out.println("Không thể xóa người dùng này. Họ là quản trị viên.");
                    return false; // Trả về false nếu không phải người dùng thường
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        return false; // Trả về false nếu không tìm thấy người dùng
    }

    public boolean insertUser(String username, String password, String email, String phone, String address, String roleID) {
        // Kiểm tra sự tồn tại của username, email, hoặc phone
        if (checkExistingUser(username, email, phone) != null) {
            System.out.println("Username, email hoặc số điện thoại đã tồn tại.");
            return false; // Không thêm người dùng nếu dữ liệu đã tồn tại
        }

        String sql = "INSERT INTO [dbo].[Users] (UserName, Password, Email, Phone, Address, RoleID) VALUES (?, ?, ?, ?, ?, ?)"; // Thêm roleID vào câu lệnh SQL
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, email);
            st.setString(4, phone);
            st.setString(5, address);
            st.setString(6, roleID); // Thiết lập roleID
            st.executeUpdate(); // Thêm người dùng
            return true; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            return false;
        }
    }

    public User getUserByID(String userID) {
        String sql = "SELECT * FROM [dbo].[Users] WHERE UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, userID); // Đặt userID dạng String
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                // Tạo đối tượng User từ kết quả truy vấn
                return new User(
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("roleID"),
                        rs.getInt("userID")
                );
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        return null; // Trả về null nếu không tìm thấy người dùng
    }

    public boolean updateUser(String userID, String username, String password, String address, String phone, String email, String newRoleID) {
        String getRoleSql = "SELECT RoleID FROM [dbo].[Users] WHERE UserID = ?";
        try {
            PreparedStatement getRoleStmt = connection.prepareStatement(getRoleSql);
            getRoleStmt.setString(1, userID); // Sử dụng userID dạng String
            ResultSet rs = getRoleStmt.executeQuery();

            if (rs.next()) {
                int currentRoleID = rs.getInt("RoleID");

                // Kiểm tra xem số điện thoại và email có trùng không
                String checkDuplicateSql = "SELECT COUNT(*) FROM [dbo].[Users] WHERE (Phone = ? OR Email = ?) AND UserID <> ?";
                PreparedStatement checkDuplicateStmt = connection.prepareStatement(checkDuplicateSql);
                checkDuplicateStmt.setString(1, phone);
                checkDuplicateStmt.setString(2, email);
                checkDuplicateStmt.setString(3, userID);
                ResultSet duplicateRs = checkDuplicateStmt.executeQuery();

                if (duplicateRs.next() && duplicateRs.getInt(1) > 0) {
                    System.out.println("Số điện thoại hoặc email đã tồn tại.");
                    return false; // Không cập nhật nếu trùng lặp
                }

                // Chỉ cho phép thay đổi từ 2 lên 1, không cho phép từ 1 xuống 2
                if (currentRoleID == 2 || (currentRoleID == 1 && newRoleID.equals("1"))) {
                    // Cập nhật thông tin người dùng
                    String updateSql = "UPDATE [dbo].[Users] SET UserName = ?, Password = ?, Address = ?, Phone = ?, Email = ?, RoleID = ? WHERE UserID = ?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateSql);
                    updateStmt.setString(1, username);
                    updateStmt.setString(2, password);
                    updateStmt.setString(3, address);
                    updateStmt.setString(4, phone);
                    updateStmt.setString(5, email);
                    updateStmt.setString(6, newRoleID); // Sử dụng newRoleID dưới dạng String
                    updateStmt.setString(7, userID); // Sử dụng userID dưới dạng String

                    int rowsAffected = updateStmt.executeUpdate();
                    return rowsAffected > 0; // Trả về true nếu cập nhật thành công
                } else if (currentRoleID == 1 && newRoleID.equals("2")) {
                    System.out.println("Không thể hạ cấp từ admin xuống người dùng.");
                    return false; // Không cho phép hạ từ admin xuống người dùng
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
        }
        return false; // Trả về false nếu không tìm thấy người dùng hoặc không cập nhật được
    }

}
