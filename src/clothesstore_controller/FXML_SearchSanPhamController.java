/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_PhieuNhapController.mapn;
import static clothesstore_controller.FXML_PhieuNhapController.rootCTPN;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import clothesstore_model.ChiTietHoaDonMuaHang;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author 15520
 */
public class FXML_SearchSanPhamController implements Initializable {

    @FXML
    private TableView tblSanPhamDaNhap, tblSanPhamChuaNhap;
    @FXML
    private TableColumn clMaSP0, clTenSP0, clCheck0,
            clMaSP1, clTenSP1, clCheck1;

    ObservableList<ChiTietHoaDonMuaHang> dataTableSPDaNhap, dataTableSPChuaNhap;
    public static ObservableList<ChiTietHoaDonMuaHang> listSP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataTableSPDaNhap = FXCollections.observableArrayList();
        dataTableSPChuaNhap = FXCollections.observableArrayList();
        listSP = FXCollections.observableArrayList();
        InitTableSPDaNhap();
        InitTableSPChuaNhap();

        ChiTietHoaDonMuaHang ctpn = new ChiTietHoaDonMuaHang();
        dataTableSPDaNhap = ctpn.LoadSanPhamDaNhap(mapn);
        dataTableSPChuaNhap = ctpn.LoadSanPhamChuaNhap(mapn);

        tblSanPhamDaNhap.setItems(dataTableSPDaNhap);
        tblSanPhamChuaNhap.setItems(dataTableSPChuaNhap);
    }

    @FXML
    private void handler_btnNext(ActionEvent event) {
        AnchorPane ctpn;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_ChiTietPhieuNhap.fxml"));
            ctpn = fxmlLoader.load();
            SildingWindowAnimation silde = new SildingWindowAnimation();
            silde.SildeTo(rootCTPN,
                    ctpn,
                    SildingWindowAnimation.Direction.SildeLeft);
            ctpn.requestFocus();

            rootCTPN.setLeftAnchor(ctpn, 0.0);
            rootCTPN.setRightAnchor(ctpn, 0.0);
            rootCTPN.setTopAnchor(ctpn, 0.0);
            rootCTPN.setBottomAnchor(ctpn, 0.0);

            FXML_ChiTietPhieuNhapController controller = fxmlLoader.getController();
            controller._setListSP(listSP);
            controller.InitTableCTPN();
        } catch (IOException ex) {
            Logger.getLogger(FXML_SearchSanPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setListSP(ObservableList<ChiTietHoaDonMuaHang> list) {
        listSP = list;
        System.out.println(listSP.size());
    }

    private void InitTableSPDaNhap() {
        clMaSP1.setCellValueFactory(new PropertyValueFactory("masanpham"));
        clTenSP1.setCellValueFactory(new PropertyValueFactory("tensanpham"));
        clCheck1.setCellValueFactory(new PropertyValueFactory("checked"));
        clCheck1.setCellFactory(CheckBoxTableCell.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Integer param) {
                if (dataTableSPDaNhap.get(param).checkedProperty().get()) {
                    // do something here
                    listSP.add(dataTableSPDaNhap.get(param));
                } else {
                    listSP.remove(dataTableSPDaNhap.get(param));
                }
                return dataTableSPDaNhap.get(param).checkedProperty();
            }
        }));
        tblSanPhamDaNhap.setPlaceholder(new Label("Không tìm thấy sản phẩm"));
    }

    private void InitTableSPChuaNhap() {
        clMaSP0.setCellValueFactory(new PropertyValueFactory("masanpham"));
        clTenSP0.setCellValueFactory(new PropertyValueFactory("tensanpham"));
        clCheck0.setCellValueFactory(new PropertyValueFactory("checked"));
        clCheck0.setCellFactory(CheckBoxTableCell.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Integer param) {
                if (dataTableSPChuaNhap.get(param).checkedProperty().get()) {
                    // do something here
                    listSP.add(dataTableSPChuaNhap.get(param));
                } else {
                    listSP.remove(dataTableSPChuaNhap.get(param));
                }
                return dataTableSPChuaNhap.get(param).checkedProperty();
            }
        }));
        tblSanPhamChuaNhap.setPlaceholder(new Label("Không tìm thấy sản phẩm"));
    }
}
