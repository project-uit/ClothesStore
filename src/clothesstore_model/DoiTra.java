/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class DoiTra {

    private IntegerProperty madoitra;
    private IntegerProperty mahoadon;
    private Date ngaytra;
    private StringProperty lydo;
    private StringProperty tennhanvien;
    private StringProperty sodienthoai; // sdt khach hang

    public DoiTra() {
    }

    public DoiTra(int madoitra, int mahoadon, String tennhanvien, String sodienthoai, Date ngaytra, String lydo) {
        this.madoitra = new SimpleIntegerProperty(madoitra);
        this.mahoadon = new SimpleIntegerProperty(mahoadon);
        this.tennhanvien = new SimpleStringProperty(tennhanvien);
        this.sodienthoai = new SimpleStringProperty(sodienthoai);
        this.ngaytra = ngaytra;
        this.lydo = new SimpleStringProperty(lydo);
    }

    public DoiTra(int mahoadon, Date ngaytra, String lydo) {
        this.mahoadon = new SimpleIntegerProperty(mahoadon);
        this.ngaytra = ngaytra;
        this.lydo = new SimpleStringProperty(lydo);
    }

    public boolean ThemDoiTra() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO doitra(mahoadon, ngaytra, lydo) VALUES(?, ?, ?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, mahoadon.get());
                ptm.setDate(2, ngaytra);
                ptm.setString(3, lydo.get());
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

    public boolean updateTongTienHoaDon(int maHD, int TongTien) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "UPDATE hoadon set tongtien = tongtien + ? where mahoadon = ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, TongTien);
                ptm.setInt(2, maHD);

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

    public ObservableList<DoiTra> getListKhoDoiTra() {
        ObservableList<DoiTra> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM doitra, hoadon, nhanvien, chitietkhachhang "
                + "Where doitra.mahoadon = hoadon.mahoadon "
                + "and hoadon.manhanvien = nhanvien.manhanvien "
                + "and chitietkhachhang.mahoadon = hoadon.mahoadon";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    DoiTra doitra = new DoiTra(rs.getInt("madoitra"),
                            rs.getInt("mahoadon"),
                            rs.getString("tennhanvien"),
                            rs.getString("sodienthoai"),
                            rs.getDate("ngaytra"),
                            rs.getString("lydo")
                    );
                    list.add(doitra);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ObservableList<DoiTra> getListKhoDoiTraFromMaHD(int mhd) {
        ObservableList<DoiTra> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM doitra, hoadon, nhanvien, chitietkhachhang "
                + "Where doitra.mahoadon = hoadon.mahoadon "
                + "and hoadon.manhanvien = nhanvien.manhanvien "
                + "and chitietkhachhang.mahoadon = hoadon.mahoadon and doitra.mahoadon = " + mhd + "";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    DoiTra doitra = new DoiTra(rs.getInt("madoitra"),
                            rs.getInt("mahoadon"),
                            rs.getString("tennhanvien"),
                            rs.getString("sodienthoai"),
                            rs.getDate("ngaytra"),
                            rs.getString("lydo")
                    );
                    list.add(doitra);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ObservableList<DoiTra> getListKhoDoiTraFromMaDate(Date date) {
        ObservableList<DoiTra> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM doitra, hoadon, nhanvien, chitietkhachhang "
                + "Where doitra.mahoadon = hoadon.mahoadon "
                + "and hoadon.manhanvien = nhanvien.manhanvien "
                + "and chitietkhachhang.mahoadon = hoadon.mahoadon and doitra.ngaytra = '"+date+"'";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    DoiTra doitra = new DoiTra(rs.getInt("madoitra"),
                            rs.getInt("mahoadon"),
                            rs.getString("tennhanvien"),
                            rs.getString("sodienthoai"),
                            rs.getDate("ngaytra"),
                            rs.getString("lydo")
                    );
                    list.add(doitra);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public int getLastId() {
        int id = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT madoitra FROM doitra ORDER BY madoitra DESC LIMIT 1;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("madoitra");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public IntegerProperty getMadoitra() {
        return madoitra;
    }

    public IntegerProperty getMahoadon() {
        return mahoadon;
    }

    public Date getNgaytra() {
        return ngaytra;
    }

    public StringProperty getTennhanvien() {
        return tennhanvien;
    }

    public StringProperty getSodienthoai() {
        return sodienthoai;
    }

    public StringProperty getLydo() {
        return lydo;
    }

    public void setMadoitra(IntegerProperty madoitra) {
        this.madoitra = madoitra;
    }

    public void setMahoadon(IntegerProperty mahoadon) {
        this.mahoadon = mahoadon;
    }

    public void setNgaytra(Date ngaytra) {
        this.ngaytra = ngaytra;
    }

    public void setTennhanvien(StringProperty tennhanvien) {
        this.tennhanvien = tennhanvien;
    }

    public void setSodienthoai(StringProperty sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setLydo(StringProperty lydo) {
        this.lydo = lydo;
    }

}
