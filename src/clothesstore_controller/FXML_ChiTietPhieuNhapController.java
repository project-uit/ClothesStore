/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;
import clothesstore_model.ChiTietPhieuNhap;
import clothesstore_model.PhieuNhap;
import static clothesstore_controller.FXML_PhieuNhapController.mapn;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonBar;

/**
 * FXML Controller class
 *
 * @author 15520
 */
public class FXML_ChiTietPhieuNhapController implements Initializable {

    @FXML
    private TableView tablechitietsanpham;
    @FXML
    private TableColumn machitietphieunhap;
    @FXML
    private TableColumn sanpham;
    @FXML
    private TableColumn soluongsanphamnhap;
    @FXML
    private TableColumn giavon;
    @FXML
    private TableColumn thanhtien;
    @FXML
    private AnchorPane paneINFO1;
    private JFXTextField txtfimachitiet;
    @FXML
    private JFXTextField txtfisanpham;
    @FXML
    private JFXTextField txtfisoluong;
    @FXML
    private JFXTextField txtfigiavon;
    @FXML
    private JFXTextField txtfithanhtien;
    @FXML
    private JFXButton btnthem;
    private PhieuNhap phieunhap;
    @FXML
    private JFXButton btnluu;
    @FXML
    private JFXButton btnthemsanpham;
    private ChiTietPhieuNhap chitietpn;

