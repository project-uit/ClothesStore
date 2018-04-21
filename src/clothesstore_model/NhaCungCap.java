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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 15520
 */
public class NhaCungCap {
    private IntegerProperty manhacungcap;
    private StringProperty tencungcap;
    private StringProperty diachi;
    private StringProperty email;
    private StringProperty ghichu;

    public NhaCungCap(int manhacungcap, String tencungcap, String diachi, String email, String ghichu) {
        this.manhacungcap =new SimpleIntegerProperty (manhacungcap);
        this.tencungcap =new SimpleStringProperty ( tencungcap);
        this.diachi =new SimpleStringProperty ( diachi);
        this.email =new SimpleStringProperty ( email);
        this.ghichu =new SimpleStringProperty ( ghichu);
    }

    public int getManhacungcap() {
        return manhacungcap.getValue();
    }

    public String getTencungcap() {
        return tencungcap.getValue();
    }

    public String getDiachi() {
        return diachi.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getGhichu() {
        return ghichu.getValue();
    }

    public void setManhacungcap(IntegerProperty manhacungcap) {
        this.manhacungcap = manhacungcap;
    }

    public void setTencungcap(StringProperty tencungcap) {
        this.tencungcap = tencungcap;
    }

    public void setDiachi(StringProperty diachi) {
        this.diachi = diachi;
    }

    public void setEmail(StringProperty email) {
        this.email = email;
    }

    public void setGhichu(StringProperty ghichu) {
        this.ghichu = ghichu;
    }
    
    public ObservableList<NhaCungCap> getTableNhaCungCap(){      
        ObservableList<NhaCungCap> list = FXCollections.observableArrayList(); 
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM nhacungcap";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    NhaCungCap cnn = new NhaCungCap(rs.getInt("manhacungcap")
                        , rs.getString("tencungcap"),rs.getString("diachi"),rs.getString("email"),rs.getString("ghichu"));
                    list.add(cnn);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }  
        }
        return list;
    }
    
    public boolean CapNhatNhaCungCap(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "update nhacungcap set manhacungcap = ?, tencungcap = ?,diachi = ?,email = ?, ghichu = ? WHERE manhacungcap = ?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, manhacungcap.getValue());
                ptm.setString(2, tencungcap.getValue());
                ptm.setString(3, diachi.getValue());
                ptm.setString(4, email.getValue());
                ptm.setString(5, ghichu.getValue());         
                ptm.setInt(6, manhacungcap.getValue());
                ptm.execute();  
            }
            catch(Exception e){
                e.printStackTrace();
                return false; 
            } 
        }
        return true; 
    }

    public NhaCungCap() {
    }
    
    public boolean ThemNhaCungCap(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "insert into nhacungcap(manhacungcap, tencungcap,diachi,email,ghichu)  values (?, ?, ?, ?, ?);";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, this.manhacungcap.getValue());
                ptm.setString(2, this.tencungcap.getValue());
                ptm.setString(3, this.diachi.getValue());
                ptm.setString(4, this.email.getValue());
                ptm.setString(5, this.ghichu.getValue());
                ptm.execute();  
            }
            catch(Exception e){
                e.printStackTrace();
                return false; 
            } 
        }
        return true; 
    }   
    
    public boolean XoaNhaCungCap(String tencungcap){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "delete from nhacungcap where tencungcap = ?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, tencungcap);
                ptm.execute();  
            }
            catch(Exception e){
                e.printStackTrace();
                return false; 
            } 
        }
        return true; 
    }   
    
    
     
}
