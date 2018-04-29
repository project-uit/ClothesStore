/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.NhomHang;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_NhomHangController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnThem;

    @FXML
    private JFXButton btnXoa;
    @FXML
    private JFXTextField txt_fi_tennhomhang;
    @FXML
    private JFXTreeTableView<NhomHang> tree_table_vi;

    private JFXTreeTableColumn<NhomHang, String> ten_nhomhang = new JFXTreeTableColumn<>("Tên nhóm hàng");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ten_nhomhang.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NhomHang, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NhomHang, String> param) {
                return param.getValue().getValue().getTennhomhang();
            }
        });

        tree_table_vi.getColumns().setAll(ten_nhomhang);

        tree_table_vi.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        tree_table_vi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (tree_table_vi.getSelectionModel().getSelectedItem() != null) {
                    TreeItem<NhomHang> nhomhhangItem = tree_table_vi.getSelectionModel().getSelectedItem();
                    txt_fi_tennhomhang.setText("" + nhomhhangItem.getValue().getTennhomhang().get());
                }
            }
        });
        viewListTable();
        txt_fi_tennhomhang.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                tree_table_vi.getSelectionModel().clearSelection();

            }
        });
    }

    public void viewListTable() {
        NhomHang tennh = new NhomHang();
        ObservableList<NhomHang> tennhlist = tennh.getNHList();
        TreeItem<NhomHang> root = new RecursiveTreeItem<>(tennhlist, RecursiveTreeObject::getChildren);
        tree_table_vi.setRoot(root);
        tree_table_vi.setShowRoot(false);
    }

    @FXML
    private void ClickEvent(ActionEvent event) throws IOException {
        JFXButton btn = (JFXButton) event.getSource();
        if (btn == btnThem) {
            insertNhomHang();
        } else if (btn == btnXoa) {
            deleteNhomhang();
        }

    }

    public void insertNhomHang() {
        StringProperty tennh = new SimpleStringProperty(txt_fi_tennhomhang.getText());
        NhomHang nhomhang = new NhomHang(tennh);
        if (nhomhang.isEmpty()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Bạn không được để tên nhóm hàng trống")
                    .showAndWait();
            return;
        }
        if (nhomhang.insert()) {
            viewListTable();
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm dữ liệu thành công")
                    .showAndWait();

        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Thêm dữ liệu thất bại")
                    .showAndWait();
        }

    }

    public void deleteNhomhang() {
        StringProperty tennh = new SimpleStringProperty(txt_fi_tennhomhang.getText());
        NhomHang nhomhang = new NhomHang(tennh);
        if (nhomhang.delete()) {
            viewListTable();
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
