/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.beans.property.IntegerProperty;

/**
 *
 * @author user
 */
public class HoaDonDoiTra {

    private IntegerProperty mahoadondoitra;
    private IntegerProperty madoitra;
    private IntegerProperty thanhtien;

    public HoaDonDoiTra() {
    }

    public HoaDonDoiTra(IntegerProperty mahoadondoitra, IntegerProperty madoitra, IntegerProperty thanhtien) {
        this.mahoadondoitra = mahoadondoitra;
        this.madoitra = madoitra;
        this.thanhtien = thanhtien;
    }

    public boolean ThemHoaDonDoiTra(int madt, int thanhtien) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO hoadondoitra(madoitra, thanhtien) VALUES(?, ?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, madt);
                ptm.setInt(2, thanhtien);
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

    public int getLastId() {
        int id = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT mahoadondoitra FROM hoadondoitra ORDER BY mahoadondoitra DESC LIMIT 1;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("mahoadondoitra");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public IntegerProperty getMahoadondoitra() {
        return mahoadondoitra;
    }

    public IntegerProperty getMadoitra() {
        return madoitra;
    }

    public IntegerProperty getThanhtien() {
        return thanhtien;
    }

    public void setMahoadondoitra(IntegerProperty mahoadondoitra) {
        this.mahoadondoitra = mahoadondoitra;
    }

    public void setMadoitra(IntegerProperty madoitra) {
        this.madoitra = madoitra;
    }

    public void setThanhtien(IntegerProperty thanhtien) {
        this.thanhtien = thanhtien;
    }

}
