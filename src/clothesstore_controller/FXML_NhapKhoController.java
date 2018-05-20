/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietPhieuNhap;
import clothesstore_model.KhoSanPham;
import clothesstore_model.PhieuNhap;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_NhapKhoController implements Initializable {

    @FXML
    private TableView tableviewphieunhap;
    @FXML
    private TableView<ChiTietPhieuNhap> tableviewchitietphieunhap;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        PhieuNhap pn = new PhieuNhap();
        ObservableList<PhieuNhap> list = pn.getListPhieuNhap();
        InitTableViewPhieuNhap(list);
        InitCMB();
        tableviewchitietphieunhap.setPlaceholder(new Label("Chọn vào phiếu nhập ở bảng trên để nhập kho"));
    }

    private void InitCMB() {
        checkboxFilter.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    PhieuNhap pn = new PhieuNhap();
                    ObservableList<PhieuNhap> list = FXCollections.observableArrayList();
                    if (flag) {
                        list = pn.getListPhieuNhapChuaNhapKhoTheoNgay(selectedDate);
                    } else {
                        list = pn.getListPhieuNhapChuaNhapKho();
                    }
                    InitTableViewPhieuNhap(list);
                } else {
                    PhieuNhap pn = new PhieuNhap();
                    ObservableList<PhieuNhap> list = FXCollections.observableArrayList();
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

    private void InitTableViewPhieuNhap(ObservableList<PhieuNhap> list) {
        clmaphieunhap.setCellValueFactory(new PropertyValueFactory("maphieunhap"));
        clngaynhap.setCellValueFactory(new PropertyValueFactory("ngaynhap"));
        clnhacungcap.setCellValueFactory(new PropertyValueFactory("tencungcap"));
        cltongtien.setCellValueFactory(new PropertyValueFactory("tongtien"));
        tableviewphieunhap.setPlaceholder(new Label("Tất cả các phiếu nhập đã được nhập "));
        tableviewphieunhap.setItems(list);

        tableviewphieunhap.setRowFactory(tv -> {
            TableRow<PhieuNhap> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    PhieuNhap rowData = row.getItem();
                    InitTableViewChiTietPhieuNhap(rowData.getMaphieunhap());
                    MAPN = row.getItem().getMaphieunhap();
                }
            });
            return row;
        });
//        tableviewphieunhap.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
//                if (tableviewphieunhap.getSelectionModel().getSelectedItem() == null) {
//                    tableviewchitietphieunhap.getItems().clear();
//                }
//            }
//        });
    }

    private void InitTableViewChiTietPhieuNhap(int maphieunhap) {
        ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
        ObservableList<ChiTietPhieuNhap> list = ctpn.getTableChiTietPhieuNhap(maphieunhap);

        clmachitiet.setCellValueFactory(new PropertyValueFactory("machitietphieunhap"));
        _clmaphieunhap.setCellValueFactory(new PropertyValueFactory("maphieunhap"));
        clsanpham.setCellValueFactory(new PropertyValueFactory("masanpham"));
        cltensanpham.setCellValueFactory(new PropertyValueFactory("tensanpham"));
        clsoluong.setCellValueFactory(new PropertyValueFactory("soluongsanphamnhap"));
        clgiavon.setCellValueFactory(new PropertyValueFactory("giavon"));
        clthanhtien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
        tableviewchitietphieunhap.setItems(list);
        tableviewchitietphieunhap.setRowFactory(tv -> {
            TableRow<ChiTietPhieuNhap> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ChiTietPhieuNhap selectedRow = (ChiTietPhieuNhap) tableviewchitietphieunhap.getSelectionModel().getSelectedItem();
                    if (checkPhieuNhap(selectedRow.getMasanpham()) == true) {
                        ChiTietPhieuNhap rowData = row.getItem();
                        MaSP = rowData.getMasanpham();
                        TenSP = rowData.getTensanpham();
                        SLSP = rowData.getSoluongsanphamnhap();
                        DisplayChitiet();
                    } else {
                        ShowMessage
                                .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Sản phẩm này đã được nhập kho trước đó")
                                .showAndWait();
                    }
                }
            });
            return row;
        });
    }

    private boolean checkPhieuNhap(String masp) {
        KhoSanPham ksp = new KhoSanPham();
        List listSPDaNhapKho = new ArrayList();
        listSPDaNhapKho = ksp.getListMaSPDaNhapKho(MAPN);

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
                    PhieuNhap pn = new PhieuNhap();
                    ObservableList<PhieuNhap> list = pn.getListPhieuNhapChuaNhapKho();
                    InitTableViewPhieuNhap(list);
                } else {
                    PhieuNhap pn = new PhieuNhap();
                    ObservableList<PhieuNhap> list = pn.getListPhieuNhap();
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
            PhieuNhap pn = new PhieuNhap();
            ObservableList<PhieuNhap> list = pn.getListPhieuNhapChuaNhapKho();
            InitTableViewPhieuNhap(list);
        } else {
            PhieuNhap pn = new PhieuNhap();
            ObservableList<PhieuNhap> list = pn.getListPhieuNhap();
            InitTableViewPhieuNhap(list);
        }
    }

    @FXML
    private void currentdatepickeronhidden(Event event) {
        flag = true;
        try {
            selectedDate = java.sql.Date.valueOf(dtpFilter.getValue());
            PhieuNhap pn = new PhieuNhap();
            ObservableList<PhieuNhap> list;
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
