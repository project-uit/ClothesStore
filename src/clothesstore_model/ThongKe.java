/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author quochung
 */
public class ThongKe {

    public static void pdf_thongkesp_banchay_trongquy(Chart chart, String title, int quy, int nam) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF file", ".pdf")
        );

        File filepdf = fileChooser.showSaveDialog(null);
        if (filepdf != null) {
            try {
                OutputStream file = new FileOutputStream(filepdf);
                // set size cho page
                Rectangle pageSize = new Rectangle(216, 720);
                Document document = new Document(); // new Document(pageSize);
                // set size cho page
                document.setPageSize(PageSize.A4);

                PdfWriter.getInstance(document, file);
                document.open();
                BaseFont unicode_font = BaseFont.createFont("times.ttf",
                        BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font font = new Font(unicode_font, 14, Font.NORMAL);
                Paragraph paragraph;
                //title
                paragraph = new Paragraph(title, new Font(unicode_font, 18, Font.BOLD));
                paragraph.setAlignment(Element.ALIGN_CENTER);

                document.add(paragraph);
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                //Ngày in báo cáo
                paragraph = new Paragraph("Ngày: " + dt.format(new Date()));
                paragraph.setSpacingAfter((float) 1.5);
                document.add(paragraph);
                //add biểu đồ
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                WritableImage image = chart.snapshot(snapshotParameters, null);
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteOutput);
                Image img = Image.getInstance(byteOutput.toByteArray());
                img.scaleToFit(500, 500);
                document.add(img);

                //Create Paragraph
                document.add(new Paragraph(" "));
                paragraph = new Paragraph("Danh sách sản phẩm ", new Font(unicode_font, 14,
                        Font.BOLD));
                paragraph.add(new Paragraph(" "));
                document.add(paragraph);
                //Create a table in PDF
                PdfPTable pdfTable = new PdfPTable(5); // 5 cột
                // khởi tạo cột
                PdfPCell cell1 = new PdfPCell(new Phrase("Mã sản phẩm", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);

                cell1 = new PdfPCell(new Phrase("Tên sản phẩm", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);

                cell1 = new PdfPCell(new Phrase("Số lượng đã bán", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);

                cell1 = new PdfPCell(new Phrase("Doanh thu", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);

                cell1 = new PdfPCell(new Phrase("Tỷ lệ số lượng (%)", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);

                pdfTable.setHeaderRows(1);

                DBConnection db = new DBConnection();
                Connection con = db.getConnecttion();
                Integer tongsl = tongsp_daban(quy, nam);
                if (con != null) {
                    try {
                        String call = "{call soluongban_theoquy(?,?)}";
                        CallableStatement stmt = con.prepareCall(call);
                        stmt.setInt(1, quy);
                        stmt.setInt(2, nam);
                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {
                            pdfTable.addCell(rs.getString(1));
                            pdfTable.addCell(rs.getString(2));
                            pdfTable.addCell(rs.getString(3));
                            String tien = String.format("%,8d%n", rs.getInt(4)).trim();
                            pdfTable.addCell(tien);
                            pdfTable.addCell(((float) rs.getInt(3) / tongsl * 100) + "");
                        }
                        stmt.close();
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SanPham.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                document.add(pdfTable);
                document.add(new Paragraph(" "));
                document.add(new Paragraph("Tổng số lượng sản phẩm đã bán: " + tongsl, font));
                document.close();
                file.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    public static void pdf_thongke_doanhthu(Chart chart, String title, int nam) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF file", ".pdf")
        );

        File filepdf = fileChooser.showSaveDialog(null);
        if (filepdf != null) {
            try {
                OutputStream file = new FileOutputStream(filepdf);
                // set size cho page
                Rectangle pageSize = new Rectangle(216, 720);
                Document document = new Document(); // new Document(pageSize);
                // set size cho page
                document.setPageSize(PageSize.A4);

                PdfWriter.getInstance(document, file);
                document.open();
                BaseFont unicode_font = BaseFont.createFont("times.ttf",
                        BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font font = new Font(unicode_font, 14, Font.NORMAL);
                Paragraph paragraph;
                //title
                paragraph = new Paragraph(title, new Font(unicode_font, 18, Font.BOLD));
                paragraph.setAlignment(Element.ALIGN_CENTER);

                document.add(paragraph);
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                //Ngày in báo cáo
                paragraph = new Paragraph("Ngày: " + dt.format(new Date()));
                document.add(paragraph);
                //add biểu đồ
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                WritableImage image = chart.snapshot(snapshotParameters, null);
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteOutput);
                Image img = Image.getInstance(byteOutput.toByteArray());
                img.scaleToFit(500, 500);
                document.add(img);

                //Create Paragraph
                document.add(new Paragraph(" "));
                paragraph = new Paragraph("Doanh thu từng tháng trong năm " + nam,
                        new Font(unicode_font, 14,
                                Font.BOLD));
                paragraph.add(new Paragraph(" "));
                document.add(paragraph);
                //Create a table in PDF
                PdfPTable pdfTable = new PdfPTable(2); // 5 cột
                // khởi tạo cột
                PdfPCell cell1 = new PdfPCell(new Phrase("Tháng", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);

                cell1 = new PdfPCell(new Phrase("Doanh thu", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);
                pdfTable.setHeaderRows(1);
                HoaDon hd = new HoaDon();
                List<Integer> doanhthu12months = hd.getDoanhThu12months(nam);
                int i = 1;
                int tong=0;
                for (Integer temp : doanhthu12months) {
                    pdfTable.addCell("" + i);
                    String tien = String.format("%,8d%n", temp).trim();
                    tong+=temp;
                    pdfTable.addCell(tien);
                    i++;
                }

                document.add(pdfTable);
                paragraph = new Paragraph("Tổng doanh thu: "+String.format("%,8d%n", tong).trim(), new Font(unicode_font, 18, Font.BOLD));
                document.add(paragraph);
                document.close();
                file.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    public static void pdf_thongke_nhomhang(Chart chart, String title, Integer nam, Integer thang) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF file", ".pdf")
        );

        File filepdf = fileChooser.showSaveDialog(null);
        if (filepdf != null) {
            try {
                OutputStream file = new FileOutputStream(filepdf);
                // set size cho page
                Rectangle pageSize = new Rectangle(216, 720);
                Document document = new Document(); // new Document(pageSize);
                // set size cho page
                document.setPageSize(PageSize.A4);

                PdfWriter.getInstance(document, file);
                document.open();
                BaseFont unicode_font = BaseFont.createFont("times.ttf",
                        BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font font = new Font(unicode_font, 14, Font.NORMAL);
                Paragraph paragraph;
                //title
                paragraph = new Paragraph(title, new Font(unicode_font, 18, Font.BOLD));
                paragraph.setAlignment(Element.ALIGN_CENTER);

                document.add(paragraph);
                SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                //Ngày in báo cáo
                paragraph = new Paragraph("Ngày: " + dt.format(new Date()));
                document.add(paragraph);
                //add biểu đồ
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                WritableImage image = chart.snapshot(snapshotParameters, null);
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteOutput);
                Image img = Image.getInstance(byteOutput.toByteArray());
                img.scaleToFit(500, 500);
                document.add(img);

                //Create Paragraph
                document.add(new Paragraph(" "));
                paragraph = new Paragraph("Danh sách nhóm hàng ", new Font(unicode_font, 14,
                        Font.BOLD));
                paragraph.add(new Paragraph(" "));
                document.add(paragraph);
                //Create a table in PDF
                PdfPTable pdfTable = new PdfPTable(2); // 5 cột
                // khởi tạo cột
                PdfPCell cell1 = new PdfPCell(new Phrase("Tên nhóm hàng", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);
                cell1 = new PdfPCell(new Phrase("Số lượng", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);
                pdfTable.setHeaderRows(1);
                HashMap<String, Integer> hm_sp;
                if (thang == 0) {
                    hm_sp = thongke_nhomhang_year(nam);
                } else {
                    hm_sp = thongke_nhomhang_month(thang, nam);
                }
                Set set = hm_sp.entrySet();
                Iterator i = set.iterator();
                while (i.hasNext()) {
                    Map.Entry<String, Integer> me = (Map.Entry) i.next();
                    pdfTable.addCell(me.getKey());
                    pdfTable.addCell(me.getValue() + "");
                }

                document.add(pdfTable);
                document.close();
                file.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
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

    public static HashMap<String, Integer> thongke_nhomhang_month(int thang, int nam) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String call = "select tennhomhang, sum(soluongmua) as tong "
                + "from sanpham sp, chitiethoadon cthd, chitietsanpham ctsp, hoadon hd "
                + "where cthd.machitietsanpham=ctsp.machitietsanpham and sp.masanpham = ctsp.masanpham "
                + "and cthd.mahoadon = hd.mahoadon  and MONTH(hd.ngayban) = ? "
                + "and YEAR(hd.ngayban)= ? "
                + "group by tennhomhang";
        HashMap<String, Integer> value = new HashMap<>();
        if (con != null) {
            try (CallableStatement stmt = con.prepareCall(call)) {

                stmt.setInt(1, thang);
                stmt.setInt(2, nam);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    value.put(rs.getString(1), rs.getInt(2));
                }
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    public static HashMap<String, Integer> thongke_nhomhang_year(int nam) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String call = "select tennhomhang, sum(soluongmua) as tong "
                + "from sanpham sp, chitiethoadon cthd, chitietsanpham ctsp, hoadon hd "
                + "where cthd.machitietsanpham=ctsp.machitietsanpham "
                + "and sp.masanpham = ctsp.masanpham and cthd.mahoadon = hd.mahoadon  "
                + "and YEAR(hd.ngayban)= ? "
                + "group by tennhomhang";
        HashMap<String, Integer> value = new HashMap<>();
        if (con != null) {
            try (CallableStatement stmt = con.prepareCall(call)) {
                stmt.setInt(1, nam);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    value.put(rs.getString(1), rs.getInt(2));
                }
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }
}
