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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dieunguyen
 */
public class KhoSanPham {

    private IntegerProperty makhosanpham;
    private IntegerProperty manhanvien;
    private IntegerProperty maphieunhap;
    private StringProperty masanpham;

    public KhoSanPham() {
    }

    public KhoSanPham(IntegerProperty manhanvien, IntegerProperty maphieunhap) {
        this.manhanvien = manhanvien;
        this.maphieunhap = maphieunhap;
    }

    public KhoSanPham(int manhanvien, int maphieunhap, String masanpham) {
        this.manhanvien = new SimpleIntegerProperty(manhanvien);
        this.maphieunhap = new SimpleIntegerProperty(maphieunhap);
        this.masanpham = new SimpleStringProperty(masanpham);
    }

    public boolean ThemKhoSanPham() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO khosanpham(manhanvien, maphieunhap, masanpham) VALUES(?, ?, ?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, manhanvien.getValue());
                ptm.setInt(2, maphieunhap.getValue());
                ptm.setString(3, masanpham.getValue());
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
        String sql = "SELECT ksp.masanpham \n"
                + "FROM phieunhap pn, khosanpham ksp\n"
                + "where pn.maphieunhap = ksp.maphieunhap \n"
                + "and ksp.maphieunhap = '"+mapn+"';";
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

    public ObservableList<KhoSanPham> getListKhoSanPham() {
        ObservableList<KhoSanPham> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM khosanpham";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    KhoSanPham ksp = new KhoSanPham(rs.getInt("manhanvien"),
                             rs.getInt("maphieunhap"),
                             rs.getString("masanpham")
                    );
                    list.add(ksp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void setMakhosanpham(IntegerProperty makhosanpham) {
        this.makhosanpham = makhosanpham;
    }

    public void setManhanvien(IntegerProperty manhanvien) {
        this.manhanvien = manhanvien;
    }

    public void setMaphieunhap(IntegerProperty maphieunhap) {
        this.maphieunhap = maphieunhap;
    }

    public Integer getMakhosanpham() {
        return makhosanpham.getValue();
    }

    public Integer getManhanvien() {
        return manhanvien.getValue();
    }

    public Integer getMaphieunhap() {
        return maphieunhap.getValue();
    }

}