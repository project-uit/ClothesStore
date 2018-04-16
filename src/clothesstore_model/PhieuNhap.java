/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 15520
 */
public class PhieuNhap {
    private IntegerProperty maphieunhap;
    private IntegerProperty tongtien; 
    private IntegerProperty manhanvien;
    private IntegerProperty manhacungcap;
    private Date ngaynhap;

    public PhieuNhap(int maphieunhap, int tongtien, int manhanvien, int manhacungcap, Date ngaynhap) {
        this.maphieunhap =new SimpleIntegerProperty (maphieunhap);
        this.tongtien = new SimpleIntegerProperty(tongtien);
        this.manhanvien = new SimpleIntegerProperty (manhanvien);
        this.manhacungcap =new SimpleIntegerProperty (manhacungcap);
        this.ngaynhap = ngaynhap;
    }

    public PhieuNhap( int manhanvien, int manhacungcap, Date ngaynhap,int tongtien) {
        
        this.tongtien =new SimpleIntegerProperty (tongtien);
        this.manhanvien =new SimpleIntegerProperty ( manhanvien);
        this.manhacungcap =new SimpleIntegerProperty (manhacungcap);
        this.ngaynhap = ngaynhap;
    }
    
    public PhieuNhap(int maphieunhap, int manhanvien, int manhacungcap, Date ngaynhap) {
        this.maphieunhap =new SimpleIntegerProperty (maphieunhap);
        this.manhanvien =new SimpleIntegerProperty ( manhanvien);
        this.manhacungcap =new SimpleIntegerProperty (manhacungcap);
        this.ngaynhap = ngaynhap;
    }

    public PhieuNhap() {
    }

    public int getMaphieunhap() {
        return maphieunhap.getValue();
    }

    public Integer getTongtien() {
        return tongtien.getValue();
    }

    public Integer getManhanvien() {
        return manhanvien.getValue();
    }

    public int getManhacungcap() {
        return manhacungcap.getValue();
    }

    public Date getNgaynhap() {
        return ngaynhap;
    }

    public void setMaphieunhap(IntegerProperty maphieunhap) {
        this.maphieunhap = maphieunhap;
    }

    public void setTongtien(IntegerProperty tongtien) {
        this.tongtien = tongtien;
    }

    public void setMaNhanVien(IntegerProperty tennhanvien) {
        this.manhanvien = tennhanvien;
    }

    public void setManhacungcap(IntegerProperty manhacungcap) {
        this.manhacungcap = manhacungcap;
    }

    public void setNgaynhap(Date ngaynhap) {
        this.ngaynhap = ngaynhap;
    }
    
    public ObservableList<PhieuNhap> getListPhieuNhap(){      
        ObservableList<PhieuNhap> list = FXCollections.observableArrayList(); 
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM phieunhap";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    
                    
                    PhieuNhap phieunhap = new PhieuNhap(rs.getInt("maphieunhap")
                            , rs.getInt("tongtien")
                            , rs.getInt("manhanvien")
                            , rs.getInt("manhacungcap")
                            , rs.getDate("ngaynhap"));
                         
                    list.add(phieunhap);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }  
        }
        return list;
    }
    
    public boolean ThemPhieuNhap(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO phieunhap( manhanvien, manhacungcap, ngaynhap,tongtien) VALUES( ?, ?, ?,?);";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, manhanvien.getValue());
                ptm.setInt(2, manhacungcap.getValue());
                ptm.setDate(3, ngaynhap); 
                ptm.setInt(4, tongtien.getValue());
                ptm.execute();  
            }
            catch(Exception e){
                e.printStackTrace();
                return false; 
            } 
        }
        return true; 
    }
    
    public boolean CapNhatPhieuNhap(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "update phieunhap set  manhacungcap = ?,manhanvien = ?,ngaynhap = ?  WHERE maphieunhap = ?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, this.manhacungcap.getValue());
                ptm.setInt(2,this.manhanvien.getValue());
                ptm.setDate(3, this.ngaynhap);         
                ptm.setInt(4, maphieunhap.getValue());
                ptm.execute();  
            }
            catch(Exception e){
                e.printStackTrace();
                return false; 
            } 
        }
        return true; 
    }
    
    public boolean XoaPhieuNhap(int maphieunhap){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = " delete from phieunhap where maphieunhap =?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, maphieunhap);
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
