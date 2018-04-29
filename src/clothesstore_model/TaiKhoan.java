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
public class TaiKhoan {
    private StringProperty tentaikhoan;
    private StringProperty matkhau;
    private IntegerProperty phanquyen;
    private IntegerProperty manhanvien;

    public TaiKhoan(StringProperty tentaikhoan, StringProperty matkhau, IntegerProperty phanquyen, IntegerProperty manhanvien) {
        this.tentaikhoan = tentaikhoan;
        this.matkhau = matkhau;
        this.phanquyen = phanquyen;
        this.manhanvien = manhanvien;
    }

    public TaiKhoan(String tentaikhoan, String matkhau, int phanquyen, int manhanvien ) {      
        this.tentaikhoan = new SimpleStringProperty(tentaikhoan);
        this.matkhau = new SimpleStringProperty(matkhau);
        this.phanquyen = new SimpleIntegerProperty(phanquyen);
        this.manhanvien = new SimpleIntegerProperty(manhanvien);
    }

    public TaiKhoan(String tentaikhoan, String matkhau) {      
        this.tentaikhoan = new SimpleStringProperty(tentaikhoan);
        this.matkhau = new SimpleStringProperty(matkhau);      
    }
    
    public TaiKhoan() {             
    }
     
    public boolean CheckLogin(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM dangnhap WHERE tentaikhoan = ? and matkhau = ?";   
        if(con!=null){
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, tentaikhoan.getValue());
                ptm.setString(2, matkhau.getValue());
                ResultSet rs = ptm.executeQuery();
                if(rs.next())
                {
                    ptm.close();
                    con.close();
                    return true;
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(TaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public int GetQuyenFromID(String id){
        int quyen = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT phanquyen FROM dangnhap WHERE tentaikhoan = ?";   
        if(con!=null){
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, id);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    quyen = rs.getInt("phanquyen");
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(TaiKhoan.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return quyen;
    }
    
    public ObservableList<TaiKhoan> getListTaiKhoan(){      
        ObservableList<TaiKhoan> list = FXCollections.observableArrayList(); 
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM dangnhap";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    TaiKhoan taikhoan = new TaiKhoan(rs.getString("tentaikhoan")
                            , rs.getString("matkhau")
                            , rs.getInt("phanquyen")
                            , rs.getInt("manhanvien"));
                    list.add(taikhoan);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }  
        }
        return list;
    }

    public boolean ThemTaiKhoan(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO dangnhap(tentaikhoan, matkhau, phanquyen, manhanvien) VALUES(?, ?, ?, ?);";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, tentaikhoan.getValue());
                ptm.setString(2, matkhau.getValue());
                ptm.setInt(3, phanquyen.getValue());
                ptm.setInt(4, manhanvien.getValue());
                ptm.execute();
                
                ptm.close();
                con.close();  
                
                return true;
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("hello");
            } 
        }
        return false; 
    }
    
    public boolean SuaTaiKhoan(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "UPDATE dangnhap set tentaikhoan =?, matkhau =? where manhanvien = ?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, tentaikhoan.getValue());
                ptm.setString(2, matkhau.getValue());
                ptm.setInt(3, manhanvien.getValue());
                
                ptm.execute();
                
                ptm.close();
                con.close();  
                
                return true;
            }
            catch(Exception e){
                e.printStackTrace();
            } 
        }
        return false; 
    }
    
    public boolean XoaTaiKhoan(String tentaikhoan){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "Delete from dangnhap where tentaikhoan = ?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, tentaikhoan);
                ptm.execute();
                
                ptm.close();
                con.close();  
                
                return true;
            }
            catch(Exception e){
                e.printStackTrace();
            } 
        }
        return false; 
    }
    
    public String getTentaikhoan() {
        return tentaikhoan.getValue();
    }

    public String getMatkhau() {
        return matkhau.getValue();
    }

    public Integer getPhanquyen() {
        return phanquyen.getValue();
    }

    public Integer getManhanvien() {
        return manhanvien.getValue();
    }
    

    public void setTentaikhoan(StringProperty tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public void setMatkhau(StringProperty matkhau) {
        this.matkhau = matkhau;
    }

    public void setPhanquyen(IntegerProperty phanquyen) {
        this.phanquyen = phanquyen;
    }

    public void setManhanvien(IntegerProperty manhanvien) {
        this.manhanvien = manhanvien;
    }
}
