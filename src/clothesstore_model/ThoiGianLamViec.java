/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dieunguyen
 */
public class ThoiGianLamViec {
    private StringProperty tenca;
    private StringProperty giolam;   

    public ThoiGianLamViec(StringProperty tenca, StringProperty giolam) {
        this.tenca = tenca;
        this.giolam = giolam;
    }
    
    public ThoiGianLamViec(String tenca, String giolam) {      
        this.tenca = new SimpleStringProperty(tenca);
        this.giolam = new SimpleStringProperty(giolam);      
    }
    
    public ThoiGianLamViec() {             
    }
    
    public ObservableList<ThoiGianLamViec> getTableThoiGianLamViec(){      
        ObservableList<ThoiGianLamViec> list = FXCollections.observableArrayList(); 
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM thoigianlamviec";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    ThoiGianLamViec time = new ThoiGianLamViec(rs.getString("tenca")
                        , rs.getString("giolam"));
                    list.add(time);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }  
        }
        return list;
    }
    
    public boolean CapNhatCa(String tenca){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "update thoigianlamviec set tenca = ?, giolam = ? WHERE tenca = ?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, this.tenca.getValue());
                ptm.setString(2, this.giolam.getValue());
                ptm.setString(3, tenca);
                ptm.execute();  
            }
            catch(Exception e){
                e.printStackTrace();
                return false; 
            } 
        }
        return true; 
    }
    
    public boolean ThemCa(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "insert into thoigianlamviec(tenca, giolam)  values (?, ?);";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, this.tenca.getValue());
                ptm.setString(2, this.giolam.getValue());
                ptm.execute();  
            }
            catch(Exception e){
                e.printStackTrace();
                return false; 
            } 
        }
        return true; 
    }   
    
    public boolean XoaCa(String tenca){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "delete from thoigianlamviec where tenca = ?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, tenca);
                ptm.execute();  
            }
            catch(Exception e){
                e.printStackTrace();
                return false; 
            } 
        }
        return true; 
    }   
    
    public String getTenca() {
        return tenca.getValue();
    }

    public String getGiolam() {
        return giolam.getValue();
    }

    public void setTenca(StringProperty tenca) {
        this.tenca = tenca;
    }

    public void setGiolam(StringProperty giolam) {
        this.giolam = giolam;
    }
}

