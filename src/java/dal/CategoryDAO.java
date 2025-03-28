package dal;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Category;

/**
 *
 * @author anhbu
 */
public class CategoryDAO extends DBContext {

    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT [CategoryID]\n"
                + "      ,[CategoryName]\n"
                + "      ,[Description]\n"
                + "  FROM [dbo].[Categories]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("CategoryID"));
                c.setName(rs.getString("CategoryName"));
                c.setDescribe(rs.getString("Description"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM [dbo].[Categories] WHERE CategoryID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Category(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"),
                        rs.getString("Description")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void addCategory(String name, String description) {
        String sql = "INSERT INTO [dbo].[Categories] ([CategoryName], [Description]) VALUES (?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, description);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public boolean isCategoryExist(String name) {
        String sql = "SELECT 1 FROM [dbo].[Categories] WHERE CategoryName = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public static void main(String[] args) {
        CategoryDAO dao = new CategoryDAO();
        List<Category> list = dao.getAll();
        for (Category o : list) {
            System.out.println(o);
        }
    }

}
