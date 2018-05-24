package clothesstore_controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static clothesstore_controller.FXML_HoaDonMuaHangController.mapn;
import clothesstore_model.ChiTietHoaDonMuaHang;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_XemCTPNController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView tblCTPN;
    @FXML
    private TableColumn clMaSP, clTenSP, clSoLuong, clGiaVon, clThanhTien;
    @FXML
    private JFXTextField txtmaphieunhap;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtmaphieunhap.setText(Integer.toString(mapn));
        InitTableCTPN();
    }    
    private void InitTableCTPN() {
        ObservableList<ChiTietHoaDonMuaHang> list = FXCollections.observableArrayList();
        ChiTietHoaDonMuaHang ctpn = new ChiTietHoaDonMuaHang();
        list = ctpn.getTableChiTietPhieuNhap(mapn);
        clMaSP.setCellValueFactory(new PropertyValueFactory("masanpham"));
        clTenSP.setCellValueFactory(new PropertyValueFactory("tensanpham"));
        clThanhTien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
        clSoLuong.setCellValueFactory(new PropertyValueFactory("soluongsanphamnhap"));
        clGiaVon.setCellValueFactory(new PropertyValueFactory("giavon")); 
        tblCTPN.setPlaceholder(new Label("Chưa thêm chi tiết phiếu nhập"));
        tblCTPN.setItems(list);
    }
}
