/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author user
 */
public class ChiTietNhapKho {

    private IntegerProperty machitietnhapkho;
    private IntegerProperty manhapkho;
    private StringProperty machitietsanpham;
    private IntegerProperty soluong;

    public ChiTietNhapKho(int manhapkho, String machitietsanpham, int soluong) {
        this.manhapkho = new SimpleIntegerProperty(manhapkho);
        this.machitietsanpham = new SimpleStringProperty(machitietsanpham);
        this.soluong = new SimpleIntegerProperty(soluong);
    }

    public boolean ThemChiTietNhapKho() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO chitietnhapkho(manhapkho, machitietsanpham, soluong) VALUES(?, ?, ?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, manhapkho.get());
                ptm.setString(2, machitietsanpham.get());
                ptm.setInt(3, soluong.get());
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

    public IntegerProperty getMachitietnhapkho() {
        return machitietnhapkho;
    }

    public IntegerProperty getManhapkho() {
        return manhapkho;
    }

    public StringProperty getMachitietsanpham() {
        return machitietsanpham;
    }

    public IntegerProperty getSoluong() {
        return soluong;
    }

    public void setMachitietnhapkho(IntegerProperty machitietnhapkho) {
        this.machitietnhapkho = machitietnhapkho;
    }

    public void setManhapkho(IntegerProperty manhapkho) {
        this.manhapkho = manhapkho;
    }

    public void setMachitietsanpham(StringProperty machitietsanpham) {
        this.machitietsanpham = machitietsanpham;
    }

    public void setSoluong(IntegerProperty soluong) {
        this.soluong = soluong;
    }
}
