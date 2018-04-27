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
import java.sql.Statement;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 15520
 */
public class ChiTietPhieuNhap {

    private IntegerProperty machitietphieunhap;
    private StringProperty masanpham;
    private IntegerProperty maphieunhap;
    private IntegerProperty soluongsanphamnhap;
    private IntegerProperty giavon;
    private IntegerProperty thanhtien;
    private StringProperty tensanpham;
    private BooleanProperty checked;
    /* */
    private StringProperty string_soluongsanphamnhap;
    private StringProperty string_giavon;

    public ChiTietPhieuNhap(int mpn, String masanpham, String tensanpham) {
        this.maphieunhap = new SimpleIntegerProperty(mpn);
        this.masanpham = new SimpleStringProperty(masanpham);
        this.tensanpham = new SimpleStringProperty(tensanpham);
        this.soluongsanphamnhap = new SimpleIntegerProperty(0);
        this.giavon = new SimpleIntegerProperty(0);
        this.thanhtien = new SimpleIntegerProperty(0);
        this.checked = new SimpleBooleanProperty(false);
    }

    public ChiTietPhieuNhap(int machitietphieunhap, String masanpham, int maphieunhap, int soluongsanphamnhap, int giavon, int thanhtien, String tensanpham) {
        this.machitietphieunhap = new SimpleIntegerProperty(machitietphieunhap);
        this.masanpham = new SimpleStringProperty(masanpham);
        this.maphieunhap = new SimpleIntegerProperty(maphieunhap);
        this.soluongsanphamnhap = new SimpleIntegerProperty(soluongsanphamnhap);
        this.giavon = new SimpleIntegerProperty(giavon);
        this.thanhtien = new SimpleIntegerProperty(thanhtien);
        this.tensanpham = new SimpleStringProperty(tensanpham);
    }

    public ChiTietPhieuNhap(int machitietphieunhap, String masanpham, int maphieunhap, int soluongsanphamnhap, int giavon, int thanhtien) {
        this.machitietphieunhap = new SimpleIntegerProperty(machitietphieunhap);
        this.masanpham = new SimpleStringProperty(masanpham);
        this.maphieunhap = new SimpleIntegerProperty(maphieunhap);
        this.soluongsanphamnhap = new SimpleIntegerProperty(soluongsanphamnhap);
        this.giavon = new SimpleIntegerProperty(giavon);
        this.thanhtien = new SimpleIntegerProperty(thanhtien);
    }

    public ChiTietPhieuNhap(String masanpham, int maphieunhap, int soluongsanphamnhap, int giavon, int thanhtien) {
        this.masanpham = new SimpleStringProperty(masanpham);
        this.maphieunhap = new SimpleIntegerProperty(maphieunhap);
        this.soluongsanphamnhap = new SimpleIntegerProperty(soluongsanphamnhap);
        this.giavon = new SimpleIntegerProperty(giavon);
        this.thanhtien = new SimpleIntegerProperty(thanhtien);
    }

    public int getMachitietphieunhap() {
        return machitietphieunhap.getValue();
    }

    public String getMasanpham() {
        return masanpham.getValue();
    }

    public int getMaphieunhap() {
        return maphieunhap.getValue();
    }

    public int getSoluongsanphamnhap() {
        return soluongsanphamnhap.getValue();
    }

    public int getGiavon() {
        return giavon.getValue();
    }

    public int getThanhtien() {
        return thanhtien.getValue();
    }

    public String getTensanpham() {
        return tensanpham.getValue();
    }

    public StringProperty getString_soluongsanphamnhap() {
        return string_soluongsanphamnhap;
    }

    public StringProperty getString_giavon() {
        return string_giavon;
    }

    public void setMachitietphieunhap(IntegerProperty machitietphieunhap) {
        this.machitietphieunhap = machitietphieunhap;
    }

    public void setMasanpham(StringProperty masanpham) {
        this.masanpham = masanpham;
    }

    public void setMaphieunhap(IntegerProperty maphieunhap) {
        this.maphieunhap = maphieunhap;
    }

    public void setSoluongsanphamnhap(IntegerProperty soluongsanphamnhap) {
        this.soluongsanphamnhap = soluongsanphamnhap;
    }

    public void setGiavon(IntegerProperty giavon) {
        this.giavon = giavon;
    }

    public void setThanhtien(IntegerProperty thanhtien) {
        this.thanhtien = thanhtien;
    }

    public void setTensanpham(StringProperty tensanpham) {
        this.tensanpham = tensanpham;
    }

    public void setString_soluongsanphamnhap(StringProperty string_soluongsanphamnhap) {
        this.string_soluongsanphamnhap = string_soluongsanphamnhap;
        this.soluongsanphamnhap = new SimpleIntegerProperty(Integer.valueOf(string_soluongsanphamnhap.get()));
    }

