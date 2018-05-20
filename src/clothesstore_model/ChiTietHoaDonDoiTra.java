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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author user
 */
public class ChiTietHoaDonDoiTra {

    private IntegerProperty machitiethoadondoitra;
    private StringProperty machitietsanpham;
    private StringProperty tensanpham;
    private IntegerProperty giaban;
    private IntegerProperty soluongmua;
    private IntegerProperty thanhtien;

    public ChiTietHoaDonDoiTra() {
    }

    public ChiTietHoaDonDoiTra(IntegerProperty machitiethoadondoitra, StringProperty machitietsanpham, StringProperty tensanpham, IntegerProperty giaban, IntegerProperty soluongmua, IntegerProperty thanhtien) {
        this.machitiethoadondoitra = machitiethoadondoitra;
        this.machitietsanpham = machitietsanpham;
        this.tensanpham = tensanpham;
        this.giaban = giaban;
        this.soluongmua = soluongmua;
        this.thanhtien = thanhtien;
    }

    public ChiTietHoaDonDoiTra(StringProperty machitietsanpham, StringProperty tensanpham, IntegerProperty giaban, IntegerProperty soluongmua, IntegerProperty thanhtien) {
        this.machitietsanpham = machitietsanpham;
        this.tensanpham = tensanpham;
        this.giaban = giaban;
        this.soluongmua = soluongmua;
        this.thanhtien = thanhtien;
    }

    public ObservableList<ChiTietHoaDonDoiTra> getListcthdDoiTrafromID(int maDoiTra) {
        ObservableList<ChiTietHoaDonDoiTra> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT cthddt.machitietsanpham, sp.tensanpham, sp.giaban, cthddt.soluong, cthddt.thanhtien"
                + " FROM hoadondoitra hddt, chitiethoadondoitra cthddt, sanpham sp, chitietsanpham ctsp "
                + "Where sp.masanpham = ctsp.masanpham "
                + "and cthddt.mahoadondoitra = hddt.mahoadondoitra "
                + "and cthddt.machitietsanpham = ctsp.machitietsanpham and "
                + "madoitra = "+maDoiTra+"";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    ChiTietHoaDonDoiTra cthdDoiTra = new ChiTietHoaDonDoiTra(
                            new SimpleStringProperty(rs.getString("machitietsanpham")),
                            new SimpleStringProperty(rs.getString("tensanpham")),
                            new SimpleIntegerProperty(rs.getInt("giaban")),
                            new SimpleIntegerProperty(rs.getInt("soluong")),
                            new SimpleIntegerProperty(rs.getInt("thanhtien"))
                    );
                    list.add(cthdDoiTra);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ChiTietHoaDonDoiTra getCTSPfromMa(String mactsp) {
        ChiTietHoaDonDoiTra ctsp = new ChiTietHoaDonDoiTra();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            try {
                String sql = "SELECT machitietsanpham, tensanpham, giaban FROM sanpham, chitietsanpham "
                        + "where sanpham.masanpham = chitietsanpham.masanpham "
                        + "and machitietsanpham = '" + mactsp + "'";
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    String ma = rs.getString("machitietsanpham");
                    int giaban = rs.getInt("giaban");
                    String ten = rs.getString("tensanpham");
                    ctsp = new ChiTietHoaDonDoiTra(new SimpleStringProperty(ma),
                            new SimpleStringProperty(ten),
                            new SimpleIntegerProperty(giaban),
                            new SimpleIntegerProperty(0),
                            new SimpleIntegerProperty(0));
                }
                ptm.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ctsp;
    }

    public boolean ThemChiTietHoaDonDoiTra(int mahddt) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO chitiethoadondoitra(mahoadondoitra, machitietsanpham, soluong, thanhtien) VALUES(?, ?, ?, ?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, mahddt);
                ptm.setString(2, machitietsanpham.get());
                ptm.setInt(3, soluongmua.get());
                ptm.setInt(4, thanhtien.get());
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

    public IntegerProperty getMachitiethoadondoitra() {
        return machitiethoadondoitra;
    }

    public StringProperty getMachitietsanpham() {
        return machitietsanpham;
    }

    public StringProperty getTensanpham() {
        return tensanpham;
    }

    public IntegerProperty getGiaban() {
        return giaban;
    }

    public IntegerProperty getSoluongmua() {
        return soluongmua;
    }

    public IntegerProperty getThanhtien() {
        return thanhtien;
    }

    public void setMachitiethoadondoitra(IntegerProperty machitiethoadondoitra) {
        this.machitiethoadondoitra = machitiethoadondoitra;
    }

    public void setMachitietsanpham(StringProperty machitietsanpham) {
        this.machitietsanpham = machitietsanpham;
    }

    public void setTensanpham(StringProperty tensanpham) {
        this.tensanpham = tensanpham;
    }

    public void setGiaban(IntegerProperty giaban) {
        this.giaban = giaban;
    }

    public void setSoluongmua(IntegerProperty soluongmua) {
        this.soluongmua = soluongmua;
    }

    public void setThanhtien(IntegerProperty thanhtien) {
        this.thanhtien = thanhtien;
    }

}
