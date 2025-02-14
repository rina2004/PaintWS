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
import model.Supplier;

/**
 *
 * @author anhbu
 */
public class SupplierDAO extends DBContext {

    public List<Supplier> getAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM Suppliers";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setId(rs.getInt("SupplierID"));
                s.setCompanyName(rs.getString("CompanyName"));
                s.setContactName(rs.getString("ContactName"));
                s.setCountry(rs.getString("Country"));
                s.setPhone(rs.getString("Phone"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public Supplier getSupplierById(int id) {
        String sql = "SELECT * FROM [dbo].[Suppliers] WHERE SupplierID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Supplier(
                        rs.getInt("SupplierID"),
                        rs.getString("CompanyName"),
                        rs.getString("ContactName"),
                        rs.getString("Country"),
                        rs.getString("Phone")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

}
