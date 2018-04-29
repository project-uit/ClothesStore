/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author quochung
 */
public class HoaDon {

    private IntegerProperty mahoadon;
    private StringProperty manhanvien;
    private StringProperty sodienthoai;
    private Date ngayban;
    private IntegerProperty tongtien;

    public HoaDon() {
    }

    public HoaDon(StringProperty manhanvien, StringProperty sodienthoai, IntegerProperty tongtien) {
        this.manhanvien = manhanvien;
        this.sodienthoai = sodienthoai;
        this.tongtien = tongtien;
    }

    public HoaDon(StringProperty manhanvien, StringProperty sodienthoai, Date ngayban) {
        this.manhanvien = manhanvien;
        this.sodienthoai = sodienthoai;
        this.ngayban = ngayban;
    }
    
    public HoaDon(IntegerProperty mahoadon, StringProperty manhanvien, StringProperty sodienthoai, Date ngayban, IntegerProperty tongtien) {
        this.mahoadon = mahoadon;
        this.manhanvien = manhanvien;
        this.sodienthoai = sodienthoai;
        this.ngayban = ngayban;
        this.tongtien = tongtien;
    }

    public IntegerProperty getMahoadon() {
        return mahoadon;
    }

    public StringProperty getManhanvien() {
        return manhanvien;
    }

    public StringProperty getSodienthoai() {
        return sodienthoai;
    }

    public Date getNgayban() {
        return ngayban;
    }

    public IntegerProperty getTongtien() {
        return tongtien;
    }

    public void setMahoadon(IntegerProperty mahoadon) {
        this.mahoadon = mahoadon;
    }

    public void setManhanvien(StringProperty manhanvien) {
        this.manhanvien = manhanvien;
    }

    public void setSodienthoai(StringProperty sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setNgayban(Date ngayban) {
        this.ngayban = ngayban;
    }

    public void setTongtien(IntegerProperty tongtien) {
        this.tongtien = tongtien;
    }
    public Integer getMaHoaDon()
    {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        Integer mahd=-1;
        if (con != null) {
            try {
                String sql = "SELECT mahoadon FROM hoadon where tongtien is null";
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    mahd = rs.getInt("mahoadon");                    
                }
                ptm.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mahd;
    }
    public boolean insert() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {

            String query = "insert into hoadon(manhanvien,sodienthoai,ngayban,tongtien)"
                    + " values(?,?,now(),?)";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, manhanvien.get());
                ptm.setString(2, sodienthoai.get());
                ptm.setInt(3, tongtien.get());
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

    public boolean delete() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "delete from hoadon "
                    + " mahoadon = ?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, mahoadon.get());
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
