/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_DangNhapController.MaNhanVien;
import static clothesstore_controller.FXML_NhapKhoController.MAPN;
import static clothesstore_controller.FXML_NhapKhoController.MaSP;
import static clothesstore_controller.FXML_NhapKhoController.SLSP;
import static clothesstore_controller.FXML_NhapKhoController.TenSP;
import static clothesstore_controller.FXML_NhapKhoController.stageCTKSP;
import clothesstore_model.ChiTietKhoSanPham;
import clothesstore_model.ChiTietSanPham;
import clothesstore_model.KhoSanPham;
import clothesstore_model.MauSac;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_ChiTietKhoSPController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView tblchitietkhosp;
    @FXML
    private TableColumn clmachitietsp, cltenchitietsanpham, clsoluong;
    @FXML
    private Label lbTongSoLuong, lbSoLuongYC, lbTenSP;
    @FXML
    private JFXButton btnLuu;

    private static int tongsoluong = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableCTSP();
        lbTenSP.setText(TenSP);
        lbSoLuongYC.setText("Số lượng yêu cầu: " + SLSP);
    }

    private void InitTableCTSP() {
        ChiTietSanPham ctsp = new ChiTietSanPham();
        List<ChiTietSanPham> listCTSP = new ArrayList();
        listCTSP = ctsp.getListCTSPFromMasp(MaSP);

        ObservableList<ChiTietKhoSanPham> listCTKSP = observableArrayList();
        for (ChiTietSanPham _ctsp : listCTSP) {
            String gioitinh = "NULL";
            if (_ctsp.getGioitinh().get() == 1) {
                gioitinh = "Nam";
            } else {
                gioitinh = "Nữ";
            }
            MauSac ms = new MauSac();

            String ten = _ctsp.getSize().get() + "_" + ms.getTenMauFromMaMau(_ctsp.getMausac().get())  + "_" + gioitinh;
            String maCTSP = _ctsp.getMachitietsanpham().get();
            String soluong = "0";
            ChiTietKhoSanPham ctksp = new ChiTietKhoSanPham(maCTSP, ten, soluong);
            listCTKSP.add(ctksp);
        }

        clmachitietsp.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietKhoSanPham, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietKhoSanPham, String> p) {
                return p.getValue().getMachitietsp();
            }
        });

        cltenchitietsanpham.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietKhoSanPham, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietKhoSanPham, String> p) {
                return p.getValue().getTenchitietsp();
            }
        });

        clsoluong.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietKhoSanPham, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietKhoSanPham, String> p) {
                return p.getValue().getSoluong();
            }
        });

        clsoluong.setCellFactory(new Callback<TableColumn<ChiTietKhoSanPham, String>, TableCell<ChiTietKhoSanPham, String>>() {
            @Override
            public TableCell<ChiTietKhoSanPham, String> call(TableColumn<ChiTietKhoSanPham, String> p) {
                return new TextFieldTableCell();
            }
        });

        clsoluong.setCellFactory(TextFieldTableCell.forTableColumn());
        clsoluong.setOnEditStart(
                new EventHandler<TableColumn.CellEditEvent<ChiTietKhoSanPham, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ChiTietKhoSanPham, String> t) {
                btnLuu.setDisable(true);
            }
        }
        );

        tblchitietkhosp.setRowFactory(tv -> {
            TableRow<ChiTietKhoSanPham> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    clsoluong.setOnEditCommit(
                            new EventHandler<TableColumn.CellEditEvent<ChiTietKhoSanPham, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ChiTietKhoSanPham, String> t) {
                            ((ChiTietKhoSanPham) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setSoluong(new SimpleStringProperty(t.getNewValue()));
                            tongsoluong = 0;
                            boolean flag = true;
                            try {
                                Integer.valueOf(((ChiTietKhoSanPham) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).getSoluong().get().trim());
                                row.setStyle("-fx-background-color: green");
                            } catch (NumberFormatException ex) {
                                row.setStyle("-fx-background-color: red");
                                ShowMessage
                                        .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Số lượng chỉ được nhập số")
                                        .showAndWait();
                                btnLuu.setDisable(true);
                                flag = false;
                            }

                            for (Object o : tblchitietkhosp.getItems()) {
                                int sl = 0;
                                try {
                                    sl = Integer.valueOf(clsoluong.getCellData(o).toString().trim());
                                } catch (NumberFormatException ex) {
                                    sl = 0;
                                } finally {
                                    tongsoluong += sl;
                                }
                            }
                            lbTongSoLuong.setText("Tổng số lượng: " + tongsoluong);
                            if (tongsoluong == SLSP && flag == true) {
                                btnLuu.setDisable(false);
                            } else {
                                btnLuu.setDisable(true);
                            }
                        }
                    });
                }
            });
            return row;
        });

        tblchitietkhosp.setItems(listCTKSP);
    }

    @FXML
    private void Handler_btnLuu(ActionEvent event) {
        KhoSanPham ksp = new KhoSanPham(MaNhanVien, MAPN);
        if (ksp.ThemKhoSanPham()) {
            for (Object o : tblchitietkhosp.getItems()) {
                String MaCTSP = clmachitietsp.getCellData(o).toString();
                ChiTietSanPham ctsp = new ChiTietSanPham();
                int soluongcu = ctsp.getSoLuongFromMaCTSP(MaCTSP);
                int soluongnhap = Integer.valueOf(clsoluong.getCellData(o).toString());
                int soluongmoi = soluongcu + soluongnhap;
                ctsp.updateSoLuongFromMaCTSP(MaCTSP, soluongmoi);
            }
            btnLuu.setDisable(true);
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm dữ liệu thành công")
                    .showAndWait();
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Thêm dữ liệu thất bại")
                    .showAndWait();
        }
    }

    @FXML
    private void Handler_btnThoat(ActionEvent event) {
        stageCTKSP.close();
    }
}