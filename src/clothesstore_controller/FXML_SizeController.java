/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.MauSac;
import clothesstore_model.Size;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
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
public class FXML_SizeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnXoa;
    @FXML
    private JFXButton btnThem;
    @FXML
    private JFXTextField txt_fi_tensize;
    @FXML
    private TableView<String> table_view;
    private String tensize;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        viewTable();
        table_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (table_view.getSelectionModel().getSelectedItem() != null) {

                    tensize = newValue.toString().split("]")[0].substring(1).trim();
                    txt_fi_tensize.setText(tensize);
                }
            }
        });
        txt_fi_tensize.setOnKeyTyped(event -> {
            int maxCharacters = 8;
            if (txt_fi_tensize.getText().length() > maxCharacters - 1) {
                event.consume();
            }
        });
    }

    @FXML
    private void ClickEvent(ActionEvent evt) {
        JFXButton btn = (JFXButton) evt.getSource();
        if (btn == btnThem) {
            ThemSize();
        } else if (btn == btnXoa) {
            XoaSize();
        }
    }

    private void ThemSize() {
        StringProperty tensize = new SimpleStringProperty(txt_fi_tensize.getText());
        Size size = new Size(tensize);
        if (size.insert()) {
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

    private void XoaSize() {
        StringProperty _tensize = new SimpleStringProperty(tensize);
        Size size = new Size(_tensize);
        if (size.delete()) {
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

    private void viewTable() {
        if (!table_view.getColumns().isEmpty()) {
            table_view.getColumns().clear();
        }
        Size size = new Size();
        size.LoadTable(table_view);
        table_view.getColumns().get(0).setText("Tên size");

    }
}
