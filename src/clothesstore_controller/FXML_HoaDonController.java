/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietSanPham;
import clothesstore_model.KhachHang;
import clothesstore_model.SanPham;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_HoaDonController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnThem, btnXoa, btnLapHoaDon, btnThanhToan, btnInHoaDon;
    @FXML
    private JFXTextField txt_fi_tenkhachhang, txt_fi_sodienthoai,
            txt_fi_tongtien, txt_fi_machitietsanpham, txt_fi_dongia, txt_fi_thanhtien;
    @FXML
    private JFXDatePicker date_ngayban;
    @FXML
    private Spinner<Integer> spin_soluong;
    @FXML
    private TableView table_view;

    private Integer soluongmua;
    private int dongia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO               
        date_ngayban.setValue(LocalDate.now());
        InitTextField();
        InitSpinner();
    }

    private void InitTextField() {
        txt_fi_sodienthoai.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txt_fi_sodienthoai.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txt_fi_sodienthoai.setOnKeyTyped(event -> {
            int maxCharacters = 13;
            if (txt_fi_sodienthoai.getText().length() > maxCharacters - 1) {
                event.consume();
            }
        });
        txt_fi_sodienthoai.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                getTenkhachhangtuSDT();
            }
        });
        txt_fi_machitietsanpham.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                getDonGia();
                InitSpinner();
            }
        });
        List<String> arr_mactsp = new ChiTietSanPham().getListMactsp();
        TextFields.bindAutoCompletion(txt_fi_machitietsanpham, arr_mactsp);
        List<String> arr_sdt = new KhachHang().getListSDT();
        TextFields.bindAutoCompletion(txt_fi_machitietsanpham, arr_sdt);
    }

    private void getDonGia() {
        SanPham sp = new SanPham();
        dongia = sp.getDongia(txt_fi_machitietsanpham.getText());
        if (dongia != -1) {
            txt_fi_dongia.setText("" + dongia);
        }
    }

    private void getTenkhachhangtuSDT() {
        KhachHang kh = new KhachHang();
        String tenkh = kh.getTenkhachhang(txt_fi_sodienthoai.getText());
        txt_fi_tenkhachhang.setText(tenkh);
    }

    private void InitSpinner() {
        StringProperty mactsp = new SimpleStringProperty(txt_fi_machitietsanpham.getText());
        ChiTietSanPham ctsp = new ChiTietSanPham(mactsp);
        int max = ctsp.getMAXSoluongCTSP();
        int min = 0;
        if (max != -1) {
            SpinnerValueFactory<Integer> valueFactory
                    = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
            spin_soluong.setValueFactory(valueFactory);
        }
        //spin_soluong.getEditor()
        spin_soluong.valueProperty().addListener((obs, oldValue, newValue)
                -> {
            soluongmua = newValue;
            int thanhtien = soluongmua * dongia;
            txt_fi_thanhtien.setText("" + thanhtien);
        }
        );
    }
    private boolean checkLaphoadon = true;

    @FXML
    private void Click_Event(ActionEvent act) {
        JFXButton btn = (JFXButton) act.getSource();
        if (btn == btnLapHoaDon) {
            if (checkLaphoadon) {
                laphoadon_click();
            } else {
                huyhoadon_click();
            }
        }
        else if(btn==btnThem)
        {
            btnthem_click();
        }
    }

    private void laphoadon_click() {

      //  btnThanhToan.setDisable(false);
        btnThem.setDisable(false);
        btnXoa.setDisable(false);
      //  btnInHoaDon.setDisable(false);
        txt_fi_sodienthoai.setEditable(false);
        btnLapHoaDon.setText("Hủy hóa đơn");
        checkLaphoadon=false;
    }

    private void huyhoadon_click() {
        btnLapHoaDon.setText("Lập hóa đơn");
        btnThanhToan.setDisable(true);
        btnThem.setDisable(true);
        btnXoa.setDisable(true);
        btnInHoaDon.setDisable(true);
        txt_fi_sodienthoai.setEditable(true);
        checkLaphoadon=true;
    }
    private void btnthem_click()
    {
        btnThanhToan.setDisable(false);
    }
}
