/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietHoaDonMuaHang;
import clothesstore_model.NhapKho;
import clothesstore_model.HoaDonMuaHang;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_NhapKhoController implements Initializable {

    @FXML
    private TableView tableviewphieunhap;
    @FXML
    private TableView<ChiTietHoaDonMuaHang> tableviewchitietphieunhap;
    @FXML
    private TableColumn clmaphieunhap, _clmaphieunhap, clgiavon, clthanhtien,
            clsoluong, clsanpham, cltensanpham, clmachitiet, cltongtien, clngaynhap, clnhacungcap;
    @FXML
    private JFXDatePicker dtpFilter;
    @FXML
    private JFXCheckBox checkboxFilter;

    public static Stage stageCTKSP;
    public static String MaSP;
    public static String TenSP;
    public static int SLSP;
    public static int MAPN;
    private Date selectedDate = null;
    private boolean flag = false;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        HoaDonMuaHang pn = new HoaDonMuaHang();
        ObservableList<HoaDonMuaHang> list = pn.getListPhieuNhap();
        FormatDate();
        InitTableViewPhieuNhap(list);
        InitCMB();
        tableviewchitietphieunhap.setPlaceholder(new Label("Chọn vào phiếu nhập ở bảng trên để nhập kho"));
    }

    private void FormatDate() {
        dtpFilter.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }

    private void InitCMB() {
        checkboxFilter.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    HoaDonMuaHang pn = new HoaDonMuaHang();
                    ObservableList<HoaDonMuaHang> list = FXCollections.observableArrayList();
                    if (flag) {
                        list = pn.getListPhieuNhapChuaNhapKhoTheoNgay(selectedDate);
                    } else {
                        list = pn.getListPhieuNhapChuaNhapKho();
                    }
                    InitTableViewPhieuNhap(list);
                } else {
                    HoaDonMuaHang pn = new HoaDonMuaHang();
                    ObservableList<HoaDonMuaHang> list = FXCollections.observableArrayList();
                    if (flag) {
                        list = pn.getListPhieuNhapTheoNgay(selectedDate);
                    } else {
                        list = pn.getListPhieuNhap();
                    }
                    InitTableViewPhieuNhap(list);
                }
            }
        });
    }

    private void InitTableViewPhieuNhap(ObservableList<HoaDonMuaHang> list) {
        clmaphieunhap.setCellValueFactory(new PropertyValueFactory("mahoadonmuahang"));
        clngaynhap.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HoaDonMuaHang, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<HoaDonMuaHang, Integer> p) {
                return new ReadOnlyObjectWrapper(new SimpleDateFormat("dd-MM-yyyy").format(p.getValue().getNgaynhap()));
            }
        });
        clnhacungcap.setCellValueFactory(new PropertyValueFactory("tencungcap"));
        //cltongtien.setCellValueFactory(new PropertyValueFactory("tongtien"));
        cltongtien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HoaDonMuaHang, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<HoaDonMuaHang, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getTongtien()));
            }
        });
        tableviewphieunhap.setPlaceholder(new Label("Tất cả các phiếu nhập đã được nhập "));
        tableviewphieunhap.setItems(list);

        tableviewphieunhap.setRowFactory(tv -> {
            TableRow<HoaDonMuaHang> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    HoaDonMuaHang rowData = row.getItem();
                    InitTableViewChiTietPhieuNhap(rowData.getMahoadonmuahang());
                    MAPN = row.getItem().getMahoadonmuahang();
                }
            });
            return row;
        });
    }

    private void InitTableViewChiTietPhieuNhap(int maphieunhap) {
        ChiTietHoaDonMuaHang ctpn = new ChiTietHoaDonMuaHang();
        ObservableList<ChiTietHoaDonMuaHang> list = ctpn.getTableChiTietPhieuNhap(maphieunhap);

        clmachitiet.setCellValueFactory(new PropertyValueFactory("machitietphieunhap"));
        _clmaphieunhap.setCellValueFactory(new PropertyValueFactory("maphieunhap"));
        clsanpham.setCellValueFactory(new PropertyValueFactory("masanpham"));
        cltensanpham.setCellValueFactory(new PropertyValueFactory("tensanpham"));
        clsoluong.setCellValueFactory(new PropertyValueFactory("soluongsanphamnhap"));
        clgiavon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getGiavon()));
            }
        });
        clthanhtien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getThanhtien()));
            }
        });
        tableviewchitietphieunhap.setItems(list);
        tableviewchitietphieunhap.setRowFactory(tv -> {
            TableRow<ChiTietHoaDonMuaHang> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ChiTietHoaDonMuaHang selectedRow = (ChiTietHoaDonMuaHang) tableviewchitietphieunhap.getSelectionModel().getSelectedItem();
                    if (checkPhieuNhap(selectedRow.getMasanpham()) == true) {
                        ChiTietHoaDonMuaHang rowData = row.getItem();
                        MaSP = rowData.getMasanpham();
                        TenSP = rowData.getTensanpham();
                        SLSP = rowData.getSoluongsanphamnhap();
                        DisplayChitiet();
                    } else {
                        TrayNotification tray = new TrayNotification("Thông báo",
                                "Sản phẩm này đã được nhập kho trước đó", NotificationType.ERROR);
                        tray.showAndDismiss(Duration.seconds(2));
                    }
                }
            });
            return row;
        });
    }

    private boolean checkPhieuNhap(String masp) {
        NhapKho nk = new NhapKho();
        List listSPDaNhapKho = new ArrayList();
        listSPDaNhapKho = nk.getListMaSPDaNhapKho(MAPN);
        for (Object SPDaNhapKho : listSPDaNhapKho) {
            if (masp.equals(SPDaNhapKho.toString())) {
                return false;
            }
        }
        return true;
    }

    private void DisplayChitiet() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_ChiTietKhoSP.fxml"));
            Scene scene = new Scene(root);
            stageCTKSP = new Stage();
            stageCTKSP.initModality(Modality.APPLICATION_MODAL);
            stageCTKSP.initStyle(StageStyle.UNDECORATED);
            stageCTKSP.setResizable(false);
            stageCTKSP.setScene(scene);
            stageCTKSP.setOnHidden((WindowEvent event) -> {
                if (checkboxFilter.isSelected()) {
                    HoaDonMuaHang pn = new HoaDonMuaHang();
                    ObservableList<HoaDonMuaHang> list = pn.getListPhieuNhapChuaNhapKho();
                    InitTableViewPhieuNhap(list);
                } else {
                    HoaDonMuaHang pn = new HoaDonMuaHang();
                    ObservableList<HoaDonMuaHang> list = pn.getListPhieuNhap();
                    InitTableViewPhieuNhap(list);
                }
            });
            stageCTKSP.show();
        } catch (IOException ex) {
            Logger.getLogger(FXML_NhapKhoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Handler_linkBoChon(ActionEvent event) {
        flag = false;
        dtpFilter.setValue(null);
        if (checkboxFilter.isSelected()) {
            HoaDonMuaHang pn = new HoaDonMuaHang();
            ObservableList<HoaDonMuaHang> list = pn.getListPhieuNhapChuaNhapKho();
            InitTableViewPhieuNhap(list);
        } else {
            HoaDonMuaHang pn = new HoaDonMuaHang();
            ObservableList<HoaDonMuaHang> list = pn.getListPhieuNhap();
            InitTableViewPhieuNhap(list);
        }
    }

    @FXML
    private void currentdatepickeronhidden(Event event) {
        flag = true;
        try {
            selectedDate = java.sql.Date.valueOf(dtpFilter.getValue());
            HoaDonMuaHang pn = new HoaDonMuaHang();
            ObservableList<HoaDonMuaHang> list;
            if (checkboxFilter.isSelected()) {
                list = pn.getListPhieuNhapChuaNhapKhoTheoNgay(selectedDate);
                InitTableViewPhieuNhap(list);
            } else {
                list = pn.getListPhieuNhapTheoNgay(selectedDate);
                InitTableViewPhieuNhap(list);
            }
        } catch (Exception ex) {
        }
    }
}
