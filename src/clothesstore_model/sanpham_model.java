/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author quochung
 */
public class sanpham_model extends RecursiveTreeObject<sanpham_model> {

    private StringProperty masanpham;
    private StringProperty tensanpham;
    private StringProperty tennhasanxuat;
    private StringProperty tennhomhang;
    private StringProperty ghichu;

    public sanpham_model(StringProperty masanpham) {
        this.masanpham = masanpham;
    }

    public sanpham_model() {
    }

    public sanpham_model(StringProperty tensanpham, StringProperty tennhasanxuat, StringProperty tennhomhang, StringProperty ghichu) {
        this.tensanpham = tensanpham;
        this.tennhasanxuat = tennhasanxuat;
        this.tennhomhang = tennhomhang;
        this.ghichu = ghichu;
    }

    public sanpham_model(StringProperty masanpham, StringProperty tensanpham, StringProperty tennhasanxuat, StringProperty tennhomhang, StringProperty ghichu) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.tennhasanxuat = tennhasanxuat;
        this.tennhomhang = tennhomhang;
        this.ghichu = ghichu;
    }

    public void setMasanpham(StringProperty masanpham) {
        this.masanpham = masanpham;
    }

    public void setTensanpham(StringProperty tensanpham) {
        this.tensanpham = tensanpham;
    }

    public void setTennhasanxuat(StringProperty tennhasanxuat) {
        this.tennhasanxuat = tennhasanxuat;
    }

    public void setTennhomhang(StringProperty tennhomhang) {
        this.tennhomhang = tennhomhang;
    }

    public void setGhichu(StringProperty ghichu) {
        this.ghichu = ghichu;
    }

    public StringProperty getMasanpham() {
        return masanpham;
    }

    public StringProperty getTensanpham() {
        return tensanpham;
    }

    public StringProperty getTennhasanxuat() {
        return tennhasanxuat;
    }

    public StringProperty getTennhomhang() {
        return tennhomhang;
    }

    public StringProperty getGhichu() {
        return ghichu;
    }

    public boolean insert() {

        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {

            String query = "insert into sanpham values(?,?,?,?,?)";

            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1, masanpham.get());
                ptm.setString(2, tensanpham.get());
                ptm.setString(3, tennhasanxuat.get());
                ptm.setString(4, tennhomhang.get());
                ptm.setString(5, ghichu.get());
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

            String query = "delete from sanpham where masanpham = ?";

            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1, masanpham.get());

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

    public ObservableList<sanpham_model> getSPList() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<sanpham_model> spList = FXCollections.observableArrayList();

        if (con != null) {
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery("select * from sanpham");) {
                while (rs.next()) {
                    StringProperty masp = new SimpleStringProperty(rs.getString("masanpham"));
                    StringProperty tensp = new SimpleStringProperty(rs.getString("tensanpham"));

                    StringProperty nhasanxuat = new SimpleStringProperty(rs.getString("tennhasanxuat"));
                    StringProperty nhomhang = new SimpleStringProperty(rs.getString("tennhomhang"));
                    StringProperty gchu = new SimpleStringProperty(rs.getString("ghichu"));

                    sanpham_model cus = new sanpham_model(masp, tensp, nhasanxuat, nhomhang, gchu);
                    spList.add(cus);

                }

            } catch (SQLException ex) {

            }

        }
        return spList;

    }

    public boolean update() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query;
            query = "update sanpham set tensanpham=?, tennhasanxuat=?,tennhomhang=?,ghichu=? where masanpham = ?";

            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1, tensanpham.get());
                ptm.setString(2, tennhasanxuat.get());
                ptm.setString(3, tennhomhang.get());
                ptm.setString(4, ghichu.get());
                ptm.setString(5, masanpham.get());
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

    public XSSFWorkbook exportExcel() {

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Danh sách sản phẩm");
        XSSFRow header = sheet.createRow(0);
        
        header.createCell(0).setCellValue("Mã sản phẩm");
        header.createCell(1).setCellValue("Tên sản phẩm");
        header.createCell(2).setCellValue("Tên nhà sản xuất");
        header.createCell(3).setCellValue("Tên nhóm hàng");
        header.createCell(4).setCellValue("ghi chú");
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.setColumnWidth(4,256*25);
        sheet.setZoom(120);
        
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        int index = 1;
        if (con != null) {
            try {
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from sanpham");
                while (rs.next()) {
                    XSSFRow row = sheet.createRow(index);
                    row.createCell(0).setCellValue(rs.getString("masanpham"));
                    row.createCell(1).setCellValue(rs.getString("tensanpham"));
                    row.createCell(2).setCellValue(rs.getString("tennhasanxuat"));
                    row.createCell(3).setCellValue(rs.getString("tennhomhang"));
                    row.createCell(4).setCellValue(rs.getString("ghichu"));
                    index++;
                }
              

                stmnt.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(sanpham_model.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return wb;
    }
}
