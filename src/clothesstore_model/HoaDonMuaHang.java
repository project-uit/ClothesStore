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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 15520
 */
public class HoaDonMuaHang {
    private IntegerProperty mahoadonmuahang;
    private IntegerProperty tongtien;
    private IntegerProperty manhacungcap;
    private Date ngaynhap;

    private StringProperty tencungcap;

    public HoaDonMuaHang(int mahoadonmuahang, int tongtien, String tencungcap, Date ngaynhap) {
        this.mahoadonmuahang = new SimpleIntegerProperty(mahoadonmuahang);
        this.tongtien = new SimpleIntegerProperty(tongtien);
        this.tencungcap = new SimpleStringProperty(tencungcap);
        this.ngaynhap = ngaynhap;
    }

    public HoaDonMuaHang(int manhacungcap, Date ngaynhap, int tongtien) {
        this.tongtien = new SimpleIntegerProperty(tongtien);
        this.manhacungcap = new SimpleIntegerProperty(manhacungcap);
        this.ngaynhap = ngaynhap;
    }

    public HoaDonMuaHang(int mahoadonmuahang, int manhacungcap, Date ngaynhap) {
        this.mahoadonmuahang = new SimpleIntegerProperty(mahoadonmuahang);
        this.manhacungcap = new SimpleIntegerProperty(manhacungcap);
        this.ngaynhap = ngaynhap;
    }

    public HoaDonMuaHang() {
    }

    public int getMahoadonmuahang() {
        return mahoadonmuahang.getValue();
    }

    public Integer getTongtien() {
        return tongtien.getValue();
    }

    public int getManhacungcap() {
        return manhacungcap.getValue();
    }

    public Date getNgaynhap() {
        return ngaynhap;
    }

    public void setMahoadonmuahang(IntegerProperty mahoadonmuahang) {
        this.mahoadonmuahang = mahoadonmuahang;
    }

    public void setTongtien(IntegerProperty tongtien) {
        this.tongtien = tongtien;
    }

    public void setManhacungcap(IntegerProperty manhacungcap) {
        this.manhacungcap = manhacungcap;
    }

    public void setNgaynhap(Date ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public String getTencungcap() {
        return tencungcap.getValue();
    }

    public void setTencungcap(StringProperty tencungcap) {
        this.tencungcap = tencungcap;
    }

    public ObservableList<HoaDonMuaHang> getListPhieuNhap() {
        ObservableList<HoaDonMuaHang> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM hoadonmuahang, nhacungcap WHERE hoadonmuahang.manhacungcap = nhacungcap.manhacungcap";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {

                    HoaDonMuaHang hoadonmuahang = new HoaDonMuaHang(rs.getInt("mahoadonmuahang"),
                            rs.getInt("tongtien"),
                            rs.getString("tencungcap"),
                            rs.getDate("ngaynhap"));

                    list.add(hoadonmuahang);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ObservableList<HoaDonMuaHang> getListPhieuNhapChuaNhapKho() {
        ObservableList<HoaDonMuaHang> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT mahoadonmuahang, tongtien, ngaynhap, tencungcap\n"
                + "FROM(\n"
                + "	select pn.mahoadonmuahang, pn.tongtien, pn.ngaynhap, pn.manhacungcap\n"
                + "	from hoadonmuahang pn\n"
                + "	left join khosanpham ksp on pn.mahoadonmuahang = ksp.mahoadonmuahang \n"
                + "	where ksp.makhosanpham is null) PN, nhacungcap NCC\n"
                + "WHERE PN.manhacungcap = NCC.manhacungcap";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {

                    HoaDonMuaHang hoadonmuahang = new HoaDonMuaHang(rs.getInt("mahoadonmuahang"),
                            rs.getInt("tongtien"),
                            rs.getString("tencungcap"),
                            rs.getDate("ngaynhap"));

                    list.add(hoadonmuahang);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ObservableList<HoaDonMuaHang> getListPhieuNhapChuaNhapKhoTheoNgay(Date date) {
        ObservableList<HoaDonMuaHang> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT mahoadonmuahang, tongtien, ngaynhap, tencungcap\n"
                + "FROM(\n"
                + "	select pn.mahoadonmuahang, pn.tongtien, pn.ngaynhap, pn.manhacungcap\n"
                + "	from hoadonmuahang pn\n"
                + "	left join khosanpham ksp on pn.mahoadonmuahang = ksp.mahoadonmuahang \n"
                + "	where ksp.makhosanpham is null) PN, nhacungcap NCC\n"
                + "WHERE PN.mahoadonmuahang = NCC.manhacungcap and PN.ngaynhap = '" + date + "'";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {

                    HoaDonMuaHang hoadonmuahang = new HoaDonMuaHang(rs.getInt("mahoadonmuahang"),
                            rs.getInt("tongtien"),
                            rs.getString("tencungcap"),
                            rs.getDate("ngaynhap"));

                    list.add(hoadonmuahang);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ObservableList<HoaDonMuaHang> getListPhieuNhapTheoNgay(Date ngay) {
        ObservableList<HoaDonMuaHang> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM hoadonmuahang pn, nhacungcap ncc "
                + "WHERE pn.manhacungcap = ncc.manhacungcap and ngaynhap = '" + ngay + "'";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    HoaDonMuaHang hoadonmuahang = new HoaDonMuaHang(rs.getInt("mahoadonmuahang"),
                            rs.getInt("tongtien"),
                            rs.getString("tencungcap"),
                            rs.getDate("ngaynhap"));
                    list.add(hoadonmuahang);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public boolean ThemPhieuNhap() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO hoadonmuahang(  manhacungcap, ngaynhap,tongtien) VALUES( ?, ?,?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, manhacungcap.getValue());
                ptm.setDate(2, ngaynhap);
                ptm.setInt(3, tongtien.getValue());
                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean CapNhatPhieuNhap() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "update hoadonmuahang set  manhacungcap = ?,ngaynhap = ?  WHERE mahoadonmuahang = ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, this.manhacungcap.getValue());
                ptm.setDate(2, this.ngaynhap);
                ptm.setInt(3, mahoadonmuahang.getValue());
                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean XoaPhieuNhap(int mahoadonmuahang) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = " delete from hoadonmuahang where mahoadonmuahang =?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, mahoadonmuahang);
                ptm.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
