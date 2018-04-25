/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
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
public class SanPham extends RecursiveTreeObject<SanPham> {

    private StringProperty masanpham;
    private StringProperty tensanpham;
    private StringProperty tennhasanxuat;
    private StringProperty tennhomhang;
    private StringProperty ghichu;
    private IntegerProperty giaban;

    public SanPham(StringProperty masanpham) {
        this.masanpham = masanpham;
    }

    public SanPham(StringProperty masanpham, IntegerProperty giaban) {
        this.masanpham = masanpham;
        this.giaban = giaban;
    }

    public SanPham() {
    }

    public boolean isEmpty() {
        return (tensanpham.get().isEmpty()
                || tennhasanxuat.get().isEmpty() || tennhomhang.get().isEmpty());
    }

    public SanPham(StringProperty masanpham, StringProperty tensanpham, StringProperty tennhasanxuat, StringProperty tennhomhang, StringProperty ghichu, IntegerProperty giaban) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.tennhasanxuat = tennhasanxuat;
        this.tennhomhang = tennhomhang;
        this.ghichu = ghichu;
        this.giaban = giaban;
    }

    public SanPham(StringProperty tensanpham, StringProperty tennhasanxuat, StringProperty tennhomhang, StringProperty ghichu) {
        this.tensanpham = tensanpham;
        this.tennhasanxuat = tennhasanxuat;
        this.tennhomhang = tennhomhang;
        this.ghichu = ghichu;
    }

    public SanPham(StringProperty masanpham, StringProperty tensanpham, StringProperty tennhasanxuat, StringProperty tennhomhang, StringProperty ghichu) {
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

    public void setGiaban(IntegerProperty giaban) {
        this.giaban = giaban;
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

    public IntegerProperty getGiaban() {
        return giaban;
    }

    private String keyValueMasp() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String value = "";
        if (con != null) {
            try {

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select GenerateLicensePlate() as masanpham;");
                while (rs.next()) {
                    value = rs.getString("masanpham");
                }

            } catch (SQLException ex) {
                Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    public boolean insert() {

        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            masanpham.set(keyValueMasp());
            try {

                String query = "insert into sanpham(masanpham,tensanpham,tennhasanxuat,tennhomhang,ghichu)"
                        + " values(?,?,?,?,?)";
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

    public int delete() {
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
                    return 1;
                }

            } catch (MySQLIntegrityConstraintViolationException ex) {
                return 2;
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return 0;
    }

    public ObservableList<SanPham> getSPList() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<SanPham> spList = FXCollections.observableArrayList();

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

                    SanPham cus = new SanPham(masp, tensp, nhasanxuat, nhomhang, gchu);
                    spList.add(cus);

                }

            } catch (SQLException ex) {

            }

        }
        return spList;

    }

    public boolean updateGiaban() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query;
            query = "update sanpham set giaban= ? where masanpham = ?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setInt(1, giaban.get());
                ptm.setString(2, masanpham.get());
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
        sheet.setColumnWidth(4, 256 * 25);
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
                Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return wb;
    }
}
