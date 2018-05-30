/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static clothesstore_controller.FXML_HoaDonMuaHangController.mapn;
import static clothesstore_controller.FXML_HoaDonMuaHangController.rootCTPN;
import clothesstore_model.ChiTietHoaDonMuaHang;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author 15520
 */
public class FXML_ChiTietPhieuNhapController implements Initializable {

    @FXML
    private TableView<ChiTietHoaDonMuaHang> tblCTPN;
    @FXML
    private TableColumn clMaSP, clTenSP, clSoLuong, clGiaVon, clThanhTien, clGiaBan;
    @FXML
    private JFXButton btnBack, btnSave;
    @FXML
    private JFXTextField txtmaphieunhap;
    public static ObservableList<ChiTietHoaDonMuaHang> _listSP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableCTPN();
        txtmaphieunhap.setText(Integer.toString(mapn));
    }

    public void _setListSP(ObservableList<ChiTietHoaDonMuaHang> list) {
        _listSP = list;
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }

    public void InitTableCTPN() {
        clMaSP.setCellValueFactory(new PropertyValueFactory("masanpham"));
        clTenSP.setCellValueFactory(new PropertyValueFactory("tensanpham"));
//        clThanhTien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
        clThanhTien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getThanhtien()));
            }
        });
//        clSoLuong.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String> p) {
//                return p.getValue().getString_soluongsanphamnhap();
//            }
//        });
        clSoLuong.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getSoluongsanphamnhap()));
            }
        });
        clSoLuong.setCellFactory(new Callback<TableColumn<ChiTietHoaDonMuaHang, String>, TableCell<ChiTietHoaDonMuaHang, String>>() {
            @Override
            public TableCell<ChiTietHoaDonMuaHang, String> call(TableColumn<ChiTietHoaDonMuaHang, String> p) {
                return new TextFieldTableCell();
            }
        });
        clSoLuong.setCellFactory(TextFieldTableCell.forTableColumn());
        clSoLuong.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String> t) {
                btnSave.setDisable(true);
            }
        });

//        clGiaVon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String> p) {
//                return p.getValue().getString_giavon();
//            }
//        });
        clGiaVon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getGiavon()));
            }
        });

        clGiaVon.setCellFactory(new Callback<TableColumn<ChiTietHoaDonMuaHang, String>, TableCell<ChiTietHoaDonMuaHang, String>>() {
            @Override
            public TableCell<ChiTietHoaDonMuaHang, String> call(TableColumn<ChiTietHoaDonMuaHang, String> p) {
                return new TextFieldTableCell();
            }
        });
        clGiaVon.setCellFactory(TextFieldTableCell.forTableColumn());
        clGiaVon.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String> t) {
                btnSave.setDisable(true);
            }
        });
