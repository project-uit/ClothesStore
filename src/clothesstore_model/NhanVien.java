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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dieunguyen
 */
public class NhanVien {
    private IntegerProperty manhanvien;
    private StringProperty tennhanvien; 
    private StringProperty diachi;
    private IntegerProperty gioitinh; 
    private Date ngaysinh;
    private StringProperty cmnd;
    private IntegerProperty trangthai; 
    private IntegerProperty luong; 
    private TaiKhoan taikhoan;

    public NhanVien(){};
    
    public NhanVien(IntegerProperty manhanvien, StringProperty tennhanvien, StringProperty diachi, IntegerProperty gioitinh, 
            Date ngaysinh, StringProperty cmnd, IntegerProperty trangthai, IntegerProperty luong) {
        this.manhanvien = manhanvien;
        this.tennhanvien = tennhanvien;
        this.diachi = diachi;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.cmnd = cmnd;
        this.trangthai = trangthai;
        this.luong = luong;
    }

    public NhanVien(String tennhanvien, String diachi, int gioitinh, Date ngaysinh, String cmnd, 
            int trangthai, int luong ) {
        this.tennhanvien = new SimpleStringProperty(tennhanvien);
        this.diachi = new SimpleStringProperty(diachi);
        this.gioitinh = new SimpleIntegerProperty(gioitinh);
        this.ngaysinh = ngaysinh;
        this.cmnd = new SimpleStringProperty(cmnd);
        this.trangthai = new SimpleIntegerProperty(trangthai);
        this.luong = new SimpleIntegerProperty(luong); 
    }
    
    public NhanVien(int manhanvien, String tennhanvien, String diachi, int gioitinh, Date ngaysinh, String cmnd, 
            int trangthai, int luong ) {
        this.manhanvien = new SimpleIntegerProperty(manhanvien);
        this.tennhanvien = new SimpleStringProperty(tennhanvien);
        this.diachi = new SimpleStringProperty(diachi);
        this.gioitinh = new SimpleIntegerProperty(gioitinh);
        this.ngaysinh = ngaysinh;
        this.cmnd = new SimpleStringProperty(cmnd);
        this.trangthai = new SimpleIntegerProperty(trangthai);
        this.luong = new SimpleIntegerProperty(luong); 
    }
    
    public NhanVien(int manhanvien, String tennhanvien, String diachi, int gioitinh, Date ngaysinh, String cmnd, 
            int trangthai, int luong, TaiKhoan taikhoan ) {  
        this.manhanvien = new SimpleIntegerProperty(manhanvien);
        this.tennhanvien = new SimpleStringProperty(tennhanvien);
        this.diachi = new SimpleStringProperty(diachi);
        this.gioitinh = new SimpleIntegerProperty(gioitinh);
        this.ngaysinh = ngaysinh;
        this.cmnd = new SimpleStringProperty(cmnd);
        this.trangthai = new SimpleIntegerProperty(trangthai);
        this.luong = new SimpleIntegerProperty(luong); 
        this.taikhoan = taikhoan;
    }
    
    public boolean ThemNhanVien(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO nhanvien(tennhanvien, diachi, gioitinh, ngaysinh, cmnd, trangthai, luong) VALUES(?, ?, ?, ?, ?, ?, ?);";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, tennhanvien.getValue());
                ptm.setString(2, diachi.getValue());
                ptm.setInt(3, gioitinh.getValue());
                ptm.setDate(4, ngaysinh);
                ptm.setString(5, cmnd.getValue());
                ptm.setInt(6, trangthai.getValue());
                ptm.setInt(7, luong.getValue());
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
    
    public boolean SuaNhanVien(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "Update nhanvien set tennhanvien =?, diachi =?, gioitinh =?, ngaysinh =?, cmnd =?, trangthai =?, luong =? where manhanvien =?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, tennhanvien.getValue());
                ptm.setString(2, diachi.getValue());
                ptm.setInt(3, gioitinh.getValue());
                ptm.setDate(4, ngaysinh);
                ptm.setString(5, cmnd.getValue());
                ptm.setInt(6, trangthai.getValue());
                ptm.setInt(7, luong.getValue());
                ptm.setInt(8, manhanvien.getValue());
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
    
    public boolean XoaNhanVien(){
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "Delete from nhanvien where manhanvien = ?;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, manhanvien.getValue());
                ptm.execute();
                
                ptm.close();
                con.close();  
                
                return true; 
            }
            catch(Exception e){
            } 
        }
        return false; 
    }
    