    public static Stage stageQuanLySearchSanPham;
    @FXML
    private JFXButton btnhuy;
    @FXML
    private JFXTextField txtmaphieunhap;
    @FXML
    private JFXTextField txtgiaban;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableView();
        txtmaphieunhap.setText(Integer.toString(mapn));
        txtfigiavon.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtfigiavon.setText(newValue.replaceAll("[^\\d]", ""));

                }
                if (txtfisoluong.getText().isEmpty() || newValue.isEmpty()) {
                    txtfithanhtien.setText("0");
                    return;
                }
                try {
                    Integer value1 = Integer.valueOf(newValue);
                    Integer value2 = Integer.valueOf(txtfisoluong.getText());
                    Integer r = value1 * value2;
                    txtfithanhtien.setText("" + r);
                } catch (NumberFormatException ex) {

                }
            }
        });
        txtfisoluong.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtfisoluong.setText(newValue.replaceAll("[^\\d]", ""));

                }
                if (txtfigiavon.getText().isEmpty() || newValue.isEmpty()) {
                    txtfithanhtien.setText("0");
                    return;
                }
                try {
                    Integer value1 = Integer.valueOf(newValue);
                    Integer value2 = Integer.valueOf(txtfigiavon.getText());
                    Integer r = value1 * value2;
                    txtfithanhtien.setText("" + r);
                } catch (NumberFormatException ex) {

                }

            }
        });

    }

    public void InitTableView() {
        ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
        ObservableList<ChiTietPhieuNhap> list = ctpn.getTableChiTietPhieuNhap(mapn);
        machitietphieunhap.setCellValueFactory(new PropertyValueFactory("machitietphieunhap"));
        sanpham.setCellValueFactory(new PropertyValueFactory("masanpham"));
        soluongsanphamnhap.setCellValueFactory(new PropertyValueFactory("soluongsanphamnhap"));
        giavon.setCellValueFactory(new PropertyValueFactory("giavon"));
        thanhtien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
        tablechitietsanpham.setItems(list);
    }

    @FXML
    private void Handler_Btnthem(ActionEvent event) {
        String masp = txtfisanpham.getText();
        //String maphieunhap = Integer.toString(mapn);
        tablechitietsanpham.getSelectionModel().focus(-1);
        int giaban = 0;
        try {
            giaban = Integer.parseInt(txtgiaban.getText());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        int soluong = 0;
        try {
            soluong = Integer.parseInt(txtfisoluong.getText().toString());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        int giavon = 0;
        try {
            giavon = Integer.parseInt(txtfigiavon.getText().toString());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        int thanhtien = 0;
        try {
            thanhtien = Integer.parseInt(txtfithanhtien.getText().toString());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if (masp.equals("") || soluong == 0 || giavon == 0 || giaban == 0) {

            ButtonType cancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Vui lòng điền đầy đủ thông tin",
                    cancel);

            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(masp, mapn, soluong, giavon, thanhtien);
            ctpn.ThemChiTietPhieuNhap();
            ctpn.CapNhatTongTienPhieuNhap(mapn);
            ctpn.CapNhatGiaBanSanPham(masp, giaban);
            InitTableView();
        }
    }

    private void handler_thoat(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void handler_luu(ActionEvent event) {

        String masp = txtfisanpham.getText();
        String maphieunhap = Integer.toString(mapn);
        int giaban = 0;
        try {
            giaban = Integer.parseInt(txtgiaban.getText());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        int soluong = 0;
        try {
            soluong = Integer.parseInt(txtfisoluong.getText().toString());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        int giavon = 0;
        try {
            giavon = Integer.parseInt(txtfigiavon.getText().toString());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        int thanhtien = 0;
        try {
            thanhtien = Integer.parseInt(txtfithanhtien.getText().toString());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if (masp.equals("") || soluong == 0 || giavon == 0 || giaban == 0) {

            ButtonType cancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Vui lòng điền đầy đủ thông tin",
                    cancel);

            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(chitietpn.getMachitietphieunhap(), masp, mapn, soluong, giavon, thanhtien);
            ctpn.CapNhatChiTietPhieuNhap();
            ctpn.CapNhatTongTienPhieuNhap(mapn);
            ctpn.CapNhatGiaBanSanPham(masp, giaban);
            InitTableView();
            btnluu.setDisable(true);
            btnthem.setDisable(false);
            btnhuy.setDisable(true);
        }

    }

    @FXML
    private void handler_suachitietphieunhap(ActionEvent event) {
        ChiTietPhieuNhap getSelectedRow = (ChiTietPhieuNhap) tablechitietsanpham.getSelectionModel().getSelectedItem();
        chitietpn = (ChiTietPhieuNhap) tablechitietsanpham.getSelectionModel().getSelectedItem();
        if (getSelectedRow == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            alert.setContentText("Mời chọn chi tiết phiếu nhập mã ");
            return;
        }
        txtfisanpham.setText(getSelectedRow.getMasanpham());
        txtfisoluong.setText(Integer.toString(getSelectedRow.getSoluongsanphamnhap()));
        txtfigiavon.setText(Integer.toString(getSelectedRow.getGiavon()));
        txtfithanhtien.setText(Integer.toString(getSelectedRow.getThanhtien()));
        btnluu.setDisable(false);
        btnthem.setDisable(true);
        btnhuy.setDisable(false);
    }

    @FXML
    private void handler_xoachitietphieunhap(ActionEvent event) {
        ChiTietPhieuNhap selectedForDeletion = (ChiTietPhieuNhap) tablechitietsanpham.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Mời chọn chi tiết phiếu nhập");
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nhắc nhở");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có muốn xóa chi tiết phiếu nhập mã " + selectedForDeletion.getMachitietphieunhap() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
            String maphieunhap = Integer.toString(mapn);
            ctpn.XoaChiTietPhieuNhap(selectedForDeletion.getMachitietphieunhap(), maphieunhap);

            ctpn.CapNhatTongTienPhieuNhap(mapn);
            InitTableView();
            System.out.println("Xoa Thanh Cong");
        } else {
            System.out.println("Xoa That Bai");
        }
    }

    @FXML
    private void handler_themsanpham(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/clothesstore_view/FXML_SearchSanPham.fxml"));
            loader.load();
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            FXML_SearchSanPhamController wc = loader.<FXML_SearchSanPhamController>getController();

            stage.setOnHidden((WindowEvent event1) -> {
                txtfisanpham.setText(wc.getData());
            });
            stage.show();
            stageQuanLySearchSanPham = stage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handler_huy(ActionEvent event) {
        btnluu.setDisable(true);
        btnthem.setDisable(false);
        btnhuy.setDisable(true);
    }

}
