/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.MauSac;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_MauSacController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnXoa;
    @FXML
    private JFXButton btnThem;
    @FXML
    private JFXTextField txt_fi_tenmausac;
    @FXML
    private TableView<String> table_view;
    private String tenmau;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        viewTable();
        table_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (table_view.getSelectionModel().getSelectedItem() != null) {                   
                    tenmau = newValue.toString().split("]")[0].substring(1).trim();                
                }
            }
        });
    }

    @FXML
    private void ClickEvent(ActionEvent evt) {
        JFXButton btn = (JFXButton) evt.getSource();
        if (btn == btnThem) {
            ThemMauSac();
        } else if (btn == btnXoa) {
            XoaMauSac();
        }
    }

    private void viewTable() {
        if (!table_view.getColumns().isEmpty()) {
            table_view.getColumns().clear();
        }
        MauSac mausac = new MauSac();
        mausac.LoadTable(table_view);
        table_view.getColumns().get(0).setText("Tên màu");
    }

    private void ThemMauSac() {
        StringProperty tenmau = new SimpleStringProperty(txt_fi_tenmausac.getText());
        MauSac mausac = new MauSac(tenmau);
        if (mausac.insert()) {
            viewTable();
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm dữ liệu thành công")
                    .showAndWait();
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Thêm dữ liệu thất bại")
                    .showAndWait();
        }
    }

    private void XoaMauSac() {
        StringProperty _tenmau = new SimpleStringProperty(this.tenmau);
        MauSac mausac = new MauSac(_tenmau);
        if (mausac.delete()) {
            viewTable();
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Xóa dữ liệu thành công")
                    .showAndWait();
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Xóa dữ liệu thất bại")
                    .showAndWait();
        }
    }
}
