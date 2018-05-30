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
public class ChiTietHoaDonMuaHang {

    private IntegerProperty machitiethoadonmuahang;
    private StringProperty masanpham;
    private IntegerProperty mahoadonmuahang;
    private IntegerProperty soluongsanphamnhap;
    private IntegerProperty giavon;
    private IntegerProperty thanhtien;

    /* */
    private StringProperty tensanpham;
    private BooleanProperty checked;
    private IntegerProperty giaban;

    public ChiTietHoaDonMuaHang(int mpn, String masanpham, String tensanpham, int giaban) {
        this.mahoadonmuahang = new SimpleIntegerProperty(mpn);
        this.masanpham = new SimpleStringProperty(masanpham);
        this.tensanpham = new SimpleStringProperty(tensanpham);
        this.soluongsanphamnhap = new SimpleIntegerProperty(0);
        this.giavon = new SimpleIntegerProperty(0);
        this.giaban = new SimpleIntegerProperty(giaban);
        this.thanhtien = new SimpleIntegerProperty(0);
        this.checked = new SimpleBooleanProperty(false);
    }

    public ChiTietHoaDonMuaHang(int machitiethoadonmuahang, String masanpham, int mahoadonmuahang, int soluongsanphamnhap, int giavon, int thanhtien, String tensanpham) {
        this.machitiethoadonmuahang = new SimpleIntegerProperty(machitiethoadonmuahang);
        this.masanpham = new SimpleStringProperty(masanpham);
        this.mahoadonmuahang = new SimpleIntegerProperty(mahoadonmuahang);
        this.soluongsanphamnhap = new SimpleIntegerProperty(soluongsanphamnhap);
        this.giavon = new SimpleIntegerProperty(giavon);
        this.thanhtien = new SimpleIntegerProperty(thanhtien);
        this.tensanpham = new SimpleStringProperty(tensanpham);
    }

    public ChiTietHoaDonMuaHang(int machitiethoadonmuahang, String masanpham, int mahoadonmuahang, int soluongsanphamnhap, int giavon, int thanhtien) {
        this.machitiethoadonmuahang = new SimpleIntegerProperty(machitiethoadonmuahang);
        this.masanpham = new SimpleStringProperty(masanpham);
        this.mahoadonmuahang = new SimpleIntegerProperty(mahoadonmuahang);
        this.soluongsanphamnhap = new SimpleIntegerProperty(soluongsanphamnhap);
        this.giavon = new SimpleIntegerProperty(giavon);
        this.thanhtien = new SimpleIntegerProperty(thanhtien);
    }

    public ChiTietHoaDonMuaHang(String masanpham, int mahoadonmuahang, int soluongsanphamnhap, int giavon, int thanhtien) {
        this.masanpham = new SimpleStringProperty(masanpham);
        this.mahoadonmuahang = new SimpleIntegerProperty(mahoadonmuahang);
        this.soluongsanphamnhap = new SimpleIntegerProperty(soluongsanphamnhap);
        this.giavon = new SimpleIntegerProperty(giavon);
        this.thanhtien = new SimpleIntegerProperty(thanhtien);
    }

    public int getMachitiethoadonmuahang() {
        return machitiethoadonmuahang.getValue();
    }

    public String getMasanpham() {
        return masanpham.getValue();
    }

