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
import static clothesstore_controller.FXML_PhieuNhapController.mapn;
import static clothesstore_controller.FXML_PhieuNhapController.rootCTPN;
import clothesstore_model.ChiTietPhieuNhap;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author 15520
 */
public class FXML_ChiTietPhieuNhapController implements Initializable {

    @FXML
    private TableView tblCTPN;
    @FXML
    private TableColumn clMaSP, clTenSP, clSoLuong, clGiaVon, clThanhTien, clGiaBan;
    @FXML
    private JFXButton btnBack, btnSave;
    @FXML
    private JFXTextField txtmaphieunhap;
    public static ObservableList<ChiTietPhieuNhap> _listSP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableCTPN();
        txtmaphieunhap.setText(Integer.toString(mapn));
    }

    public void _setListSP(ObservableList<ChiTietPhieuNhap> list) {
        _listSP = list;
    }

    public void InitTableCTPN() {
        clMaSP.setCellValueFactory(new PropertyValueFactory("masanpham"));
        clTenSP.setCellValueFactory(new PropertyValueFactory("tensanpham"));
        clThanhTien.setCellValueFactory(new PropertyValueFactory("thanhtien"));

        clSoLuong.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietPhieuNhap, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietPhieuNhap, String> p) {
                return p.getValue().getString_soluongsanphamnhap();
            }
        });
        clSoLuong.setCellFactory(new Callback<TableColumn<ChiTietPhieuNhap, String>, TableCell<ChiTietPhieuNhap, String>>() {
            @Override
            public TableCell<ChiTietPhieuNhap, String> call(TableColumn<ChiTietPhieuNhap, String> p) {
                return new TextFieldTableCell();
            }
        });
        clSoLuong.setCellFactory(TextFieldTableCell.forTableColumn());
        clSoLuong.setOnEditStart(
                new EventHandler<TableColumn.CellEditEvent<ChiTietPhieuNhap, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ChiTietPhieuNhap, String> t) {
                btnSave.setDisable(true);
            }
        });

        clGiaVon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietPhieuNhap, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietPhieuNhap, String> p) {
                return p.getValue().getString_giavon();
            }
        });

        clGiaVon.setCellFactory(new Callback<TableColumn<ChiTietPhieuNhap, String>, TableCell<ChiTietPhieuNhap, String>>() {
            @Override
            public TableCell<ChiTietPhieuNhap, String> call(TableColumn<ChiTietPhieuNhap, String> p) {
                return new TextFieldTableCell();
            }
        });
        clGiaVon.setCellFactory(TextFieldTableCell.forTableColumn());
        clGiaVon.setOnEditStart(
                new EventHandler<TableColumn.CellEditEvent<ChiTietPhieuNhap, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ChiTietPhieuNhap, String> t) {
                btnSave.setDisable(true);
            }
        });
        clGiaBan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietPhieuNhap, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietPhieuNhap, String> p) {
                return p.getValue().getGiaban();
            }
        });
        clGiaBan.setCellFactory(new Callback<TableColumn<ChiTietPhieuNhap, String>, TableCell<ChiTietPhieuNhap, String>>() {
            @Override
            public TableCell<ChiTietPhieuNhap, String> call(TableColumn<ChiTietPhieuNhap, String> p) {
                return new TextFieldTableCell();
            }
        });
        clGiaBan.setCellFactory(TextFieldTableCell.forTableColumn());
        clGiaBan.setOnEditStart(
                new EventHandler<TableColumn.CellEditEvent<ChiTietPhieuNhap, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ChiTietPhieuNhap, String> t) {
                btnSave.setDisable(true);
            }
        });
        tblCTPN.setRowFactory(tv -> {
            TableRow<ChiTietPhieuNhap> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    clGiaVon.setOnEditCommit(
                            new EventHandler<TableColumn.CellEditEvent<ChiTietPhieuNhap, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ChiTietPhieuNhap, String> t) {
                            btnSave.setDisable(false);
                            try {
                                ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setString_giavon(
                                        new SimpleStringProperty(t.getNewValue().trim()));

                                int sl = ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getSoluongsanphamnhap();
                                int gv = ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getGiavon();
                                int tt = sl * gv;
                                ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setThanhtien(
                                        new SimpleIntegerProperty(tt));
                                row.setItem(((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())));
                                tblCTPN.refresh();
                            } catch (NumberFormatException ex) {
                                ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setGiavon(
                                        new SimpleIntegerProperty(0));
                                ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setThanhtien(
                                        new SimpleIntegerProperty(0));
                                //row.setStyle("-fx-background-color: red");
                                ShowMessage
                                        .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Giá vốn chỉ được nhập số")
                                        .showAndWait();
                                tblCTPN.refresh();
                            }
                        }
                    });
                    clSoLuong.setOnEditCommit(
                            new EventHandler<TableColumn.CellEditEvent<ChiTietPhieuNhap, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ChiTietPhieuNhap, String> t) {
                            btnSave.setDisable(false);
                            try {
                                ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setString_soluongsanphamnhap(
                                        new SimpleStringProperty(t.getNewValue().trim()));
                                int sl = ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getSoluongsanphamnhap();
                                int gv = ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getGiavon();
                                int tt = sl * gv;

                                ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setThanhtien(
                                        new SimpleIntegerProperty(tt));
                                row.setItem(((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())));
                                tblCTPN.refresh();
                            } catch (NumberFormatException ex) {
                                ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setSoluongsanphamnhap(
                                        new SimpleIntegerProperty(0));
                                ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setThanhtien(
                                        new SimpleIntegerProperty(0));
                                //row.setStyle("-fx-background-color: red");
                                ShowMessage
                                        .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Số lượng chỉ được nhập số")
                                        .showAndWait();
                                tblCTPN.refresh();
                            }
                        }
                    });
                    clGiaBan.setOnEditCommit(
                            new EventHandler<TableColumn.CellEditEvent<ChiTietPhieuNhap, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ChiTietPhieuNhap, String> t) {
                            btnSave.setDisable(false);
                            try {
                                ((ChiTietPhieuNhap) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setGiaban(
                                        new SimpleStringProperty(t.getNewValue().trim()));
                                Integer.valueOf(row.getItem().getGiaban().get());
                            } catch (NumberFormatException ex) {
                                //row.setStyle("-fx-background-color: red");                                
                                ShowMessage
                                        .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Giá bán chỉ được nhập số")
                                        .showAndWait();
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
        for (Object o : tblCTPN.getItems()) {
            if (Integer.valueOf(clThanhTien.getCellData(o).toString()) <= 0) {
                tblCTPN.getSelectionModel().select(o);
                ShowMessage
                        .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Số lượng, Giá vốn chỉ được nhập số (Số dương > 0)")
                        .showAndWait();
                flag = false;
                break;
            }
            try {
                int gb = Integer.valueOf(clGiaBan.getCellData(o).toString());
                if (gb <= 0) {
                    flag = false;
                    tblCTPN.getSelectionModel().select(o);
                    ShowMessage
                            .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Giá bán chỉ được nhập số (Số dương > 0)")
                            .showAndWait();
                    flag = false;
                    break;
                }
            } catch (NumberFormatException ex) {
                tblCTPN.getSelectionModel().select(o);
                ShowMessage
                        .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Giá bán chỉ được nhập số (Số dương > 0)")
                        .showAndWait();
                flag = false;
                break;
            }
        }
        if (flag) {
            ButtonType yes = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Bạn có chắc chắn muốn lưu",
                    yes,
                    cancel);

            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == yes) {
                for (int i = 0; i < tblCTPN.getItems().size(); i++) {
                    String msp = clMaSP.getCellData(i).toString();
                    int sl = Integer.valueOf(clSoLuong.getCellData(i).toString());
                    int gv = Integer.valueOf(clGiaVon.getCellData(i).toString());
                    int tt = Integer.valueOf(clThanhTien.getCellData(i).toString());
                    int gb = Integer.valueOf(clGiaBan.getCellData(i).toString());
                    ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(msp, mapn, sl, gv, tt);
                    if (ctpn.ThemChiTietPhieuNhap()) {
                        ctpn.CapNhatGiaBanSanPham(msp, gb);
                        btnSave.setDisable(true);
                        btnBack.setDisable(true);
                        System.out.println("Luu thanh cong");
                    } else {
                        System.out.println("Luu that bai");
                    }
                }
                ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
                ctpn.CapNhatTongTienPhieuNhap(mapn);
            }
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
