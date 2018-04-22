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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;

/**
 *
 * @author quochung
 */
public class Size {
    private StringProperty tensize;

    public Size() {
    }

    public Size(StringProperty tensize) {
        this.tensize = tensize;
    }

    public StringProperty getTensize() {
        return tensize;
    }

    public void setTensize(StringProperty tensize) {
        this.tensize = tensize;
    }
    public boolean insert() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "insert into size(tensize)"
                    + " values(?) ";
                    
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, tensize.get());
                
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
            String query = "delete from size where tensize =? ";
                    
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, tensize.get());
                
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
    public void LoadTable(TableView tbv) {
        LoadTableFromDB tb = new LoadTableFromDB("select * from size");
        tb.LoadTable(tbv);
    }
      public void LoadCmB(JFXComboBox cmb) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery("select * from size");) {
                while (rs.next()) {
                    StringProperty _tensize = new SimpleStringProperty(rs.getString("tensize"));               
                    Size size = new Size(_tensize);
                    cmb.getItems().add(size);
                }
                
            } catch (SQLException ex) {

            }

        }
    }
}
