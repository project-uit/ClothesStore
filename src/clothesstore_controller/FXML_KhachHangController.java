/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietKhachHang;
import clothesstore_model.KhachHang;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_KhachHangController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField txt_fi_tenkhachhang, txt_fi_sodienthoai;
    @FXML
    private JFXButton btnLuu;
    private Integer mahoadon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTextField();
        List<String> arr_sdt = new KhachHang().getListSDT();
        TextFields.bindAutoCompletion(txt_fi_sodienthoai, arr_sdt);
    }

    public JFXButton getbtnLuu() {
        return btnLuu;
    }

    public void btnluu_click() {
        KhachHang kh = new KhachHang(new SimpleStringProperty(txt_fi_tenkhachhang.getText()),
                new SimpleStringProperty(txt_fi_sodienthoai.getText()));
        if (kh.isEmpty()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Bạn phải điền thông tin vào ô số điện thoại")
                    .showAndWait();
          
        }
        ChiTietKhachHang ctkh = new ChiTietKhachHang();
        ctkh.setSodienthoai(new SimpleStringProperty(txt_fi_sodienthoai.getText()));
        ctkh.setMahoadon(new SimpleIntegerProperty(mahoadon));
        boolean flag = false;
        if (ctkh.insert()) {
            System.out.println("Lưu thành công thông tin cũ");
            flag = true;
        } else {
            if (kh.insert()) {
                if (ctkh.insert()) {
                    System.out.println("Lưu thành công thông tin mới");
                    flag = true;
                }
            }
        }
        btnLuu.setMouseTransparent(flag);
        

    }

    public void setMahoadon(Integer mahoadon) {
        this.mahoadon = mahoadon;
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
                KhachHang kh = new KhachHang();
                String tenkh = kh.getTenkhachhang(txt_fi_sodienthoai.getText());
                txt_fi_tenkhachhang.setText(tenkh);
            }
        });
    }
}
