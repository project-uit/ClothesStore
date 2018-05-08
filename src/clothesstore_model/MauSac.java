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
import java.text.Normalizer;
import java.util.regex.Pattern;
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

    private StringProperty tenmau;

    public MauSac() {
    }

    public MauSac(StringProperty tenmau) {
        this.tenmau = tenmau;
    }

    public StringProperty getTenmau() {
        return tenmau;
    }

    public void setTenmau(StringProperty tenmau) {
        this.tenmau = tenmau;
    }

    public boolean insert() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "insert into mausac"
                    + " values(?)";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, tenmau.get());

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

    public boolean delete() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "delete from mausac "
                    + "where tenmau=?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, tenmau.get());

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
                    ResultSet rs = stmnt.executeQuery("select * from mausac");) {
                while (rs.next()) {                  
                    cmb.getItems().add(rs.getString(1));
                }

            } catch (SQLException ex) {

            }

        }
    }

    public void LoadTable(TableView tbv) {
        LoadTableFromDB tb = new LoadTableFromDB("select * from mausac ");
        tb.LoadTable(tbv);
    }

    public static String convertTVToEN(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "_").replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
