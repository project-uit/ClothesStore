/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author dieunguyen
 */
public class NhapKho {

    private IntegerProperty manhapkho;
    private IntegerProperty manhanvien;
    private IntegerProperty mahoadonmuahang;

    public NhapKho() {
    }

    public NhapKho(IntegerProperty manhanvien, IntegerProperty mahoadonmuahang) {
        this.manhanvien = manhanvien;
        this.mahoadonmuahang = mahoadonmuahang;
    }

    public NhapKho(int manhanvien, int mahoadonmuahang) {
        this.manhanvien = new SimpleIntegerProperty(manhanvien);
        this.mahoadonmuahang = new SimpleIntegerProperty(mahoadonmuahang);
    }

    public boolean ThemNhapKho() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO nhapkho(manhanvien, mahoadonmuahang) VALUES(?, ?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, manhanvien.getValue());
                ptm.setInt(2, mahoadonmuahang.getValue());
                ptm.execute();

                ptm.close();
                con.close();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List getListMaSPDaNhapKho(int mapn) {
        List list = new ArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT sp.masanpham "
                + "FROM nhapkho nk, chitietnhapkho ctnk, sanpham sp, chitietsanpham ctsp "
                + "where nk.manhapkho = ctnk.manhapkho and ctnk.machitietsanpham = ctsp.machitietsanpham "
                + "and sp.masanpham = ctsp.masanpham "
                + "and nk.mahoadonmuahang = "+mapn+"";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    list.add(rs.getString("masanpham"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public Boolean checkExist(int mahoadonmuahang) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT manhapkho FROM nhapkho Where mahoadonmuahang = "+mahoadonmuahang+"";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    if (rs.getString("manhapkho") != null)
                        return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public int getMaNhapKhoFromMaPN(int mahoadonmuahang) {
        int mank = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT manhapkho FROM nhapkho Where mahoadonmuahang = " + mahoadonmuahang + "";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    mank = rs.getInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mank;
    }

    public IntegerProperty getManhapkho() {
        return manhapkho;
    }

    public IntegerProperty getManhanvien() {
        return manhanvien;
    }

    public IntegerProperty getMahoadonmuahang() {
        return mahoadonmuahang;
    }

    public void setManhapkho(IntegerProperty manhapkho) {
        this.manhapkho = manhapkho;
    }

    public void setManhanvien(IntegerProperty manhanvien) {
        this.manhanvien = manhanvien;
    }

    public void setMahoadonmuahang(IntegerProperty mahoadonmuahang) {
        this.mahoadonmuahang = mahoadonmuahang;
    }
}
