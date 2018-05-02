/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.CuaHang;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_CuaHangController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextArea txt_area_address;
    @FXML
    private JFXTextField txt_fi_tencuahang, txt_fi_sodienthoai, txt_fi_email;
    private CuaHang store;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        store = CuaHang.getObject();
        init();
    }

    private void init() {
        txt_fi_tencuahang.setText(store.getTencuahang().get());
        txt_fi_sodienthoai.setText(store.getSodienthoai().get());
        txt_fi_email.setText(store.getEmail().get());
        txt_area_address.setText(store.getDiachi().get());
    }

    @FXML
    private void btnLuu_click(ActionEvent act) {
        CuaHang ch = new CuaHang(new SimpleStringProperty(txt_fi_tencuahang.getText()),
                new SimpleStringProperty(txt_area_address.getText()),
                new SimpleStringProperty(txt_fi_sodienthoai.getText()),
                new SimpleStringProperty(txt_fi_email.getText()));
        if (ch.update()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Lưu thành công")
                    .showAndWait();
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Lưu thất bại")
                    .showAndWait();
        }
    }

}
