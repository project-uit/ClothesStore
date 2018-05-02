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
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author quochung
 */
public class CuaHang {
    private IntegerProperty id;
    private StringProperty tencuahang;
    private StringProperty diachi;
    private StringProperty sodienthoai;
    private StringProperty email;

    public CuaHang() {
       
    }
    
    public CuaHang(StringProperty tencuahang, StringProperty diachi, StringProperty sodienthoai, StringProperty email) {
        id = new SimpleIntegerProperty(1);
        this.tencuahang = tencuahang;
        this.diachi = diachi;
        this.sodienthoai = sodienthoai;
        this.email = email;
    }

    public StringProperty getTencuahang() {
        return tencuahang;
    }

    public StringProperty getDiachi() {
        return diachi;
    }

    public StringProperty getSodienthoai() {
        return sodienthoai;
    }

    public StringProperty getEmail() {
        return email;
    }

    public IntegerProperty getId() {
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public void setTencuahang(StringProperty tencuahang) {
        this.tencuahang = tencuahang;
    }

    public void setDiachi(StringProperty diachi) {
        this.diachi = diachi;
    }

    public void setSodienthoai(StringProperty sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setEmail(StringProperty email) {
        this.email = email;
    }
    public static CuaHang getObject()
    {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();        
        CuaHang store = new CuaHang();
        if (con != null) {
            try {
                String query ="select * from cuahang";
                Statement stmnt = con.createStatement();               
                ResultSet rs = stmnt.executeQuery(query);
                while (rs.next()) {                   
                    store.setId(new SimpleIntegerProperty(rs.getInt(1)));
                    store.setTencuahang(new SimpleStringProperty(rs.getString(2)));
                    store.setDiachi(new SimpleStringProperty(rs.getString(3)));
                    store.setSodienthoai(new SimpleStringProperty(rs.getString(4)));
                    store.setEmail(new SimpleStringProperty(rs.getString(5)));
                }
                stmnt.close();
                con.close();
            } catch (SQLException ex) {

            }
        }
        return store;
    }
    public boolean update()
    {
          DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "update cuahang set tencuahang=?,diachi=?,sodienthoai=?,email=? "
                    + "where id = ?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, tencuahang.get());
                ptm.setString(2, diachi.get());
                ptm.setString(3, sodienthoai.get());
                ptm.setString(4, email.get());
                ptm.setInt(5, id.get());
                int check = ptm.executeUpdate();
                if (check != 0) {
                    ptm.close();
                    con.close();
                    return true;
                }
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return false;
    }
    

}