    public void setString_giavon(StringProperty string_giavon) {
        this.string_giavon = string_giavon;
        this.giavon = new SimpleIntegerProperty(Integer.valueOf(string_giavon.get()));
    }

    public boolean isChecked() {
        return checked.get();
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

    public ObservableList<ChiTietPhieuNhap> getTableChiTietPhieuNhap(int maphieunhap) {
        ObservableList<ChiTietPhieuNhap> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT machitietphieunhap, ctpn.masanpham, maphieunhap, soluongsanphamnhap, giavon, thanhtien, tensanpham\n"
                + "FROM chitietphieunhap ctpn, sanpham sp\n"
                + "where ctpn.masanpham = sp.masanpham and maphieunhap = ?;";

        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, maphieunhap);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    ChiTietPhieuNhap ctpn;
                    ctpn = new ChiTietPhieuNhap(rs.getInt("machitietphieunhap"),
                            rs.getString("masanpham"),
                            rs.getInt("maphieunhap"),
                            rs.getInt("soluongsanphamnhap"),
                            rs.getInt("giavon"),
                            rs.getInt("thanhtien"),
                            rs.getString("tensanpham")
                    );
                    list.add(ctpn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public boolean CapNhatChiTietPhieuNhap() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "update chitietphieunhap set  masanpham = ?,maphieunhap = ?,soluongsanphamnhap = ?, giavon = ?,thanhtien=?  WHERE machitietphieunhap = ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);

                ptm.setString(1, masanpham.getValue());
                ptm.setInt(2, maphieunhap.getValue());
                ptm.setInt(3, Integer.valueOf(soluongsanphamnhap.getValue()));
                ptm.setInt(4, giavon.getValue());
                ptm.setInt(5, thanhtien.getValue());
                ptm.setInt(6, machitietphieunhap.getValue());
                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public ChiTietPhieuNhap() {
    }

    //insert,delete,update,LoadTable
    public boolean ThemChiTietPhieuNhap() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "insert into chitietphieunhap( masanpham,maphieunhap,soluongsanphamnhap,giavon,thanhtien)  values ( ?, ?, ?, ?, ?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);

                ptm.setString(1, this.masanpham.getValue());
                ptm.setInt(2, this.maphieunhap.getValue());
                ptm.setInt(3, this.soluongsanphamnhap.getValue());
                ptm.setInt(4, this.giavon.getValue());
                ptm.setInt(5, this.thanhtien.getValue());
                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean XoaChiTietPhieuNhap(int machitietphieunhap, String maphieunhap) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "delete from chitietphieunhap where machitietphieunhap = ? AND maphieunhap=?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, machitietphieunhap);
                ptm.setString(2, maphieunhap);
                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public ObservableList<ChiTietPhieuNhap> LoadSanPhamDaNhap(int mpn) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ChiTietPhieuNhap> list = FXCollections.observableArrayList();

        if (con != null) {
            try {
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT DISTINCT sanpham.masanpham, sanpham.tensanpham, chitietphieunhap.soluongsanphamnhap\n"
                        + "FROM sanpham\n"
                        + "LEFT JOIN chitietphieunhap ON sanpham.masanpham = chitietphieunhap.masanpham\n"
                        + "where soluongsanphamnhap is not null;");
                while (rs.next()) {
                    ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(mpn,
                            rs.getString("masanpham"),
                            rs.getString("tensanpham"));
                    list.add(ctpn);
                }
            } catch (SQLException ex) {
            }
        }
        return list;
    }

    public ObservableList<ChiTietPhieuNhap> LoadSanPhamChuaNhap(int mpn) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ChiTietPhieuNhap> list = FXCollections.observableArrayList();

        if (con != null) {
            try {
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT sanpham.masanpham, sanpham.tensanpham,soluongsanphamnhap "
                        + "FROM sanpham "
                        + "LEFT JOIN chitietphieunhap ON sanpham.masanpham = chitietphieunhap.masanpham "
                        + "where soluongsanphamnhap is null");
                while (rs.next()) {
                    ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(mpn,
                            rs.getString("masanpham"),
                            rs.getString("tensanpham"));
                    list.add(ctpn);
                }
            } catch (SQLException ex) {
            }
        }
        return list;
    }

    public boolean CapNhatTongTienPhieuNhap(int maphieunhap) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "update phieunhap set tongtien=(select SUM(thanhtien) "
                + "                       from chitietphieunhap "
                + "			where maphieunhap= ?)"
                + "where maphieunhap= ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);

                ptm.setInt(1, maphieunhap);
                ptm.setInt(2, maphieunhap);

                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean CapNhatGiaBanSanPham(String masanpham, int giaban) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "update sanpham set giaban= ? where masanpham= ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, giaban);
                ptm.setString(2, masanpham);
                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
