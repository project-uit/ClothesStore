/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author quochung
 */
public class ThongKe {
      public static XSSFWorkbook export_thongkesp_banchay_trongquy(int quy, int nam) {

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Danh sách 5 sản phẩm bán chạy");
        XSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("Mã sản phẩm");
        header.createCell(1).setCellValue("Tên sản phẩm");
        header.createCell(2).setCellValue("Số lượng đã bán");       
        header.createCell(3).setCellValue("Doanh thu");    
        header.createCell(4).setCellValue("Tỷ lệ số lượng");
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.setZoom(120);

        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        int index = 1;
        if (con != null) {
            try {
                String call = "{call soluongban_theoquy(?,?)}";                
                CallableStatement stmt = con.prepareCall(call);
                stmt.setInt(1, quy);
                stmt.setInt(2, nam);
                ResultSet rs = stmt.executeQuery();
                Integer tongsl =tongsp_daban(quy,nam);
                while (rs.next()) {
                    XSSFRow row = sheet.createRow(index);
                    row.createCell(0).setCellValue(rs.getString(1));
                    row.createCell(1).setCellValue(rs.getString(2));
                    row.createCell(2).setCellValue(rs.getInt(3));
                    row.createCell(3).setCellValue(rs.getInt(4)); 
                    row.createCell(4).setCellValue(((float)rs.getInt(3)/tongsl*100)); 
                    index++;
                }
                XSSFRow row = sheet.createRow(index);
                row.createCell(1).setCellValue("Tổng số lượng sản phẩm đã bán");
                row.createCell(2).setCellValue(tongsl);
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return wb;
    }
    public static HashMap<String, Integer> thongke_sp_banchay(int quy, int nam) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String call = "{call soluongban_theoquy(?,?)}";
        HashMap<String, Integer> value = new HashMap<>();
        if (con != null) {
            try (CallableStatement stmt = con.prepareCall(call)) {

                stmt.setInt(1, quy);
                stmt.setInt(2, nam);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    value.put(rs.getString(1), rs.getInt(3));
                }

                stmt.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    public static Integer tongsp_daban(int quy, int nam) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        Integer tongsl = 0;
        if (con != null) {
            try {
                String query = "select getTongsoluong_quy(?,?)";
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, quy);
                ptm.setInt(2, nam);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    tongsl = rs.getInt(1);
                }
            } catch (SQLException ex) {

            }
        }
        return tongsl;
    }
}
