/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import com.jfoenix.controls.JFXComboBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;

/**
 *
 * @author quochung
 */
public class MauSac {

    private StringProperty mamau;
    private StringProperty tenmau;
    private IntegerProperty trangthai;

    public MauSac() {
    }

    public MauSac(StringProperty mamau, StringProperty tenmau, IntegerProperty trangthai) {
        this.mamau = mamau;
        this.tenmau = tenmau;
        this.trangthai = trangthai;
    }

    public MauSac(StringProperty mamau, IntegerProperty trangthai) {
        this.mamau = mamau;
        this.trangthai = trangthai;
    }

    public MauSac(StringProperty tenmau) {
        this.tenmau = tenmau;
        trangthai = new SimpleIntegerProperty(1);

    }

    public StringProperty getMamau() {
        return mamau;
    }

    public StringProperty getTenmau() {
        return tenmau;
    }

    public IntegerProperty getTrangthai() {
        return trangthai;
    }

    public void setMamau(StringProperty mamau) {
        this.mamau = mamau;
    }

    public void setTenmau(StringProperty tenmau) {
        this.tenmau = tenmau;
    }

    public void setTrangthai(IntegerProperty trangthai) {
        this.trangthai = trangthai;
    }

    public boolean insert() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "insert into mausac(tenmau,trangthai)"
                    + " values(?,?)";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, tenmau.get());
                ptm.setInt(2, trangthai.get());
                int check = ptm.executeUpdate();
                if (check != 0) {
                    ptm.close();
                    con.close();
                    return true;
                }

            } catch (SQLException ex) {

            }
        }
        return false;
    }

    public boolean UpdateNotToSee() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "Update mausac set trangthai=? where mamau=?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, trangthai.get());
                ptm.setString(2, mamau.get());
                int check = ptm.executeUpdate();
                if (check != 0) {

                    ptm.close();
                    con.close();
                    return true;
                }

            } catch (SQLException ex) {

            }
        }
        return false;
    }

    public void LoadCmB(JFXComboBox cmb) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery("select * from mausac where trangthai=1");) {
                while (rs.next()) {
                    StringProperty _tenmau = new SimpleStringProperty(rs.getString("tenmau"));
                    StringProperty _mamau = new SimpleStringProperty(rs.getString("mamau"));
                    IntegerProperty _trangthai = new SimpleIntegerProperty(rs.getInt("trangthai"));
                    MauSac mausac = new MauSac(_mamau, _tenmau, _trangthai);
                    cmb.getItems().add(mausac);
                }

            } catch (SQLException ex) {

            }

        }
    }

    public void LoadTable(TableView tbv) {
        LoadTableFromDB tb = new LoadTableFromDB("select * from mausac where trangthai=1");
        tb.LoadTable(tbv);
    }

    public String  getTenMauFromMaMau(String mamau) {
        String tenmau ="";
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT tenmau FROM mausac WHERE mamau ='"+mamau+"'";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    tenmau = rs.getString("tenmau");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tenmau;
    }
}
