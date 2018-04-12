/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.nhasanxuat_model;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_NhasanxuatController implements Initializable {

    @FXML
    private JFXButton btnThem;

    @FXML
    private JFXButton btnXoa;

    @FXML
    private JFXTextField txt_fi_tennhasanxuat;
    @FXML
    private JFXTreeTableView<nhasanxuat_model> tree_table_vi;

    private JFXTreeTableColumn<nhasanxuat_model, String> ten_nhasanxuat = new JFXTreeTableColumn<>("Tên nhà sản xuất");

    @FXML
    private void ClickEvent(ActionEvent event) throws IOException {
        JFXButton btn = (JFXButton) event.getSource();
        if (btn == btnThem) {
            insertNhaSanXuat();
        } else if (btn == btnXoa) {
            deleteNhaSanXuat();
        }
//        } else if (btn == btnThoat) {
//            Node source = (Node) event.getSource();
//            Stage stage = (Stage) source.getScene().getWindow();
//            stage.close();
//        }

    }

    public void insertNhaSanXuat() {
        StringProperty tennsx = new SimpleStringProperty(txt_fi_tennhasanxuat.getText());
        nhasanxuat_model nsx = new nhasanxuat_model(tennsx);

        if (nsx.insert()) {
            viewListTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Thêm dữ liệu thành công");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Thêm dữ liệu thất bại");
            alert.showAndWait();
        }
    }

    public void viewListTable() {
        nhasanxuat_model tennsx = new nhasanxuat_model();
        ObservableList<nhasanxuat_model> tennsxList = tennsx.getNSXList();
        if (tennsxList.isEmpty()) {
            return;
        }
        TreeItem<nhasanxuat_model> root = new RecursiveTreeItem<>(tennsxList, RecursiveTreeObject::getChildren);
        tree_table_vi.setRoot(root);
        tree_table_vi.setShowRoot(false);
    }

    public void deleteNhaSanXuat() {
        StringProperty tennsx = new SimpleStringProperty(txt_fi_tennhasanxuat.getText());
        nhasanxuat_model nsx = new nhasanxuat_model(tennsx);
        if (nsx.delete()) {
            viewListTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Xóa dữ liệu thành công");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Xóa dữ liệu thất bại");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ten_nhasanxuat.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<nhasanxuat_model, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<nhasanxuat_model, String> param) {
                return param.getValue().getValue().getTen_nhasanxuat();
            }
        });

        tree_table_vi.getColumns().setAll(ten_nhasanxuat);

        tree_table_vi.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        tree_table_vi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (tree_table_vi.getSelectionModel().getSelectedItem() != null) {
                    TreeItem<nhasanxuat_model> nhasanxuatItem = tree_table_vi.getSelectionModel().getSelectedItem();
                    txt_fi_tennhasanxuat.setText("" + nhasanxuatItem.getValue().getTen_nhasanxuat().get());
                }
            }
        });
        viewListTable();
        txt_fi_tennhasanxuat.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                tree_table_vi.getSelectionModel().clearSelection();

            }
        });
    }

}
