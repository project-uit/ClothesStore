/*
 */
package clothesstore_controller;

import clothesstore_model.NhaSanXuat;
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
public class FXML_NhasanxuatController implements Initializable {

    @FXML
    private JFXButton btnThem;

    @FXML
    private JFXButton btnXoa;
    
    @FXML
    private JFXTextField txt_fi_tennhasanxuat;
    @FXML
    private JFXTreeTableView<NhaSanXuat> tree_table_vi;

    private JFXTreeTableColumn<NhaSanXuat, String> ten_nhasanxuat = new JFXTreeTableColumn<>("Tên nhà sản xuất");

    @FXML
    private void ClickEvent(ActionEvent event) throws IOException {
        JFXButton btn = (JFXButton) event.getSource();
        if (btn == btnThem) {
            insertNhaSanXuat();
        } else if (btn == btnXoa) {
            deleteNhaSanXuat();
        }

    }

    public void insertNhaSanXuat() {
        StringProperty tennsx = new SimpleStringProperty(txt_fi_tennhasanxuat.getText());
        NhaSanXuat nsx = new NhaSanXuat(tennsx);
        if (nsx.isEmpty()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Bạn không được để tên nhà sản xuất trống")
                    .showAndWait();
            return;
        }
        if (nsx.insert()) {
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

    public void viewListTable() {
        NhaSanXuat tennsx = new NhaSanXuat();
        ObservableList<NhaSanXuat> tennsxList = tennsx.getNSXList();
        TreeItem<NhaSanXuat> root = new RecursiveTreeItem<>(tennsxList, RecursiveTreeObject::getChildren);
        tree_table_vi.setRoot(root);
        tree_table_vi.setShowRoot(false);
    }

    public void deleteNhaSanXuat() {
        StringProperty tennsx = new SimpleStringProperty(txt_fi_tennhasanxuat.getText());
        NhaSanXuat nsx = new NhaSanXuat(tennsx);
        if (nsx.delete()) {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ten_nhasanxuat.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<NhaSanXuat, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<NhaSanXuat, String> param) {
                return param.getValue().getValue().getTen_nhasanxuat();
            }
        });

        tree_table_vi.getColumns().setAll(ten_nhasanxuat);

        tree_table_vi.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        tree_table_vi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (tree_table_vi.getSelectionModel().getSelectedItem() != null) {
                    TreeItem<NhaSanXuat> nhasanxuatItem = tree_table_vi.getSelectionModel().getSelectedItem();
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
