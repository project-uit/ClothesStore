/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_ClothesStoreController.rootP;
import static clothesstore_controller.FXML_DoiTraController.mahd;
import clothesstore_model.ChiTietDoiTra;
import clothesstore_model.ChiTietHoaDon;
import clothesstore_model.ChiTietHoaDonDoiTra;
import clothesstore_model.ChiTietSanPham;
import clothesstore_model.DoiTra;
import clothesstore_model.HoaDonDoiTra;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_ChiTietDoiTraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableColumn clMaCTSP, clTenSP, clGiaBan, clSoLuong, clThanhTien,
            _clMaCTSP, _clTenSP, _clGiaBan, _clSoLuong, _clThanhTien;
    @FXML
    private TableView<ChiTietDoiTra> tblHangDoiTra;
    @FXML
    private TableView<ChiTietHoaDonDoiTra> tblHangThayThe;
    @FXML
    private JFXButton btnLuu, btnAdd, btnPrint;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lb1, lb2, lb3;
    private DoiTra doitra;
    private int total1 = 0, total2 = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initTableHangDoiTra(mahd);
        initTableHangThayThe();
        initTextSearch();
    }

    public void setDT(DoiTra dt) {
        doitra = dt;
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }

    public void initTextSearch() {
        List<String> listCTSP = new ChiTietSanPham().getListMactsp();
        TextFields.bindAutoCompletion(txtSearch, listCTSP);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue.length() != 0) {
                    for (ChiTietHoaDonDoiTra item : tblHangThayThe.getItems()) {
                        if (item.getMachitietsanpham().get().equals(newValue)) {
                            btnAdd.setDisable(true);
                        } else {
                            btnAdd.setDisable(false);
                        }
                    }
                }
            }
        });
    }

    public void initTableHangDoiTra(int mahd) {
        clMaCTSP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietDoiTra, String> p) {
                return p.getValue().getMachitietsanpham();
            }
        });
        clTenSP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietDoiTra, String> p) {
                return p.getValue().getTensanpham();
            }
        });
        clGiaBan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getGiaban().get()));
            }
        });
        clSoLuong.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getSoluongmua().get());
            }
        });
        tblHangDoiTra.setRowFactory(tv -> {
            TableRow<ChiTietDoiTra> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    int max = new ChiTietDoiTra().getMAXctsp(mahd, row.getItem().getMachitietsanpham().get());
                    TextInputDialog dialog = new TextInputDialog("0");
                    dialog.setTitle(null);
                    dialog.setHeaderText("Nhập tối đa " + max + " sản phẩm");
                    dialog.setContentText("Số lượng: ");
                    Optional<String> result = dialog.showAndWait();

                    result.ifPresent(name -> {
                        try {
                            int sl = Integer.valueOf(name);

                            if (sl > max) {
                                sl = 0;
                                TrayNotification tray = new TrayNotification("Thông báo",
                                        "Số lượng nhập không phù hợp", NotificationType.ERROR);
                                tray.showAndDismiss(Duration.seconds(2));
                            }
                            row.getItem().setSoluongmua(new SimpleIntegerProperty(sl));
                            row.getItem().setThanhtien(new SimpleIntegerProperty(sl * row.getItem().getGiaban().get()));
                            total1 = 0;
                            for (ChiTietDoiTra item : tblHangDoiTra.getItems()) {
                                total1 += item.getThanhtien().get();
                            }
                            lb1.setText("Tổng tiền hàng đổi trả:    " + FormatTien(total1));
                            lb3.setText("Thành tiền : " + FormatTien((total2 - total1)));
                        } catch (NumberFormatException ex) {
                            row.getItem().setSoluongmua(new SimpleIntegerProperty(0));
                            row.getItem().setThanhtien(new SimpleIntegerProperty(0));
                            TrayNotification tray = new TrayNotification("Thông báo",
                                    "Số lượng nhập không phù hợp", NotificationType.ERROR);
                            tray.showAndDismiss(Duration.seconds(2));
                        }
                        tblHangDoiTra.refresh();
                    });
                }
            });
            return row;
        });
        clThanhTien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getThanhtien().get()));
            }
        });
        ChiTietDoiTra cthd = new ChiTietDoiTra();
        ObservableList<ChiTietDoiTra> list = observableArrayList();
        list = cthd.getCTHDfromMaHD(mahd);
        tblHangDoiTra.setItems(list);
        for (ChiTietDoiTra item : tblHangDoiTra.getItems()) {
            item.setSoluongmua(new SimpleIntegerProperty(0));
            item.setThanhtien(new SimpleIntegerProperty(0));
        }
        tblHangDoiTra.refresh();
    }

    public void initTableHangThayThe() {
        _clMaCTSP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String> p) {
                return p.getValue().getMachitietsanpham();
            }
        });
        _clTenSP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String> p) {
                return p.getValue().getTensanpham();
            }
        });
        _clGiaBan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getGiaban().get()));
            }
        });
        _clSoLuong.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getSoluongmua().get());
            }
        });
        tblHangThayThe.setRowFactory(tv -> {
            TableRow<ChiTietHoaDonDoiTra> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    int max = new ChiTietSanPham().getSoLuongFromMaCTSP(row.getItem().getMachitietsanpham().get());
                    TextInputDialog dialog = new TextInputDialog("0");
                    dialog.setTitle(null);
                    dialog.setHeaderText("Nhập tối đa " + max + " sản phẩm");
                    dialog.setContentText("Số lượng: ");

                    Optional<String> result = dialog.showAndWait();

                    result.ifPresent(name -> {
                        try {
                            int sl = Integer.valueOf(name);

                            if (sl > max) {
                                sl = 0;
                                TrayNotification tray = new TrayNotification("Thông báo",
                                        "Số lượng nhập không phù hợp", NotificationType.ERROR);
                                tray.showAndDismiss(Duration.seconds(2));
                            }

                            row.getItem().setSoluongmua(new SimpleIntegerProperty(sl));
                            row.getItem().setThanhtien(new SimpleIntegerProperty(sl * row.getItem().getGiaban().get()));

                            total2 = 0;
                            for (ChiTietHoaDonDoiTra item : tblHangThayThe.getItems()) {
                                total2 += item.getThanhtien().get();
                            }
                            lb2.setText("Tổng tiền hàng thay thế:   " + FormatTien(total2));
                            lb3.setText("Thành tiền:                " + FormatTien((total2 - total1)));
                        } catch (NumberFormatException ex) {
                            row.getItem().setSoluongmua(new SimpleIntegerProperty(0));
                            row.getItem().setThanhtien(new SimpleIntegerProperty(0));
                            TrayNotification tray = new TrayNotification("Thông báo",
                                    "Số lượng nhập không phù hợp", NotificationType.ERROR);
                            tray.showAndDismiss(Duration.seconds(2));
                        }
                        tblHangThayThe.refresh();
                    });
                }
            });
            return row;
        });
        _clThanhTien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getThanhtien().get()));
            }
        });
        tblHangThayThe.setPlaceholder(new Label("Thêm sản phẩm thay thế"));
    }

    @FXML
    private void Handler_btnAdd() {
        ChiTietHoaDonDoiTra ctsp = new ChiTietHoaDonDoiTra().getCTSPfromMa(txtSearch.getText().trim());
        if (ctsp.getMachitietsanpham() != null) {
            tblHangThayThe.getItems().add(ctsp);
            txtSearch.clear();
        }

    }

    @FXML
    private void Handler_btnLuu() {
        int sum1 = 0, sum2 = 0;
        sum1 = tblHangDoiTra.getItems().stream().map((item) -> item.getSoluongmua().get()).reduce(sum1, Integer::sum);
        if (sum1 == 0) {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Vui lòng nhập đầy đủ thông tin", NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(2));
            return;
        }

        sum2 = tblHangThayThe.getItems().stream().map((item) -> item.getSoluongmua().get()).reduce(sum2, Integer::sum);
        if (sum2 == 0) {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Vui lòng nhập đầy đủ thông tin", NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(2));
            return;
        }

        if (doitra.ThemDoiTra()) {
            for (ChiTietDoiTra item : tblHangDoiTra.getItems()) {
                if (item.getSoluongmua().get() != 0) {
                    item.ThemChiTietDoiTra(doitra.getLastId());
                    item.updateHDkhiDoiTra1(mahd, item.getMachitietsanpham().get(),
                            item.getSoluongmua().get(), item.getThanhtien().get());
                }
            }
        } else {
            System.out.println("Thêm Đổi Trả thất bại");
        }

        int thanhtien = 0;
        thanhtien = tblHangThayThe.getItems().stream().map((item) -> item.getThanhtien().get()).reduce(thanhtien, Integer::sum);

        if (new HoaDonDoiTra().ThemHoaDonDoiTra(doitra.getLastId(), thanhtien)) {
            List<String> listctsp = new ArrayList();
            listctsp = new ChiTietDoiTra().getListMaCTSPfromHD(mahd);
            for (ChiTietHoaDonDoiTra item : tblHangThayThe.getItems()) {
                if (item.getSoluongmua().get() != 0) {
                    item.ThemChiTietHoaDonDoiTra(new HoaDonDoiTra().getLastId());

                    if (listctsp.contains(item.getMachitietsanpham().get())) {
                        new ChiTietDoiTra().updateHDkhiDoiTra2(mahd, item.getMachitietsanpham().get(),
                                item.getSoluongmua().get(),
                                item.getThanhtien().get());
                    } else {
                        new ChiTietHoaDon(new SimpleIntegerProperty(mahd),
                                item.getMachitietsanpham(),
                                item.getSoluongmua(),
                                item.getThanhtien()).insert();
                        int sl = (new ChiTietSanPham().getSoLuongFromMaCTSP(item.getMachitietsanpham().get()))
                                - item.getSoluongmua().get();
                        new ChiTietSanPham()
                                .updateSoLuongFromMaCTSP(item.getMachitietsanpham().get(), sl);
                    }
                }
            }
            new ChiTietDoiTra().updateHDkhiDoiTra_removeSL0(mahd);
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Thêm hoá đơn đổi trả thành công", NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(2));
        } else {
            System.out.println("Thêm Hóa Đơn Đổi Trả thất bại");
        }

        btnLuu.setDisable(true);

        new DoiTra().updateTongTienHoaDon(mahd, total2 - total1);
        btnPrint.setDisable(false);
    }

    @FXML
    void Handler_btnPrint() {
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
            document.open(); // kệ nó ko sao chạy vẫn bt
            BaseFont unicode_font = BaseFont.createFont("times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Paragraph paragraph;
            //title
            paragraph = new Paragraph("Hóa đơn đổi trả", new Font(unicode_font, 18,
                    Font.BOLD));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph.add(new Paragraph(" "));
            //Ngày in báo cáo
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            document.add(new Paragraph("" + dt.format(new Date())));
//            document.addAuthor("Krishna Srinivasan");
//            document.addCreationDate();
//            document.addCreator("JavaBeat");
//            document.addTitle("Sample PDF");

            //Create Paragraph
            paragraph = new Paragraph("Danh sách sản phẩm đổi trả", new Font(unicode_font, 14,
                    Font.BOLD));
            paragraph.add(new Paragraph(" "));
            document.add(paragraph);

            //Create a table in PDF
            PdfPTable pdfTable = new PdfPTable(5); // 3 cột
            Font font = new Font(unicode_font, 12, Font.BOLD); // chỉnh lại kích thước chữ cho phù hợp giấy A4 hay A5
            PdfPCell cell1 = new PdfPCell(new Phrase("Mã chi tiết sản phẩm", font));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("Tên sản phẩm", font));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("giá bán", font));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("số lượng", font));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell1);

            cell1 = new PdfPCell(new Phrase("thành tiền", font));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell1);

            pdfTable.setHeaderRows(1);

            for (ChiTietDoiTra item : tblHangDoiTra.getItems()) {
                if (item.getSoluongmua().get() != 0) {
                    pdfTable.addCell(item.getMachitietsanpham().get());
                    pdfTable.addCell(item.getTensanpham().get());
                    pdfTable.addCell(String.valueOf(item.getGiaban().get()));
                    pdfTable.addCell(String.valueOf(item.getSoluongmua().get()));
                    pdfTable.addCell(String.valueOf(item.getThanhtien().get()));
                }
            }

            document.add(pdfTable);

            paragraph = new Paragraph("Danh sách sản phẩm thay thế", new Font(unicode_font, 14,
                    Font.BOLD));
            paragraph.add(new Paragraph(" "));
            document.add(paragraph);

            //Create a table in PDF
            PdfPTable pdfTable2 = new PdfPTable(5); // 3 cột
            PdfPCell cell2 = new PdfPCell(new Phrase("Mã chi tiết sản phẩm", font));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("Tên sản phẩm", font));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("giá bán", font));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("số lượng", font));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable2.addCell(cell2);

            cell2 = new PdfPCell(new Phrase("thành tiền", font));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable2.addCell(cell2);

            pdfTable2.setHeaderRows(1);

            for (ChiTietHoaDonDoiTra item : tblHangThayThe.getItems()) {
                if (item.getSoluongmua().get() != 0) {
                    pdfTable2.addCell(item.getMachitietsanpham().get());
                    pdfTable2.addCell(item.getTensanpham().get());
                    pdfTable2.addCell(String.valueOf(item.getGiaban().get()));
                    pdfTable2.addCell(String.valueOf(item.getSoluongmua().get()));
                    pdfTable2.addCell(String.valueOf(item.getThanhtien().get()));
                }
            }

            document.add(pdfTable2);

            paragraph = new Paragraph("", new Font(unicode_font, 14,
                    Font.BOLD));
            paragraph.add(new Paragraph(" "));
            paragraph.add(new Paragraph(" "));
            document.add(paragraph);

            Paragraph tt = new Paragraph("Tổng tiền hàng đổi trả : " + total1, new Font(unicode_font, 14,
                    Font.BOLD));
            tt.setAlignment(Element.ALIGN_RIGHT);
            document.add(tt);

            tt = new Paragraph("Tổng tiền hàng thay thế : " + total2, new Font(unicode_font, 14,
                    Font.BOLD));
            tt.setAlignment(Element.ALIGN_RIGHT);
            document.add(tt);

            tt = new Paragraph("-----------------------------", new Font(unicode_font, 14,
                    Font.BOLD));
            tt.setAlignment(Element.ALIGN_RIGHT);
            document.add(tt);

            tt = new Paragraph("Thành tiền : " + (total2 - total1), new Font(unicode_font, 14,
                    Font.BOLD));
            tt.setAlignment(Element.ALIGN_RIGHT);
            document.add(tt);

            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Handler_btnBack() {
        AnchorPane DoiTra;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_DoiTra.fxml"));
            DoiTra = fxmlLoader.load();
            SildingWindowAnimation silde = new SildingWindowAnimation();
            int last = rootP.getChildren().size() - 1;
            silde.SildeBack(rootP, (AnchorPane) rootP.getChildren().get(last),
                    SildingWindowAnimation.Direction.SildeRight);
            DoiTra.requestFocus();
            rootP.setLeftAnchor(DoiTra, 0.0);
            rootP.setRightAnchor(DoiTra, 0.0);
            rootP.setTopAnchor(DoiTra, 0.0);
            rootP.setBottomAnchor(DoiTra, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXML_SearchSanPhamController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
