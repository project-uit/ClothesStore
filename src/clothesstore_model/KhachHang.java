/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author quochung
 */
public class KhachHang {

    private StringProperty tenkhachhang;
    private StringProperty sodienthoai;

    public KhachHang(StringProperty tenkhachhang, StringProperty sodienthoai) {
        this.tenkhachhang = tenkhachhang;
        this.sodienthoai = sodienthoai;
    }

    public KhachHang() {
    }

    public StringProperty getTenkhachhang() {
        return tenkhachhang;
    }

    public StringProperty getSodienthoai() {
        return sodienthoai;
    }

    public void setTenkhachhang(StringProperty tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public void setSodienthoai(StringProperty sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getTenkhachhang(String sdt) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String tenkh = "";
        if (con != null) {
            try {
                String sql = "SELECT tenkhachhang FROM khachhang where sodienthoai = ?";
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, sdt);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    tenkh = rs.getString("tenkhachhang");
                    break;
                }
                ptm.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tenkh;
    }

    public List<String> getListSDT() {
        List<String> list = new ArrayList<>();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            try {
                String sql = "SELECT sodienthoai FROM khachhang";
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    String temp = rs.getString("sodienthoai");
                    list.add(temp);
                }
                ptm.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
}