//        clGiaBan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String> p) {
//                return p.getValue().getGiaban();
//            }
//        });
        clGiaBan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonMuaHang, String> p) {
                return new ReadOnlyObjectWrapper(FormatTien(Integer.valueOf(p.getValue().getGiaban().get())));
            }
        });
        clGiaBan.setCellFactory(new Callback<TableColumn<ChiTietHoaDonMuaHang, String>, TableCell<ChiTietHoaDonMuaHang, String>>() {
            @Override
            public TableCell<ChiTietHoaDonMuaHang, String> call(TableColumn<ChiTietHoaDonMuaHang, String> p) {
                return new TextFieldTableCell();
            }
        });
        clGiaBan.setCellFactory(TextFieldTableCell.forTableColumn());
        clGiaBan.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String> t) {
                btnSave.setDisable(true);
            }
        });
        tblCTPN.setRowFactory(tv -> {
            TableRow<ChiTietHoaDonMuaHang> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    clGiaVon.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String> t) {
                            btnSave.setDisable(false);
                            try {
                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setGiavon(
                                        new SimpleIntegerProperty(Integer.valueOf(t.getNewValue().trim())));

                                int sl = ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getSoluongsanphamnhap();
                                int gv = ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getGiavon();
                                int tt = sl * gv;
                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setThanhtien(
                                        new SimpleIntegerProperty(tt));
                                row.setItem(((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())));
                                tblCTPN.refresh();
                            } catch (NumberFormatException ex) {
                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setGiavon(
                                        new SimpleIntegerProperty(0));
                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setThanhtien(
                                        new SimpleIntegerProperty(0));
                                TrayNotification tray = new TrayNotification("Thông báo",
                                        "Giá vốn không phù hợp", NotificationType.ERROR);
                                tray.showAndDismiss(Duration.seconds(2));
                                tblCTPN.refresh();
                            }
                        }
                    });
                    clSoLuong.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String> t) {
                            btnSave.setDisable(false);
                            try {
                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setSoluongsanphamnhap(
                                        new SimpleIntegerProperty(Integer.valueOf(t.getNewValue().trim())));
                                int sl = ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getSoluongsanphamnhap();
                                int gv = ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getGiavon();
                                int tt = sl * gv;

                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setThanhtien(
                                        new SimpleIntegerProperty(tt));
                                row.setItem(((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())));
                                tblCTPN.refresh();
                            } catch (NumberFormatException ex) {
                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setSoluongsanphamnhap(
                                        new SimpleIntegerProperty(0));
                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setThanhtien(
                                        new SimpleIntegerProperty(0));
                                //row.setStyle("-fx-background-color: red");
                                TrayNotification tray = new TrayNotification("Thông báo",
                                        "Số lượng không phù hợp", NotificationType.ERROR);
                                tray.showAndDismiss(Duration.seconds(2));
                                tblCTPN.refresh();
                            }
                        }
                    });
                    clGiaBan.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ChiTietHoaDonMuaHang, String> t) {
                            btnSave.setDisable(false);
                            try {
                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setGiaban(
                                        new SimpleIntegerProperty(Integer.valueOf(t.getNewValue().trim())));
                            } catch (NumberFormatException ex) {
                                String msp = ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getMasanpham();
                                ((ChiTietHoaDonMuaHang) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setGiaban(
                                        new SimpleIntegerProperty(new ChiTietHoaDonMuaHang().getGiaBanSP(msp)));
                                TrayNotification tray = new TrayNotification("Thông báo",
                                        "Giá bán không phù hợp", NotificationType.ERROR);
                                tray.showAndDismiss(Duration.seconds(2));
                                tblCTPN.refresh();
                            }
                        }
                    });
                }
            });
            return row;
        }
        );
        tblCTPN.setItems(_listSP);
    }

    @FXML
    private void Handler_btnSave(ActionEvent event) {
        boolean flag = true;
        for (ChiTietHoaDonMuaHang o : tblCTPN.getItems()) {

            if (o.getThanhtien() <= 0) {
                tblCTPN.getSelectionModel().select(o);
                TrayNotification tray = new TrayNotification("Thông báo",
                        "Số lượng (Giá vốn) không phù hợp", NotificationType.ERROR);
                tray.showAndDismiss(Duration.seconds(2));
                flag = false;
                break;
            }
            try {
                int gb = o.getGiaban().get();
                if (gb <= 0) {
                    flag = false;
                    tblCTPN.getSelectionModel().select(o);
                    TrayNotification tray = new TrayNotification("Thông báo",
                            "Giá bán không phù hợp", NotificationType.ERROR);
                    tray.showAndDismiss(Duration.seconds(2));
                    flag = false;
                    break;
                }
            } catch (NumberFormatException ex) {
                tblCTPN.getSelectionModel().select(o);
                TrayNotification tray = new TrayNotification("Thông báo",
                        "Giá bán không phù hợp", NotificationType.ERROR);
                tray.showAndDismiss(Duration.seconds(2));
                flag = false;
                break;
            }
        }
        if (flag) {
            for (int i = 0; i < tblCTPN.getItems().size(); i++) {
                String msp = clMaSP.getCellData(i).toString();
                int sl = tblCTPN.getItems().get(i).getSoluongsanphamnhap();
                int gv = tblCTPN.getItems().get(i).getGiavon();
                int tt = tblCTPN.getItems().get(i).getThanhtien();
                int gb = tblCTPN.getItems().get(i).getGiaban().get();
                ChiTietHoaDonMuaHang ctpn = new ChiTietHoaDonMuaHang(msp, mapn, sl, gv, tt);
                if (ctpn.ThemChiTietPhieuNhap()) {
                    ctpn.CapNhatGiaBanSanPham(msp, gb);
                    btnSave.setDisable(true);
                    btnBack.setDisable(true);
                    tblCTPN.setEditable(false);
                } else {
                    System.out.println("Luu that bai");
                }
            }
            ChiTietHoaDonMuaHang ctpn = new ChiTietHoaDonMuaHang();
            ctpn.CapNhatTongTienPhieuNhap(mapn);
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Thêm hoá đơn thành công", NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(2));
        }
    }

    @FXML
    private void Handler_btnBack(ActionEvent event) {
        AnchorPane searchSP;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_SearchSanPham.fxml"));
            searchSP = fxmlLoader.load();
            SildingWindowAnimation silde = new SildingWindowAnimation();
            int last = rootCTPN.getChildren().size() - 1;
            silde.SildeBack(rootCTPN, (AnchorPane) rootCTPN.getChildren().get(last),
                    SildingWindowAnimation.Direction.SildeRight);
            searchSP.requestFocus();
            rootCTPN.setLeftAnchor(searchSP, 0.0);
            rootCTPN.setRightAnchor(searchSP, 0.0);
            rootCTPN.setTopAnchor(searchSP, 0.0);
            rootCTPN.setBottomAnchor(searchSP, 0.0);

            FXML_SearchSanPhamController controller = fxmlLoader.getController();
            controller.setListSP(_listSP);
        } catch (IOException ex) {
            Logger.getLogger(FXML_SearchSanPhamController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