    public int getMahoadonmuahang() {
        return mahoadonmuahang.getValue();
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

    public IntegerProperty getGiaban() {
        return giaban;
    }

    public void setGiaban(IntegerProperty giaban) {
        this.giaban = giaban;
    }

    public void setMachitiethoadonmuahang(IntegerProperty machitiethoadonmuahang) {
        this.machitiethoadonmuahang = machitiethoadonmuahang;
    }

    public void setMasanpham(StringProperty masanpham) {
        this.masanpham = masanpham;
    }

    public void setMahoadonmuahang(IntegerProperty mahoadonmuahang) {
        this.mahoadonmuahang = mahoadonmuahang;
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

    public boolean isChecked() {
        return checked.get();
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public BooleanProperty checkedProperty() {
        return checked;
    }

    public ObservableList<ChiTietHoaDonMuaHang> getTableChiTietPhieuNhap(int mahoadonmuahang) {
        ObservableList<ChiTietHoaDonMuaHang> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT machitiethoadonmuahang, ctpn.masanpham, mahoadonmuahang, soluongsanphamnhap, giavon, thanhtien, tensanpham\n"
                + "FROM chitiethoadonmuahang ctpn, sanpham sp\n"
                + "where ctpn.masanpham = sp.masanpham and mahoadonmuahang = ?;";

        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, mahoadonmuahang);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    ChiTietHoaDonMuaHang ctpn;
                    ctpn = new ChiTietHoaDonMuaHang(rs.getInt("machitiethoadonmuahang"),
                            rs.getString("masanpham"),
                            rs.getInt("mahoadonmuahang"),
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
        String sql = "update chitiethoadonmuahang set  masanpham = ?,mahoadonmuahang = ?,soluongsanphamnhap = ?, giavon = ?,thanhtien=?  WHERE machitiethoadonmuahang = ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);

                ptm.setString(1, masanpham.getValue());
                ptm.setInt(2, mahoadonmuahang.getValue());
                ptm.setInt(3, Integer.valueOf(soluongsanphamnhap.getValue()));
                ptm.setInt(4, giavon.getValue());
                ptm.setInt(5, thanhtien.getValue());
                ptm.setInt(6, machitiethoadonmuahang.getValue());
                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public ChiTietHoaDonMuaHang() {
    }

    //insert,delete,update,LoadTable
    public boolean ThemChiTietPhieuNhap() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "insert into chitiethoadonmuahang(masanpham,mahoadonmuahang,soluongsanphamnhap,giavon,thanhtien)  values ( ?, ?, ?, ?, ?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);

                ptm.setString(1, this.masanpham.getValue());
                ptm.setInt(2, this.mahoadonmuahang.getValue());
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

    public boolean XoaChiTietPhieuNhap(int machitiethoadonmuahang, String mahoadonmuahang) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "delete from chitiethoadonmuahang where machitiethoadonmuahang = ? AND mahoadonmuahang=?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, machitiethoadonmuahang);
                ptm.setString(2, mahoadonmuahang);
                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public ObservableList<ChiTietHoaDonMuaHang> LoadSanPhamDaNhap(int mpn) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ChiTietHoaDonMuaHang> list = FXCollections.observableArrayList();

        if (con != null) {
            try {
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from sanpham where giaban is not null");
                while (rs.next()) {
                    ChiTietHoaDonMuaHang ctpn = new ChiTietHoaDonMuaHang(mpn,
                            rs.getString("masanpham"),
                            rs.getString("tensanpham"),
                            rs.getInt("giaban"));
                    list.add(ctpn);
                }
            } catch (SQLException ex) {
            }
        }
        return list;
    }

    public ObservableList<ChiTietHoaDonMuaHang> LoadSanPhamChuaNhap(int mpn) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ChiTietHoaDonMuaHang> list = FXCollections.observableArrayList();

        if (con != null) {
            try {
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from sanpham where giaban is null");
                while (rs.next()) {
                    ChiTietHoaDonMuaHang ctpn = new ChiTietHoaDonMuaHang(mpn,
                            rs.getString("masanpham"),
                            rs.getString("tensanpham"),
                            rs.getInt("giaban"));
                    list.add(ctpn);
                }
            } catch (SQLException ex) {
            }
        }
        return list;
    }

    public boolean CapNhatTongTienPhieuNhap(int mahoadonmuahang) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "update hoadonmuahang set tongtien=(select SUM(thanhtien) "
                + "                       from chitiethoadonmuahang "
                + "			where mahoadonmuahang= ?)"
                + "where mahoadonmuahang= ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);

                ptm.setInt(1, mahoadonmuahang);
                ptm.setInt(2, mahoadonmuahang);

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

    public int getGiaBanSP(String msp) {
        int gb = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            try {
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("select giaban from sanpham where masanpham = '" + msp + "'");
                while (rs.next()) {
                    gb = rs.getInt("giaban");
                }
            } catch (SQLException ex) {
            }
        }
        return gb;
    }

}
