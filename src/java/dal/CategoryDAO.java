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
import java.util.logging.Logger;
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
            Logger.getLogger(CategoryDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }

        return list;
    }

    public Category getCategoryById(int id) {
        String sql = "SELECT CategoryID, CategoryName, Description FROM [dbo].[Categories] WHERE CategoryID = ?";

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
    
    public void create(Category c) {
        String sql = "INSERT INTO [dbo].[Categories] (CategoryName, Description) VALUES (?, ?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, c.getName());
            st.setString(2, c.getDescribe());

            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
    }

    public void update(Category c) {
        String sql = "UPDATE [dbo].[Categories] SET CategoryName = ?, Description = ? WHERE CategoryID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, c.getName());
            st.setString(2, c.getDescribe());
            st.setInt(3, c.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
    }

    
    public void delete(Category c) {
        String sql = "DELETE FROM [dbo].[Categories] WHERE CategoryID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, c.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
    }
}