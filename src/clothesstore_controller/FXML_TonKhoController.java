/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.TonKho;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_TonKhoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox<String> cmb_tonkho;
    @FXML
    private Label lb_vontonkho, lb_giatritonkho, lb_soluongtonkho;
    @FXML
    private TableView<String> table_view;
    @FXML
    private JFXButton btn_xuatbaocao;
    private int chucnang;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initCmb_tonkho();
        initLabel();
        viewListTable();
        btn_xuatbaocao.setOnAction(e -> {
            btn_xuatbaocao_click();
        });
    }

    private void btn_xuatbaocao_click() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF file", ".pdf")
        );
        File filepdf = fileChooser.showSaveDialog(null);
        try {
            OutputStream file = new FileOutputStream(filepdf);
            // set size cho page
            Rectangle pageSize = new Rectangle(216, 720);
            Document document = new Document(); // new Document(pageSize);
            // set size cho page
            document.setPageSize(PageSize.A4);
            PdfWriter.getInstance(document, file);
            document.open();
            BaseFont unicode_font = BaseFont.createFont("times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Paragraph paragraph;
            //title
            paragraph = new Paragraph("Báo cáo tồn kho", new Font(unicode_font, 18,
                    Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph.add(new Paragraph(" "));
            //Ngày in báo cáo
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            document.add(new Paragraph("" + dt.format(new Date())));

            //Create Paragraph
            paragraph = new Paragraph("Danh sách sản phẩm trong kho", new Font(unicode_font, 14,
                    Font.BOLD));
            paragraph.add(new Paragraph(" "));
            document.add(paragraph);

            //Create a table in PDF
            int cols = table_view.getColumns().size();
            
            PdfPTable pdfTable = new PdfPTable(cols);
            // add header
            for (int i = 0; i < cols; i++) {
                String header = table_view.getColumns().get(i).getText();
                PdfPCell cell1 = new PdfPCell(new Phrase(header));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfTable.addCell(cell1);
            }
            
            pdfTable.addCell("Row 1 Col 1");
            pdfTable.addCell("Row 1 Col 2");
            pdfTable.addCell("Row 1 Col 3");

            pdfTable.addCell("Row 2 Col 1");
            pdfTable.addCell("Row 2 Col 2");
            pdfTable.addCell("Row 2 Col 3");

            document.add(pdfTable);

            document.close();
            file.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void initLabel() {
        lb_vontonkho.setText(FormatTien(TonKho.getvontonkho()));
        lb_giatritonkho.setText(FormatTien(TonKho.getgiatritonkho()));
        lb_soluongtonkho.setText(TonKho.getsoluongtonkho().toString());
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }

    private void viewListTable() {
        if (!table_view.getColumns().isEmpty()) {
            table_view.getColumns().clear();
        }
        TonKho.loadtable(table_view, chucnang);
        table_view.getColumns().get(0).setText("Mã sản phẩm");
        table_view.getColumns().get(1).setText("Tên sản phẩm");
        table_view.getColumns().get(2).setText("Tồn kho tối thiểu");
        table_view.getColumns().get(3).setText("Số lượng");
        table_view.getColumns().get(4).setText("Tồn kho tối đa");
        if (chucnang == 7) {
            table_view.getColumns().get(5).setText("Ngày nhập");
            table_view.getColumns().get(6).setText("Ngày hết hạn");
        }
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void initCmb_tonkho() {
        List<String> list_tonkho = new ArrayList<>();
        list_tonkho.add("Tất cả");
        list_tonkho.add("Hàng tồn");
        list_tonkho.add("Chưa nhập vào kho");
        list_tonkho.add("Hết hàng");
        list_tonkho.add("Sắp hết hàng");
        list_tonkho.add("Vượt định mức");
        list_tonkho.add("Tồn kho lâu");
        cmb_tonkho.getItems().setAll(list_tonkho);
        cmb_tonkho.getSelectionModel().selectFirst();
        chucnang = cmb_tonkho.getSelectionModel().getSelectedIndex() + 1;
        cmb_tonkho.setOnAction(e -> {
            chucnang = cmb_tonkho.getSelectionModel().getSelectedIndex() + 1;
            viewListTable();
        });
    }
}