    public ObservableList<NhanVien> getListNhanVien(){      
        ObservableList<NhanVien> list = FXCollections.observableArrayList(); 
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM nhanvien, dangnhap where nhanvien.manhanvien = dangnhap.manhanvien";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    TaiKhoan taikhoan = new TaiKhoan(rs.getString("tentaikhoan")
                            , rs.getString("matkhau")
                            , rs.getInt("phanquyen")
                            , rs.getInt("manhanvien"));
                    
                    NhanVien nhanvien = new NhanVien(rs.getInt("manhanvien")
                            , rs.getString("tennhanvien")
                            , rs.getString("diachi")
                            , rs.getInt("gioitinh")
                            , rs.getDate("ngaysinh")
                            , rs.getString("cmnd")
                            , rs.getInt("trangthai")
                            , rs.getInt("luong")
                            , taikhoan);
                    list.add(nhanvien);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }  
        }
        return list;
    }
    
    public ObservableList<NhanVien> TrangThaiFilter(int status){      
        ObservableList<NhanVien> list = FXCollections.observableArrayList(); 
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM nhanvien, dangnhap where nhanvien.manhanvien = dangnhap.manhanvien and trangthai = "+status+"";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    TaiKhoan taikhoan = new TaiKhoan(rs.getString("tentaikhoan")
                            , rs.getString("matkhau")
                            , rs.getInt("phanquyen")
                            , rs.getInt("manhanvien"));
                    
                    NhanVien nhanvien = new NhanVien(rs.getInt("manhanvien")
                            , rs.getString("tennhanvien")
                            , rs.getString("diachi")
                            , rs.getInt("gioitinh")
                            , rs.getDate("ngaysinh")
                            , rs.getString("cmnd")
                            , rs.getInt("trangthai")
                            , rs.getInt("luong")
                            , taikhoan);
                    list.add(nhanvien);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }  
        }
        return list;
    }
    
    public int getLastId(){      
        int id = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT manhanvien FROM nhanvien ORDER BY manhanvien DESC LIMIT 1;";
        if(con!=null){
            try{
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    id = rs.getInt("manhanvien");
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }  
        }
        return id;
    }
    
    public TaiKhoan getTaiKhoan() {
        return taikhoan;
    }
     
    public Integer getManhanvien() {
        return manhanvien.getValue();
    }

    public String getTennhanvien() {
        return tennhanvien.getValue();
    }

    public String getDiachi() {
        return diachi.getValue();
    }

    public Integer getGioitinh() {
        return gioitinh.getValue();
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

   
    public String getCmnd() {
        return cmnd.getValue();
    }

    public int getTrangthai() {
        return trangthai.getValue();
    }

    public int getLuong() {
        return luong.getValue();
    }

    public void setManhanvien(IntegerProperty manhanvien) {
        this.manhanvien = manhanvien;
    }

    public void setTennhanvien(StringProperty tennhanvien) {
        this.tennhanvien = tennhanvien;
    }

    public void setDiachi(StringProperty diachi) {
        this.diachi = diachi;
    }

    public void setGioitinh(IntegerProperty gioitinh) {
        this.gioitinh = gioitinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public void setCmnd(StringProperty cmnd) {
        this.cmnd = cmnd;
    }

    public void setTrangthai(IntegerProperty trangthai) {
        this.trangthai = trangthai;
    }

    public void setLuong(IntegerProperty luong) {
        this.luong = luong;
    }
    
   
}
