/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author quochung
 */
public class ChiTietKhachHang {

    private IntegerProperty machitietkhachhang;
    private StringProperty sodienthoai;
    private IntegerProperty mahoadon;

    public ChiTietKhachHang() {
    }

    
    public ChiTietKhachHang(IntegerProperty machitietkhachhang, StringProperty sodienthoai, IntegerProperty mahoadon) {
        this.machitietkhachhang = machitietkhachhang;
        this.sodienthoai = sodienthoai;
        this.mahoadon = mahoadon;
    }

    public ChiTietKhachHang(StringProperty sodienthoai, IntegerProperty mahoadon) {
        this.sodienthoai = sodienthoai;
        this.mahoadon = mahoadon;
    }
    
    public IntegerProperty getMachitietkhachhang() {
        return machitietkhachhang;
    }

    public StringProperty getSodienthoai() {
        return sodienthoai;
    }

    public IntegerProperty getMahoadon() {
        return mahoadon;
    }

    public void setMachitietkhachhang(IntegerProperty machitietkhachhang) {
        this.machitietkhachhang = machitietkhachhang;
    }

    public void setSodienthoai(StringProperty sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setMahoadon(IntegerProperty mahoadon) {
        this.mahoadon = mahoadon;
    }
    public boolean insert()
    {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "insert into chitietkhachhang(sodienthoai,mahoadon) "
                    + " values(?,?)";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, sodienthoai.get());
                ptm.setInt(2, mahoadon.get());
                int check = ptm.executeUpdate();
                if (check != 0) {
                    ptm.close();
                    con.close();
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return false;
    }
}
