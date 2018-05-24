/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import clothesstore_model.NhaCungCap;
import clothesstore_model.HoaDonMuaHang;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author 15520
 */
public class FXML_HoaDonMuaHangController implements Initializable {

    @FXML
    private JFXDatePicker datengaynhap;
    @FXML
    private JFXComboBox cbnhacungcap;
    @FXML
    private TableColumn clmaphieunhap, clnhacungcap, clngaynhap, cltongtien;
    @FXML
    private TableView<HoaDonMuaHang> tableviewphieunhap;
    @FXML
    private JFXButton btnluuphieunhap, btnhuyphieunhap, btnthemphieu;
    @FXML
    private MenuItem menuThem;

    public static Stage stageQuanLyNCC;
    public static Stage stageQuanLyCTPN;
    public static AnchorPane rootCTPN;
    public static int mapn;
    private ChangeListener<NhaCungCap> listenerNCC;
    public int getmaphieunhap;
    private int manhacungcap;
    private HoaDonMuaHang hdmuahang;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableViewPhieuNhap();
        InitCmbNCC();
        datengaynhap.setValue(LocalDate.now());
    }

    @FXML
    private void Handler_btnnhacungcap(ActionEvent event) {
        cbnhacungcap.getSelectionModel().selectedItemProperty().removeListener(listenerNCC);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_NhaCungCap.fxml"));
            stageQuanLyNCC = new Stage();
            stageQuanLyNCC.initModality(Modality.APPLICATION_MODAL);
            //stageQuanLyNCC.initStyle(StageStyle.UNDECORATED);
            stageQuanLyNCC.setScene(new Scene(root));
            stageQuanLyNCC.setOnCloseRequest((WindowEvent event1) -> {
                NhaCungCap ncc = new NhaCungCap();
                ObservableList<NhaCungCap> list = ncc.getTableNhaCungCap();
                cbnhacungcap.getItems().clear();
                cbnhacungcap.setItems(list);
                cbnhacungcap.setConverter(new StringConverter<NhaCungCap>() {
                    @Override
                    public String toString(NhaCungCap object) {
                        return object.getTencungcap();
                    }

                    @Override
                    public NhaCungCap fromString(String string) {
                        return null;
                    }
                });

                cbnhacungcap.getSelectionModel().selectedItemProperty().addListener(listenerNCC = new ChangeListener<NhaCungCap>() {
                    @Override
                    public void changed(ObservableValue<? extends NhaCungCap> observable, NhaCungCap oldValue, NhaCungCap newValue) {
                        System.out.println(newValue.getManhacungcap());
                        manhacungcap = newValue.getManhacungcap();
                    }
                });

                cbnhacungcap.getSelectionModel().selectFirst();
            });
            stageQuanLyNCC.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handler_Themphieunhap(ActionEvent event) {
        NhaCungCap ncc = new NhaCungCap();
        ObservableList<NhaCungCap> list = ncc.getTableNhaCungCap();
        int nhacc = manhacungcap;
        LocalDate _ngaynhap = null;
        java.sql.Date ngaynhap = null;
        try {
            _ngaynhap = datengaynhap.getValue();
            ngaynhap = java.sql.Date.valueOf(_ngaynhap);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        int tongtien = 0;
        if (nhacc == 0 || _ngaynhap == null) {

            ButtonType cancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Vui lòng điền đầy đủ thông tin",
                    cancel);

            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            HoaDonMuaHang pn = new HoaDonMuaHang(nhacc, ngaynhap, tongtien);
            pn.ThemPhieuNhap();
            InitTableViewPhieuNhap();
        }
    }

    @FXML
    private void handler_xoaphieunhap(ActionEvent event) {
        HoaDonMuaHang selectedForDeletion = tableviewphieunhap.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Mời chọn hóa đơn mua hàng");
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nhắc nhở");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có muốn xóa hóa đơn mua hàng có mã " + selectedForDeletion.getMahoadonmuahang() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            HoaDonMuaHang pn = new HoaDonMuaHang();
            pn.XoaPhieuNhap(selectedForDeletion.getMahoadonmuahang());
            InitTableViewPhieuNhap();
            System.out.println("Xoa Thanh Cong");
        } else {
            System.out.println("Xoa That Bai");
        }

    }

    @FXML
    private void handler_xemchitietphieunhap(ActionEvent event) {
        HoaDonMuaHang selectedForViewing = tableviewphieunhap.getSelectionModel().getSelectedItem();
        if (selectedForViewing == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Mời chọn hóa đơn mua hàng");
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            return;
        }

        HoaDonMuaHang getSelectedRow = tableviewphieunhap.getSelectionModel().getSelectedItem();
        mapn = getSelectedRow.getMahoadonmuahang();
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_XemCTPN.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXML_HoaDonMuaHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handler_themchitietphieunhap(ActionEvent event) {
        HoaDonMuaHang selectedForDeletion = tableviewphieunhap.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Mời chọn hóa đơn mua hàng");
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            return;
        }

        HoaDonMuaHang getSelectedRow = tableviewphieunhap.getSelectionModel().getSelectedItem();
        mapn = getSelectedRow.getMahoadonmuahang();
        try {
            rootCTPN = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_SearchSanPham.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXML_HoaDonMuaHangController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(rootCTPN));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                InitTableViewPhieuNhap();
            }
        });
        stage.showAndWait();
    }

    @FXML
    private void handler_suaphieunhap(ActionEvent event) {
        HoaDonMuaHang selectedForDeletion = tableviewphieunhap.getSelectionModel().getSelectedItem();
        hdmuahang = (HoaDonMuaHang) tableviewphieunhap.getSelectionModel().getSelectedItem();
        datengaynhap.setValue(selectedForDeletion.getNgaynhap().toLocalDate());
        if (selectedForDeletion == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Mời chọn hóa đơn mua hàng");
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            return;
        }

        //txtfinguoinhap.setText(selectedForDeletion.getMaNhanVien());
        btnluuphieunhap.setDisable(false);
        btnthemphieu.setDisable(true);
        btnhuyphieunhap.setDisable(false);
    }

    @FXML
    private void handler_luuphieunhap(ActionEvent event) {

        int nhacc = manhacungcap;
        LocalDate _ngaynhap = null;
        java.sql.Date ngaynhap = null;
        try {
            _ngaynhap = datengaynhap.getValue();
            ngaynhap = java.sql.Date.valueOf(_ngaynhap);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        if (nhacc == 0 || ngaynhap == null) {

            ButtonType cancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Vui lòng điền đầy đủ thông tin",
                    cancel);

            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            HoaDonMuaHang pn = new HoaDonMuaHang(hdmuahang.getMahoadonmuahang(), nhacc, ngaynhap);
            pn.CapNhatPhieuNhap();
            InitTableViewPhieuNhap();
            btnluuphieunhap.setDisable(true);
            btnthemphieu.setDisable(false);
            btnhuyphieunhap.setDisable(true);
        }
    }

    @FXML
    private void checkAdded(ContextMenuEvent event) {
        if (tableviewphieunhap.getSelectionModel().getSelectedItem().getTongtien() != 0) {
            menuThem.setDisable(true);
        } else {
            menuThem.setDisable(false);
        }
    }

    @FXML
    private void handler_huyphieunhap(ActionEvent event) {
        btnluuphieunhap.setDisable(true);
        btnthemphieu.setDisable(false);
        btnhuyphieunhap.setDisable(true);
    }

    private void InitCmbNCC() {
        NhaCungCap ncc = new NhaCungCap();
        ObservableList<NhaCungCap> list = ncc.getTableNhaCungCap();
        cbnhacungcap.setItems(list);
        cbnhacungcap.setConverter(new StringConverter<NhaCungCap>() {
            @Override
            public String toString(NhaCungCap object) {
                return object.getTencungcap();
            }

            @Override
            public NhaCungCap fromString(String string) {
                return null;
            }
        });
        cbnhacungcap.getSelectionModel().selectedItemProperty().addListener(listenerNCC = new ChangeListener<NhaCungCap>() {
            @Override
            public void changed(ObservableValue<? extends NhaCungCap> observable, NhaCungCap oldValue, NhaCungCap newValue) {
                System.out.println(newValue.getManhacungcap());
                manhacungcap = newValue.getManhacungcap();
            }
        });
    }

    public void InitTableViewPhieuNhap() {
        HoaDonMuaHang pn = new HoaDonMuaHang();
        ObservableList<HoaDonMuaHang> list = pn.getListPhieuNhap();
        clmaphieunhap.setCellValueFactory(new PropertyValueFactory("mahoadonmuahang"));
        clngaynhap.setCellValueFactory(new PropertyValueFactory("ngaynhap"));
        clnhacungcap.setCellValueFactory(new PropertyValueFactory("tencungcap"));
        cltongtien.setCellValueFactory(new PropertyValueFactory("tongtien"));
        cltongtien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HoaDonMuaHang, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HoaDonMuaHang, String> param) {
                return new ReadOnlyObjectWrapper(FormatTien(param.getValue().getTongtien()));
            }

        });
        tableviewphieunhap.setItems(list);
        tableviewphieunhap.setPlaceholder(new Label("Không tìm thấy phiếu nhập"));
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }
}
