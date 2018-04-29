/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dieunguyen
 */
public class KhoSanPham {
    private IntegerProperty makhosanpham;
    private IntegerProperty manhanvien;
    private IntegerProperty maphieunhap;

    public KhoSanPham(){}
            
    public KhoSanPham(IntegerProperty manhanvien, IntegerProperty maphieunhap) {
        this.manhanvien = manhanvien;
        this.maphieunhap = maphieunhap;
    }

    public KhoSanPham(int manhanvien, int maphieunhap) {      
        this.manhanvien = new SimpleIntegerProperty(manhanvien);
        this.maphieunhap = new SimpleIntegerProperty(maphieunhap);
    }
    
    public boolean ThemKhoSanPham(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO khosanpham(manhanvien, maphieunhap) VALUES(?, ?);";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, manhanvien.getValue());
                ptm.setInt(2, maphieunhap.getValue());
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
    
    public List getListMaPNChuaNhapKho(){      
        List list = new ArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT pn.maphieunhap FROM phieunhap pn "
                + "LEFT JOIN khosanpham ksp ON pn.maphieunhap = ksp.maphieunhap "
                + "where ksp.makhosanpham is null";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    list.add(rs.getInt("maphieunhap"));
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }  
        }
        return list;
    }
    
    public ObservableList<KhoSanPham> getListKhoSanPham(){      
        ObservableList<KhoSanPham> list = FXCollections.observableArrayList(); 
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM khosanpham";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    KhoSanPham ksp = new KhoSanPham(rs.getInt("manhanvien")
                            , rs.getInt("maphieunhap")
                            );
                    list.add(ksp);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }  
        }
        return list;
    }
    
    public void setMakhosanpham(IntegerProperty makhosanpham) {
        this.makhosanpham = makhosanpham;
    }

    public void setManhanvien(IntegerProperty manhanvien) {
        this.manhanvien = manhanvien;
    }

    public void setMaphieunhap(IntegerProperty maphieunhap) {
        this.maphieunhap = maphieunhap;
    }

    public Integer getMakhosanpham() {
        return makhosanpham.getValue();
    }

    public Integer getManhanvien() {
        return manhanvien.getValue();
    }

    public Integer getMaphieunhap() {
        return maphieunhap.getValue();
    }
    
}
