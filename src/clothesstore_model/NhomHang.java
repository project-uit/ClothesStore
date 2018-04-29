/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author quochung
 */
public class NhomHang extends RecursiveTreeObject<NhomHang> {

    private StringProperty tennhomhang;

    public NhomHang() {
    }

    public NhomHang(StringProperty tennhomhang) {
        this.tennhomhang = tennhomhang;
    }

    public StringProperty getTennhomhang() {
        return tennhomhang;
    }

    public void setTennhomhang(StringProperty tennhomhang) {
        this.tennhomhang = tennhomhang;
    }

    
    public boolean isEmpty() {
        return tennhomhang.get().isEmpty();
    }

    
    public boolean insert() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {

            String query = "insert into nhomhang values(?)";

            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1, tennhomhang.get());
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

            String query = "delete from nhomhang where tennhomhang = ?";

            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1, tennhomhang.get());

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

    public ObservableList<NhomHang> getNHList() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<NhomHang> nhList = FXCollections.observableArrayList();

        if (con != null) {
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery("select * from nhomhang");) {
                while (rs.next()) {
                    StringProperty tennh;
                    String ten_nhomhang = rs.getString("tennhomhang");
                    tennh = new SimpleStringProperty(ten_nhomhang);

                    NhomHang nhomhang = new NhomHang(tennh);
                    nhList.add(nhomhang);

                }

            } catch (SQLException ex) {

            }

        }
        return nhList;

    }

    public void getNHList(JFXComboBox cmb) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery("select * from nhomhang");) {
                while (rs.next()) {
                    String ten_nhomhang = rs.getString("tennhomhang");
                    cmb.getItems().add(ten_nhomhang);
                }

            } catch (SQLException ex) {

            }

        }
    }

  
}
